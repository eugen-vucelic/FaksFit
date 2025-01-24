import "./Dashboard.css";
import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { API_URL } from "../config";

import { jwtDecode } from 'jwt-decode';
import { useLocation, useNavigate } from "react-router-dom";

function Dashboard(props) {
    const [dashboardData, setDashboardData] = useState(null);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);
    const [role, setRole] = React.useState(null);

    const fetchDashboardData = () => {
        setLoading(true);
        fetch(`${API_URL}/dashboard/student`, {
            method: 'GET',
            credentials: 'include',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('jwt_token')}`
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to fetch dashboard data');
                }
                return response.json();
            })
            .then(data => {
                setDashboardData(data);
                setError(null);  // Clear errors on success
            })
            .catch(error => {
                console.error('Error fetching dashboard data:', error);
                setError(error);
            })
            .finally(() => setLoading(false));
    };

    const handleTermUnenrollment = async (termId) => {
        try {
            const response = await fetch(`${API_URL}/termini/odjava/${termId}`, {
                method: 'DELETE',
                credentials: 'include',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem('jwt_token')}`
                }
            });
    
            if (response.ok) {
                window.location.reload();
            } else {
                throw new Error('Odjava nije uspjela');
            }
        } catch (error) {
            console.error('Error:', error);
            alert('Greška prilikom odjave s termina. Pokušajte ponovno.');
        }
    };
    
    // In your JSX:
    <button onClick={() => handleTermUnenrollment(termId)}>Odjavi</button>
    
    useEffect(() => {
        fetchDashboardData();
    }, []);

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return (
            <div>
                <p>Error: {error.message}</p>
                <button onClick={fetchDashboardData}>Retry</button>
            </div>
        );
    }

    function formatDate(dateTimeString) {
        if (!dateTimeString) return 'Unknown date';
        const options = { weekday: 'short', day: 'numeric', month: 'numeric', year: 'numeric' };
        const date = new Date(dateTimeString);
        return date.toLocaleDateString('hr-HR', options);
    }

    function formatTime(dateTimeString) {
        if (!dateTimeString) return 'Unknown time';
        const options = { hour: '2-digit', minute: '2-digit' };
        const date = new Date(dateTimeString);
        return date.toLocaleTimeString('hr-HR', options);
    }

    return (
        <div className="Dashboard">
            {/* Personal Information */}
            <div className="personal-info">
                <h2 className="montserrat-medium">
                    {dashboardData.firstName ?? 'Student'} {dashboardData.lastName ?? ''} ({dashboardData.JMBAG ?? 'N/A'})
                </h2>
                <h3 className="montserrat-semibold-italic">
                    prof. {dashboardData.teacher?.firstName ?? ''} {dashboardData.teacher?.lastName ?? ''}{' '}
                    <a href={`mailto:${dashboardData.teacher?.email ?? ''}`}>{dashboardData.teacher?.email ?? 'Email not available'}</a>
                </h3>
            </div>

            {/* Progress Bar */}
            <div className="bar">
                <div className="progress-bar-container">
                    <div
                        className="progress-bar"
                        style={{ width: `${dashboardData.totalPoints ?? 0}%` }}
                    ></div>
                </div>
                <p>{dashboardData.totalPoints ?? 0}/100 bodova</p>
            </div>

            {/* Activity Window */}
            <div className="activity-window">
                <div className="activities montserrat-regular">
                    <p>Nadolazeće aktivnosti:</p>
                </div>
                <div className="window">
                    {dashboardData.terminList && dashboardData.terminList.length > 0 ? (
                        dashboardData.terminList.map((term, index) => (
                            <div
                                key={index}
                                className="activity montserrat-regular"
                            >
                                <p>{formatDate(term.termStart)}</p>
                                <p>
                                    {formatTime(term.termStart)} - {formatTime(term.termEnd)}
                                </p>
                                <p>{term.activityType?.activityTypeName ?? 'Unknown Activity'}</p>
                                <p>{term.maxPoints ?? 0} bodova</p>
                                <button onClick={() => handleTermUnenrollment(term.termId)}>Odjavi</button>
                            </div>
                        ))
                    ) : (
                        <p>Nema nadolazećih aktivnosti.</p>
                    )}
                </div>
            </div>
        </div>
    );
}

export default Dashboard;
