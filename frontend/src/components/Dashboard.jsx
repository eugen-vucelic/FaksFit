import "./Dashboard.css";
import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { API_URL } from '../config';

function Dashboard(props) {
    const {isLoggedIn, setIsLoggedIn} = props;
    const [dashboardData, setDashboardData] = useState(null);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        fetch(`${API_URL}/dashboard/student`, {
            method: 'GET',
            credentials: 'include',
            headers: {
                'Accept': 'application/json'
            }
        })
            .then(response => {
                if (!response.ok) {
                    navigate('/');
                    throw new Error('Failed to fetch dashboard data');
                }
                return response.json();
            })
            .then(data => {
                setDashboardData(data);
            })
            .catch(error => {
                navigate("/");
                console.error('Error fetching dashboard data:', error);
                setIsLoggedIn(false);
                setError(error);
            });
    }, [setIsLoggedIn, navigate]);

    if (error) {
        return <div>Error: {error.message}</div>;
    }

    if (!dashboardData) {
        return <div>Loading...</div>;
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
            <div className="available montserrat-regular">
                <p>Dostupne aktivnosti:</p>
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
                                <a href="#">prijavi</a>
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