import React, { useState, useRef, useEffect } from "react";
import { API_URL } from '../config';

const PrijavaTermina = () => {
    const [datum, setDatum] = useState('');
    const [pocetak, setPocetak] = useState('');
    const [kraj, setKraj] = useState('');
    const [lokacija, setLokacija] = useState('');
    const [maksBodova, setMaksBodova] = useState('4');
    const [kapacitet, setKapacitet] = useState('15');
    const [map, setMap] = useState(null);
    const [searchQuery, setSearchQuery] = useState("");
    const mapRef = useRef(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [data, setData] = useState('');


    const initMap = () => {
        const defaultLocation = { lat: 45.8150, lng: 15.9819 }; // Zagreb
        const mapInstance = new window.google.maps.Map(mapRef.current, {
            center: defaultLocation,
            zoom: 13,  //Ovo mozemo mjenjat
        });

        setMap(mapInstance);
    };

    const handleSearch = () => {
        if (!map || !searchQuery) return;

        const geocoder = new window.google.maps.Geocoder();
        geocoder.geocode({ address: searchQuery }, (results, status) => {
            if (status === "OK" && results[0]) {
                const location = results[0].geometry.location;

                map.setCenter(location);
                map.setZoom(16);

                new window.google.maps.Marker({
                    position: location,
                    map,
                    title: searchQuery,
                });
            } else {
                alert("Location not found! Please try another search.");
            }
        });
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

    const { activityLeaderID, activityType} = data;
    

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!datum || !lokacija || !kapacitet || !pocetak || !kraj || !maksBodova) {
            alert('Sva polja su obavezna.');
            return;
        }

        const termin = {
            activityLeaderID,
            activityType,
            date: datum,
            start: pocetak,
            end: kraj,
            location: lokacija,
            maxPoints: maksBodova,
            maxCapacity: kapacitet,
            
            listOfStudentsIDs: []
        };

        try {
            const response = await fetch(`${API_URL}/voditelj/noviTermin`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                body: JSON.stringify(termin),
                credentials: 'include',
            });

            if (response.ok) {
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
        <div style={{display: "flex", flexDirection: "column", justifyContent: "center", alignItems: "center", width: "40%", padding: "10px" }}>
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
                        <label htmlFor="lokacija">Lokacija:</label>
                        <input type="text" id="lokacija" value={lokacija} onChange={(e) => setLokacija(e.target.value)} required />
                    </div>
                    <div className="form-row">
                        <label htmlFor="kapacitet">Kapacitet:</label>
                        <input type="text" id="kapacitet" value={kapacitet} onChange={(e) => setKapacitet(e.target.value)} required/>
                    </div>
                    <div className="form-row">
                        <label htmlFor="maksBodova">Broj bodova:</label>
                        <select id="maksBodova" value={maksBodova} onChange={(e) => setMaksBodova(e.target.value)}>
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="4">5</option>
                        </select>
                    </div>
                    <div className="form-row">
                        <button type="submit">Pošalji</button>
                    </div>
                </form>
        </div>
        <div style={{ display: "flex", flexDirection: "column",width: "60%", justifyContent: "center",  alignItems: "center" }}>
            <div style={{display: "flex", justifyContent: "flex-end", alignItems: "center" ,padding: "10px", height: "30px" }}>
                <h2 style={{marginRight: "20px", whiteSpace: "nowrap"}}>Prikaži lokaciju</h2>
                <input
                    type="text"
                    value={searchQuery}
                    onChange={(e) => setSearchQuery(e.target.value)}
                    placeholder="Unesi lokaciju..."
                    style={{ padding: "10px", width: "100%", marginRight: "10px" }}
                />
                <button onClick={handleSearch} style={{ padding: "10px" }}>Pretraži</button>
            </div>
            <div ref={mapRef} style={{height: "40vh", width: "70%"}}></div>
        </div>
    </div>
    );
};

export default PrijavaTermina;
