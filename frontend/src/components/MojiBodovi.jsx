import React, { useEffect, useState } from "react";
import { API_URL } from '../config';
import "./MojiBodovi.css";

function MojiBodovi() {
    const [data, setData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
  
    useEffect(() => {
        const fetchResults = async () => {
            try {
                const response = await fetch(`${API_URL}/student/moji-bodovi`, {
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
    }, []);
  
    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {error}</div>;
  
    const { status, totalPoints, progress, scores } = data;
  
    return (
        <div className="results-page">
            <div className={`status-box ${status}`}>
            {status === "pass" && <p>Prošli ste predmet!</p>}
            {status === "fail" && <p>Pali ste predmet.</p>}
            {status === "neutral" && <p>Nema dovoljno podataka.</p>}
            </div>  
            <div className="progress-section">
                <label htmlFor="progress-bar">Vaš napredak:</label>
                <div className="progress-bar-container">
                    <div className="progress-bar" style={{ width: `${progress}%` }}></div>
                </div>
                <p>{progress}% završenog gradiva</p>
            </div>
            <div className="total-points">
                <p>Ukupno bodova: {totalPoints}</p>
            </div>
            <div className="scores-section">
                <h3>Bodovi po terminima:</h3>
                <ul>
                    {scores.terms.map((term, index) => (
                    <li key={index}>
                        <strong>Termin {index + 1}:</strong> {term} bodova
                    </li>
                    ))}
                    {scores.extra > 0 && (
                    <li>
                        <strong>Dodatni bodovi od nastavnika:</strong> {scores.extra}
                    </li>
                    )}
                </ul>
            </div>
        </div>
    );
}
  
export default MojiBodovi;

