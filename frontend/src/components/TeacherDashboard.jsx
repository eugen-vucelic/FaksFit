import React, { useEffect, useState } from "react";
import './TeacherDashboard.css';

function TeacherDashboard() {
    // Set up states for holding teacher data and loading/error states
    const [teacherData, setTeacherData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    // Fetch teacher data on component mount
    useEffect(() => {
        const fetchTeacherData = async () => {
            try {
                const response = await fetch('http://localhost:8080/nastavnik/dashboard', {
                    method: "GET",
                    credentials: "include",
                    headers: {
                        "Content-Type": "application/json",
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

    const { firstName, lastName, email, officeLocation, students } = teacherData;

    return (
        <div className="teacher-dashboard">
            <h1>Dobrodošli, {firstName} {lastName}</h1>

            <div className="teacher-info">
                <p><strong>Email:</strong> {email}</p>
                <p><strong>Office Location:</strong> {officeLocation}</p>
            </div>

            <div className="students-info">
                <h3>Students</h3>
                {students && students.length > 0 ? (
                    <ul>
                        {students.map((student, index) => (
                            <li key={index}>
                                <strong>{student.firstName} {student.lastName}</strong> - {student.email}
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p>No students assigned to you.</p>
                )}
            </div>
        </div>
    );
}

export default TeacherDashboard;
