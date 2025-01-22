import React from 'react';
import { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import './Registration.css';
import { API_URL } from '../config';

function Registration({ isLoggedIn, setIsLoggedIn, setPassedOauth, passedOauth }) {
    const [ime, setIme] = useState('');
    const [prezime, setPrezime] = useState('');
    const [email, setEmail] = useState('');
    const [jmbag, setJmbag] = useState('');
    const [Fakultet, setSelectedFakultet] = useState('FER');
    const [Semestar, setSelectedSemestar] = useState('1');

    const location = useLocation();
    const navigate = useNavigate();

    useEffect(() => {
        const queryParams = new URLSearchParams(location.search);
        const emailFromQuery = queryParams.get('email');
        if (emailFromQuery) {
            setEmail(decodeURIComponent(emailFromQuery));
        } else {
            navigate('/');  // Redirect to login if no email
        }
    }, [location.search, navigate]);

    const handleGoBack = () => {
        setIsLoggedIn(false);
        setPassedOauth(false);
        document.cookie = 'JSESSIONID=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
        navigate('/');
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!ime || !prezime || !jmbag) {
            alert('Sva polja su obavezna.');
            return;
        }

        const user = {
            firstName: ime,
            lastName: prezime,
            email: email,
            JMBAG: jmbag,
            userFaculty: Fakultet,
            semester: Semestar
        };

        try {
            const response = await fetch(`${API_URL}/student/register`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                body: JSON.stringify(user),
                credentials: 'include',
            });

            if (response.ok) {
                console.log('Registration successful');
                // Double-check user is logged in (fetch current user)
                const userResponse = await fetch(`${API_URL}/student/current`, {
                credentials: 'include',
                headers: { 'Accept': 'application/json' }
                });
                if (userResponse.ok) {
                navigate('/student/dashboard');
                setIsLoggedIn(true);
                } else {
                alert('Session not recognized. Please log in again.');
                return;
                }

            } else {
                const errorData = await response.text();
                console.error('Registration error:', errorData);
                alert('Greška pri registraciji. Molimo provjerite unesene podatke.');
            }
        } catch (error) {
            console.error('Error:', error);
            alert('Došlo je do greške. Molimo pokušajte ponovno.');
        }
    };

    return (
        <div className="registration-grid">
            <div className="left-container">
                <p className="montserrat-regular blue">
                    Potrebno je odabrati fakultet na kojem polažete predmet Tjelesne i zdravstvene kulture.
                </p>
                <p className="montserrat-regular blue">
                    Nakon uspješne registracije kao student, moguće je registrirati sportsku aktivnost i slati ponude nastavnicima.
                </p>
            </div>
            <div className="right-container">
                <h2 className="montserrat-semibold-italic">Registracija (za studente)</h2>
                <form method="POST" className="montserrat-regular registration-form" onSubmit={handleSubmit}>
                    <div className="form-row">
                        <label htmlFor="ime">Ime:</label>
                        <input type="text" id="ime" value={ime} onChange={(e) => setIme(e.target.value)} required />
                    </div>
                    <div className="form-row">
                        <label htmlFor="prezime">Prezime:</label>
                        <input type="text" id="prezime" value={prezime} onChange={(e) => setPrezime(e.target.value)} required />
                    </div>
                    <div className="form-row">
                        <label htmlFor="email">Email:</label>
                        <input type="text" id="email" value={email} readOnly />
                    </div>
                    <div className="form-row">
                        <label htmlFor="jmbag">JMBAG:</label>
                        <input type="text" id="jmbag" value={jmbag} onChange={(e) => setJmbag(e.target.value)} required />
                    </div>
                    <div className="form-row">
                        <label htmlFor="fakultet">Fakultet:</label>
                        <select id="fakultet" value={Fakultet} onChange={(e) => setSelectedFakultet(e.target.value)}>
                            <option value="FER">FER</option>
                            <option value="PMF">PMF</option>
                            <option value="FSB">FSB</option>
                            <option value="FKIT">FKIT</option>
                        </select>
                    </div>
                    <div className="form-row">
                        <label htmlFor="semestar">Semestar:</label>
                        <select id="semestar" value={Semestar} onChange={(e) => setSelectedSemestar(e.target.value)}>
                            <option value="1">1.</option>
                            <option value="2">2.</option>
                            <option value="3">3.</option>
                            <option value="4">4.</option>
                        </select>
                    </div>
                    <div className="form-row">
                        <input type="checkbox" id="suglasnost" required />
                        <label htmlFor="suglasnost">Suglasan/na sam s uvjetima korištenja i GDPR privolama.</label>
                    </div>
                    <div className="form-row">
                        <button type="button" onClick={handleGoBack}>Povratak</button>
                        <button type="submit">Registracija</button>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default Registration;
