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
                        'Authorization': `Bearer ${localStorage.getItem('jwt_token')}`
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
    if (!data) return <div>No data available</div>;

    return (
        <div className="moji-bodovi-page">
            <div className={`status-box ${data.status}`}>
                {data.status === "true" && <p>Prošli ste predmet! Čestitamo! 🎉</p>}
                {data.status === "false" && <p>Nema dovoljno podataka za ocjenu. 🤔</p>}
            </div>

            <div className="progress-section">
                <label htmlFor="progress-bar">Vaš napredak:</label>
                <div className="progress-bar-container">
                    <div
                        className="progress-bar"
                        style={{ width: `${data.progress}%` }}
                        title={`${data.progress}% završenog gradiva`}
                    ></div>
                </div>
                <p>{data.progress}% završenog gradiva</p>
            </div>

            <div className="total-points">
                <p><strong>Ukupno bodova:</strong> {data.totalPoints}</p>
            </div>

            <div className="scores-section">
                <h3>Bodovi po terminima:</h3>
                {data.points && data.points.length > 0 ? (
                    <ul>
                        {data.points.map((point) => (
                            <li key={point.termId}>
                                <strong>Termin {point.termId}:</strong> {point.points} bodova
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p>Nema bodova iz termina.</p>
                )}
            </div>
        </div>
    );
}

export default MojiBodovi;