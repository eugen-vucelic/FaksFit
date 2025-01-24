import React, { useEffect, useState } from "react";
import './TeacherStudents.css';
import { API_URL } from "../config";

function TeacherStudents() {
    const [students, setStudents] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchStudents = async () => {
            try {
                const response = await fetch(`${API_URL}/nastavnik/svi-studenti`, {
                    method: "GET",
                    credentials: "include",
                    headers: {
                        "Content-Type": "application/json",
                    },
                });

                const data = await response.json();

                setStudents(data);
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };

        fetchStudents();
    }, []);

    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {error}</div>;

    if (students.length === 0) return <div>No students found.</div>;

    function getText(totalPoints) {
        let lastDigit = totalPoints % 10;

        if (lastDigit === 1 && totalPoints !== 11) {
            return "bod";
        } else if (lastDigit >= 2 && lastDigit <= 4 && (totalPoints < 12 || totalPoints > 14)) {
            return "boda";
        } else {
            return "bodova";
        }
    }


    return (
        <div className="teacher-students">
            <h1>Lista studenata s bodovima</h1>

            <div className="students-info">
                {students.length > 0 ? (
                    <ul>
                        {students.map((student, index) => (
                            <li key={index}>
                                <strong>{student.firstName} {student.lastName}</strong> - {student.email} - {student.totalPoints} {getText(student.totalPoints)}
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p>Trenutno nema studenata u sustavu.</p>
                )}
            </div>
        </div>
    );
}

export default TeacherStudents;
