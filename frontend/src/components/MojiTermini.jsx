import React, { useEffect, useState } from "react";
import { API_URL } from "../config";
import "./MojiTermini.css";

function MojiTermini() {
    const [termsData, setTermsData] = useState(null); // Data from /dashboard/student
    const [pointsData, setPointsData] = useState(null); // Data from /student/moji-bodovi
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchResults = async () => {
            try {
                const [termsResponse, pointsResponse] = await Promise.all([
                    fetch(`${API_URL}/dashboard/student`, {
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
                ]); // ZOVI student/termini da dobis natrag TermDTO objekt, * pored onoga sto ti treba
                // TermDTO izgleda ovako: (Integer maxPoints*, LocalDateTime termStart*, LocalDateTime termEnd*, LocationDTO location, ActivityTypeDTO activityType)
                // LocationDTO izgleda ovako : (String locationName*, String address)
                // ActivityTypeDTO izgleda ovako : (String activityTypeName*)

                // Check for errors in both responses
                if (!termsResponse.ok) {
                    throw new Error(`Error fetching terms: ${termsResponse.statusText}`);
                }
                if (!pointsResponse.ok) {
                    throw new Error(`Error fetching points: ${pointsResponse.statusText}`);
                }

                // Parse both responses
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
                                <p>Datum: {term.date}</p>
                                <p>Vrijeme: {term.time}</p>
                                <p>Predmet: {term.subject}</p>
                                <p>Lokacija: {term.location}</p>
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p>Nema nadolazećih termina.</p>
                )}
            </div>

            {/* Display Points Data */}
            <div className="points-section">
                <div className="scores-section">
                    <h3>Prošli prijavljeni termini:</h3>
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
        </div>
    );
}

export default MojiTermini;
