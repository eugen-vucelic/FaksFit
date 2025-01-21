import React, { useEffect, useState } from "react";
import { API_URL } from '../config';
import "./MojiBodovi.css";

function MojiBodovi() {
    const [data, setData] = useState(null); // Holds the fetched data
    const [loading, setLoading] = useState(true); // Loading state
    const [error, setError] = useState(null); // Error state

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
        <div className="moji-bodovi-page">
            <div className={`status-box ${status}`}>
                {status === "pass" && <p>Prošli ste predmet! Čestitamo! 🎉</p>}
                {status === "fail" && <p>Pali ste predmet. 😢 Potrudite se više!</p>}
                {status === "neutral" && <p>Nema dovoljno podataka za ocjenu. 🤔</p>}
            </div>

            <div className="progress-section">
                <label htmlFor="progress-bar">Vaš napredak:</label>
                <div className="progress-bar-container">
                    <div
                        className="progress-bar"
                        style={{ width: `${progress}%` }}
                        title={`${progress}% završenog gradiva`}
                    ></div>
                </div>
                <p>{progress}% završenog gradiva</p>
            </div>

            <div className="total-points">
                <p><strong>Ukupno bodova:</strong> {totalPoints}</p>
            </div>

            <div className="scores-section">
                <h3>Bodovi po terminima:</h3>
                {scores && scores.terms && scores.terms.length > 0 ? (
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
                ) : (
                    <p>Nema bodova iz termina.</p>
                )}
            </div>
        </div>
    );
}

export default MojiBodovi;
