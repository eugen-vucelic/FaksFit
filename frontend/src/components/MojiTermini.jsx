import React, { useEffect, useState } from "react";
import { API_URL } from "../config";
import "./MojiTermini.css";

function MojiTermini() {
    const [terms, setTerms] = useState([]);  // Changed to direct array
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
                            'Authorization': `Bearer ${localStorage.getItem('jwt_token')}`
                        },
                    }),
                    fetch(`${API_URL}/student/moji-bodovi`, {
                        method: "GET",
                        credentials: "include",
                        headers: {
                            "Content-Type": "application/json",
                            'Authorization': `Bearer ${localStorage.getItem('jwt_token')}`
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

                setTerms(termsResult);  // Store array directly
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

    return (
        <div className="terms-page">
            <h1 className="montserrat-bold">Moji Termini</h1>

            <div className="terms-section montserrat-regular">
                {terms && terms.length > 0 ? (
                    <ul>
                        {terms.map((term) => (  // Changed to use termId as key
                            <li key={term.termId} className="term-item">
                                <strong>Termin {term.termId}:</strong>
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
                <h3 className="montserrat-semibold">Prošli prijavljeni termini:</h3>
                {pointsData && pointsData.scores && pointsData.scores.terms && pointsData.scores.terms.length > 0 ? (
                    <ul>
                        {pointsData.scores.terms.map((term, index) => (
                            <li key={index}>
                                <strong className="montserrat-regular">Termin {index + 1}:</strong> {term} bodova
                            </li>
                        ))}
                        {pointsData.scores.extra > 0 && (
                            <li>
                                <strong className="montserrat-regular">Dodatni bodovi od nastavnika:</strong> {pointsData.scores.extra}
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