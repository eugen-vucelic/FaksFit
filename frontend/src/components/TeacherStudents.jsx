import React, { useEffect, useState } from "react";
import './TeacherStudents.css';

function TeacherStudents() {
    const [students, setStudents] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchStudents = async () => {
            try {
                const response = await fetch('http://localhost:8080/nastavnik/svi-studenti', {
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

    return (
        <div className="teacher-students">
            <h1>Lista studenata</h1>

            <div className="students-info">
                {students.length > 0 ? (
                    <ul>
                        {students.map((student, index) => (
                            <li key={index}>
                                <strong>{student.firstName} {student.lastName}</strong> - {student.email}
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
