import React, { useState, useRef, useEffect } from "react";
import { API_URL } from '../config';
import { useNavigate } from 'react-router-dom';

const PrijavaTermina = () => {
    const [datum, setDatum] = useState('');
    const [pocetak, setPocetak] = useState('');
    const [kraj, setKraj] = useState('');
    const [lokacija, setLokacija] = useState('');
    const [locationName, setLocationName] = useState('');
    const [maksBodova, setMaksBodova] = useState('4');
    const [kapacitet, setKapacitet] = useState('15');
    const [map, setMap] = useState(null);
    const [searchQuery, setSearchQuery] = useState("");
    const mapRef = useRef(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [data, setData] = useState('');
    const navigate = useNavigate();

    const initMap = () => {
        const defaultLocation = { lat: 45.8150, lng: 15.9819 }; // Zagreb
        const mapInstance = new window.google.maps.Map(mapRef.current, {
            center: defaultLocation,
            zoom: 13, // Ovo mozemo mjenjat
        });

        mapInstance.addListener("click", (event) => {
            const clickedLocation = event.latLng;
            const geocoder = new window.google.maps.Geocoder();

            geocoder.geocode({ location: clickedLocation }, (results, status) => {
                if (status === "OK" && results[0]) {
                    const address = results[0].formatted_address;
                    setLokacija(address);

                    new window.google.maps.Marker({
                        position: clickedLocation,
                        map: mapInstance,
                        title: address,
                    });
                } else {
                    alert("Nije moguće dohvatiti adresu. Pokušajte ponovno.");
                }
            });
        });

        setMap(mapInstance);
    };

    useEffect(() => {
        const script = document.createElement("script");
        script.src = `https://maps.googleapis.com/maps/api/js?key=AIzaSyC-xZPPxw-DsfXHKBwcYjDhTmj0V9o3QeQ&callback=initMap`;
        script.defer = true;
        script.async = true;
        window.initMap = initMap;
        document.head.appendChild(script);

        const fetchResults = async () => {
            try {
                const response = await fetch(`${API_URL}/voditelj/current`, {
                    method: "GET",
                    credentials: "include",
                    headers: {
                        "Content-Type": "application/json",
                    },
                });

                if (!response.ok) {
                    throw new Error(`Error: ${response.statusText}`);
                }

                const result = await response.json();
                setData(result);
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };

        fetchResults();

        return () => {
            delete window.initMap;
        };
    }, []);
    function combineDateAndTime(date, time) {
        const [day, month, year] = date.split(".");
        return `${year}-${month.padStart(2, "0")}-${day.padStart(2, "0")}T${time}`;
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!datum || !kapacitet || !pocetak || !kraj || !maksBodova) {
            alert('Sva polja su obavezna.');
            return;
        }
        if (!lokacija) {
            alert('Označite lokaciju na mapi.');
            return;
        }
        

        const termin = {
            maxPoints: maksBodova,
            termStart: combineDateAndTime(datum,pocetak),
            termEnd: combineDateAndTime(datum,kraj),
            location: {
                locationName: locationName,
                address: location,
            },
            capacity: kapacitet,
        };

        try {
            const response = await fetch(`${API_URL}/voditelj/noviTermin`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem('jwt_token')}`
                },
                body: JSON.stringify(termin),
                credentials: 'include',
            });

            if (response.ok) {
                navigate('/voditelj/dashboard');
                console.log('Uspješna prijava');
            } else {
                const errorData = await response.text();
                console.error('Registration error:', errorData);
                alert('Greška pri prijavi. Molimo provjerite unesene podatke.');
            }
        } catch (error) {
            console.error('Error:', error);
            alert('Došlo je do greške. Molimo pokušajte ponovno.');
        }
    };

    return (
        <div style={{ display: "flex", height: "80vh" }}>
            <div style={{ display: "flex", flexDirection: "column", justifyContent: "center", alignItems: "center", width: "40%", padding: "10px" }}>
                <h2>Prijava termina</h2>
                <form method="POST" className="montserrat-regular registration-form" onSubmit={handleSubmit}>
                    <div className="form-row">
                        <label htmlFor="datum">Datum:</label>
                        <input type="text" id="datum" value={datum} onChange={(e) => setDatum(e.target.value)} required />
                    </div>
                    <div className="form-row">
                        <label htmlFor="pocetak">Početak:</label>
                        <input type="text" id="pocetak" value={pocetak} onChange={(e) => setPocetak(e.target.value)} required />
                    </div>
                    <div className="form-row">
                        <label htmlFor="kraj">Kraj:</label>
                        <input type="text" id="kraj" value={kraj} onChange={(e) => setKraj(e.target.value)} required />
                    </div>
                    <div className="form-row">
                        <label htmlFor="kraj">Ime lokacije:</label>
                        <input type="text" id="kraj" value={locationName} onChange={(e) => setLocationName(e.target.value)} required />
                    </div>
                    <div className="form-row">
                        <label htmlFor="kapacitet">Kapacitet:</label>
                        <input type="text" id="kapacitet" value={kapacitet} onChange={(e) => setKapacitet(e.target.value)} required />
                    </div>
                    <div className="form-row">
                        <label htmlFor="maksBodova">Broj bodova:</label>
                        <select id="maksBodova" value={maksBodova} onChange={(e) => setMaksBodova(e.target.value)}>
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                        </select>
                    </div>
                    <div className="form-row">
                        <button type="submit">Pošalji</button>
                    </div>
                </form>
            </div>
            <div style={{ display: "flex", flexDirection: "column", width: "60%", justifyContent: "center", alignItems: "center" }}>
                <h2>Odaberi lokaciju</h2>
                <div ref={mapRef} style={{ height: "60vh", width: "80%" }}></div>
            </div>
        </div>
    );
};

export default PrijavaTermina;
