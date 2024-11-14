import "./Profile.css";
import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useLocation } from 'react-router-dom';

function Profile(props) {
    const {isLoggedIn,setIsLoggedIn} = props;
    const [dashboardData, setDashboardData] = useState(null);
    const [error, setError] = useState(null);
    const navigate = useNavigate();
    const [firstName, setFirstName] = useState('')
    const [lastName, setLastName] = useState('')
    const [gender, setSelectedGender] = useState('M')
    const [nationality, setNationality] = useState('')
    const [birthDate, setBirthDate] = useState('')
    const [phoneNumber, setPhoneNumber] = useState('')

    useEffect(() => {
        fetch('http://localhost:8080/dashboard/student', {
            method: 'GET',
            credentials: 'include',
        })
            .then(response => {
                if (!response.ok) {
                    navigate('/');
                    throw new Error('Failed to fetch dashboard data');
                }
                
                return response.json();
            })
            .then(data => {
                setDashboardData(data);
            })
            .catch(error => {
                navigate("/");
                console.error('Error fetching dashboard data:', error);
                setIsLoggedIn(false);
                setError(error);
            });
    }, [setIsLoggedIn]);

    if (error) {
        return <div>Error: {error.message}</div>;
    }

    if (!dashboardData) {
        return <div>Loading...</div>;
    }

    const location = useLocation(); // Access query params
  
    useEffect(() => {
      const queryParams = new URLSearchParams(location.search);
      const imeFromQuery = queryParams.get('firstName');
      if (imeFromQuery) {
        setFirstName(decodeURIComponent(imeFromQuery));
      }
      const prezimeFromQuery = queryParams.get('lastName');
      if (prezimeFromQuery) {
        setLastName(decodeURIComponent(prezimeFromQuery));
      }
      const spolFromQuery = queryParams.get('gender');
      if (spolFromQuery) {
        setSelectedGender(decodeURIComponent(spolFromQuery));
      }
      const nacFromQuery = queryParams.get('nationality');
      if (nacFromQuery) {
        setNationality(decodeURIComponent(nacFromQuery));
      }
      const drFromQuery = queryParams.get('birthDate');
      if (drFromQuery) {
        setBirthDate(decodeURIComponent(drFromQuery));
      }
      const brFromQuery = queryParams.get('phoneNumber');
      if (brFromQuery) {
        setPhoneNumber(decodeURIComponent(brFromQuery));
      }
    }, [location.search]);
  
    const handleSubmit = async (e) => {
        e.preventDefault();
  
        const user = {
            firstName: firstName,
            lastName: lastName,
            gender: gender,
            nationality: nationality,
            birthDate: birthDate,
            phoneNumber: phoneNumber
        };
  
        try {
            const response = await fetch('http://localhost:8080/student/patch', {
            method: 'POST',
            mode: 'cors',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(user),
            credentials: 'include',
            });
    
            if (response.ok) {
            console.log('Korisnik uspješno poslan na backend');
            setIsLoggedIn(true);
            navigate('/profil');
            } else {
            console.log(response);
            console.error('Greška pri slanju podataka');
            }
        } catch (error) {
            console.error('Greška:', error);
        }
    };

    return (
        <div className="profile-grid">
            <form method="POST" onSubmit={handleSubmit}>
                <div className="form-row">
                    <label htmlFor="ime">Ime:</label>
                    <input type="text" id="ime" value={firstName} onChange={(e) => setFirstName(e.target.value)} />
                </div>
                <div className="form-row">
                    <label htmlFor="prezime">Prezime:</label>
                    <input type="text" id="prezime" value={lastName} onChange={(e) => setLastName(e.target.value)} />
                </div>
                <div className="form-row">
                    <label htmlFor="spol">Spol:</label>
                    <select id="spol" value={gender} onChange={(e) => setSelectedGender(e.target.value)}>
                        <option value="M">M</option>
                        <option value="Ž">Ž</option>
                    </select>
                </div>
                <div className="form-row">
                    <label htmlFor="nacionalnost">Nacionalnost:</label>
                    <input type="text" id="nacionalnost" value={nationality} onChange={(e) => setNationality(e.target.value)} />
                </div>
                <div className="form-row">
                    <label htmlFor="datRod">Datum rođenja:</label>
                    <input type="text" id="datRod" value={birthDate} onChange={(e) => setBirthDate(e.target.value)} />
                </div>
                <div className="form-row">
                    <label htmlFor="broj">Broj mobitela:</label>
                    <input type="text" id="broj" value={phoneNumber} onChange={(e) => setPhoneNumber(e.target.value)} />
                </div>
                <div className="form-row">
                    <button type="submit">Promijeni podatke</button>
                </div>
            </form>
        </div>
    )
}

export default Profile;