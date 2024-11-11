import {Link} from "react-router-dom";
import "./Registration.css";
import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

function Registration({isLoggedIn}) {

    const navigate = useNavigate();

    const handleGoBack = () => {
        navigate('/');
    };

    return (
        !isLoggedIn &&
        <>
            <div className="registration-grid">
                <div className="left-container">
                    <p className="montserrat-regular blue">Potrebno je odabrati fakultet na kojem polažete predmet Tjelesne i zdravstvene kulture.</p>
                    <p className="montserrat-regular blue">Nakon uspješne registracije kao student, moguće je registrirati sportsku aktivnost i slati ponude nastavnicima.</p>
                </div>
                <div className="right-container">
                    <h2 className="montserrat-semibold-italic">Registracija (za studente)</h2>
                    <form method="POST" className="montserrat-regular registration-form">
                        <div className="form-row">
                            <label htmlFor="ime">Ime:</label>
                            <input type="text" id="ime" value={"ispunio OAuth"}></input>
                        </div>
                        <div className="form-row">
                            <label htmlFor="prezime">Prezime:</label>
                            <input type="text" id="prezime" value={"ispunio OAuth"}></input>
                        </div>
                        <div className="form-row">
                            <label htmlFor="fakultet">Fakultet:</label>
                            <select id="fakultet">
                                {/* EXTRACT FROM DATABASE */}
                                <option value="fer">FER</option>
                                <option value="pmf">PMF</option>
                                <option value="fsb">FSB</option>
                                <option value="fkit">FKIT</option>
                            </select>
                        </div>
                        <div className={"form-row"}>
                            <label htmlFor="semestar">Semestar:</label>
                            <select id="semestar">
                                {/* EXTRACT FROM DATABASE */}
                                <option value="1">1.</option>
                                <option value="2">2.</option>
                                <option value="3">3.</option>
                                <option value="4">4.</option>
                            </select>
                        </div>
                        <div className="form-row">
                            <input type="checkbox" id="suglasnost"></input>
                            <label htmlFor="suglasnost">Suglasan/na sam s uvjetima korištenja i GDPR privolama.</label>
                        </div>
                        <div className={"form-row"}>
                            <button type="button"  onClick={handleGoBack}>Povratak </button>
                            <button type="submit">Registracija</button>
                        </div>
                    </form>
                </div>
            </div>
        </>
    )
}

export default Registration;