import React, { useState, useEffect } from "react";
import { API_URL } from '../config';

function ListaTermina(){
    const [terms, setTerms] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchTerms = async () => {
        try {
            const response = await fetch(`${API_URL}/termini/svi-termini`, {
            method: "GET",
            credentials: "include",
            });

            if (!response.ok) {
                throw new Error(`Error: ${response.statusText}`);
            }

            const data = await response.json();
            data.sort((a, b) => new Date(a.start) - new Date(b.start));
            setTerms(data);
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
        };

        fetchTerms();
    }, []);

    const handleSignUp = async (termId) => {
        try {
        const response = await fetch(`${API_URL}/termini/upis-na-termin/${termId}`, {
            method: "POST",
            credentials: "include",
        });

        if (response.ok) {
            alert("Uspješno ste se prijavili na termin!");
        } else {
            const errorData = await response.text();
            alert(`Greška: ${errorData}`);
        }
        } catch (error) {
        alert("Došlo je do greške. Pokušajte ponovno.");
        }
    };

    if (loading) {
        return <p>Učitavanje termina...</p>;
    }

    if (error) {
        return <p>Greška: {error}</p>;
    }

    if (terms.length === 0) {
        return <p>Nema dostupnih termina.</p>;
    }

    return (
        <div style={{ display: "flex", flexWrap: "wrap", gap: "16px" }}>
        {terms.map((term) => (
            <div
            key={term.termId}
            style={{
                border: "1px solid #ddd",
                borderRadius: "8px",
                padding: "16px",
                width: "300px",
                boxShadow: "0 2px 4px rgba(0, 0, 0, 0.1)",
            }}
            >
            <h3>{term.activityType.activityTypeName}</h3>
            <p>
                <strong>Datum:</strong>{" "}
                {new Date(term.termStart).toLocaleDateString("hr-HR")}
            </p>
            <p>
                <strong>Vrijeme:</strong>{" "}
                {new Date(term.termStart).toLocaleTimeString("hr-HR")} -{" "}
                {new Date(term.termEnd).toLocaleTimeString("hr-HR")}
            </p>
            <p>
                <strong>Lokacija:</strong> {term.location.locationName}
            </p>
            <p>
                <strong>Maksimalni bodovi:</strong> {term.maxPoints}
            </p>
            <button
                onClick={() => handleSignUp(term.termId)}
                style={{
                backgroundColor: "#28a745",
                color: "white",
                border: "none",
                padding: "10px 16px",
                borderRadius: "4px",
                cursor: "pointer",
                }}
            >
                Prijavi se
            </button>
            </div>
        ))}
        </div>

    );
}

export default ListaTermina;
