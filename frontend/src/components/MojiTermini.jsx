import React, { useEffect, useState } from "react";
import { API_URL } from "../config";
import "./MojiTermini.css";

function MojiTermini() {
    const [termsData, setTermsData] = useState(null);
    const [pointsData, setPointsData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchResults = async () => {
            try {
                const [termsResponse, pointsResponse] = await Promise.all([
                    fetch(`${API_URL}/student/termini`, {
                        method: "GET",
                        credentials: "include",
                        headers: {
                            "Content-Type": "application/json",
                        },
                    }),
                    fetch(`${API_URL}/student/moji-bodovi`, {
                        method: "GET",
                        credentials: "include",
                        headers: {
                            "Content-Type": "application/json",
                        },
                    }),
                ]);

                if (!termsResponse.ok) {
                    throw new Error(`Error fetching terms: ${termsResponse.statusText}`);
                }
                if (!pointsResponse.ok) {
                    throw new Error(`Error fetching points: ${pointsResponse.statusText}`);
                }

                const termsResult = await termsResponse.json();
                const pointsResult = await pointsResponse.json();

                setTermsData(termsResult);
                setPointsData(pointsResult);
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

    const { terms } = termsData || {};
    const { scores } = pointsData || {};

    return (
        <div className="terms-page">
            <h1>Moji Termini</h1>

            <div className="terms-section">
                {terms && terms.length > 0 ? (
                    <ul>
                        {terms.map((term, index) => (
                            <li key={index} className="term-item">
                                <strong>Termin {index + 1}:</strong>
                                <p>Maksimalni bodovi: {term.maxPoints}</p>
                                <p>Početak: {new Date(term.termStart).toLocaleString()}</p>
                                <p>Kraj: {new Date(term.termEnd).toLocaleString()}</p>
                                <p>Lokacija: {term.location.locationName} ({term.location.address})</p>
                                <p>Vrsta aktivnosti: {term.activityType.activityTypeName}</p>
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p>Nema nadolazećih termina.</p>
                )}
            </div>

            {/* Points Section */}
            <div className="points-section">
                <h3>Prošli prijavljeni termini:</h3>
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
                    <p>Nema dostupnih bodova.</p>
                )}
            </div>
        </div>
    );
}

export default MojiTermini;
