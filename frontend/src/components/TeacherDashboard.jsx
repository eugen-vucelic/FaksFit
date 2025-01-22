import React, { useEffect, useState } from "react";
import './TeacherDashboard.css';

function TeacherDashboard() {
    const [teacherData, setTeacherData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchTeacherData = async () => {
            try {
                const response = await fetch('http://localhost:8080/nastavnik/current', {
                    method: "GET",
                    credentials: "include",
                    headers: {
                        "Content-Type": "application/json",
                        'Authorization': `Bearer ${localStorage.getItem('jwt_token')}`
                    },
                });

                if (!response.ok) {
                    throw new Error("Failed to fetch teacher data");
                }

                const data = await response.json();
                setTeacherData(data);
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };

        fetchTeacherData();
    }, []);

    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {error}</div>;

    if (!teacherData) return <div>No teacher data found.</div>;

    const { firstname, lastname, email, officeLocation, students } = teacherData;

    return (
        <div className="teacher-dashboard">
            <h1>Dobrodošli, {firstname} {lastname}</h1>

            <div className="teacher-info">
                <p><strong>Email:</strong> {email}</p>
                <p><strong>Lokacija ureda:</strong> {officeLocation}</p>
            </div>

            <div className="students-info">
                <h3>Studenti</h3>
                {students && students.length > 0 ? (
                    <ul>
                        {students.map((student, index) => (
                            <li key={index}>
                                <strong>{student.firstName} {student.lastName}</strong> - {student.email}
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p>Trenutno nemate svojih studenata.</p>
                )}
            </div>
        </div>
    );
}

export default TeacherDashboard;
