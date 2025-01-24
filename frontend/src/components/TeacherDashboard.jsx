import React, { useEffect, useState } from "react";
import './TeacherDashboard.css';
import { API_URL } from "../config";

function TeacherDashboard() {
    const [teacherData, setTeacherData] = useState(null);
    const [studentsData, setStudentsData] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchTeacherData = async () => {
            try {
                const teacherResponse = await fetch(`${API_URL}}/nastavnik/current`, {
                    method: "GET",
                    credentials: "include",
                    headers: {
                        "Content-Type": "application/json",
                        'Authorization': `Bearer ${localStorage.getItem('jwt_token')}`
                    },
                });

                if (!teacherResponse.ok) {
                    throw new Error("Failed to fetch teacher data");
                }

                const teacherData = await teacherResponse.json();
                setTeacherData(teacherData);

                // Fetch students data
                const studentsResponse = await fetch(`${API_URL}}/nastavnik/svi-studenti`, {
                    method: "GET",
                    credentials: "include",
                    headers: {
                        "Content-Type": "application/json",
                        'Authorization': `Bearer ${localStorage.getItem('jwt_token')}`
                    },
                });

                if (!studentsResponse.ok) {
                    throw new Error("Failed to fetch students data");
                }

                const studentsData = await studentsResponse.json();
                setStudentsData(studentsData);

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

    const { firstname, lastname, email, officeLocation } = teacherData;

    return (
        <div className="teacher-dashboard">
            <h1>Dobrodošli, {firstname} {lastname}</h1>

            <div className="teacher-info">
                <p><strong>Email:</strong> {email}</p>
                <p><strong>Lokacija ureda:</strong> {officeLocation}</p>
            </div>

            <div className="students-info">
                <h3>Studenti</h3>
                {studentsData && studentsData.length > 0 ? (
                    <ul>
                        {studentsData.map((student, index) => (
                            <li key={index}>
                                <strong>{student.firstName} {student.lastName}</strong> - {student.email}
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p>Trenutno nemate studenata.</p>
                )}
            </div>
        </div>
    );
}

export default TeacherDashboard;
