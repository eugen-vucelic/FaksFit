import "./LeaderDashboard.css";
import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { API_URL } from "../config";

function LeaderDashboard(props) {
    const [dashboardData, setDashboardData] = useState(null);
    const [termData, setTermData] = useState(null);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);

    const fetchDashboardData = () => {
        setLoading(true);
        fetch(`${API_URL}}/voditelj/current`, {
            method: 'GET',
            credentials: 'include',
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
        <div className="LeaderDashboard">
            <div className="personal-info">
                <h2 className="montserrat-medium">
                    {dashboardData.firstName ?? 'Voditelj'} {dashboardData.lastName ?? ''}
                </h2>
                <h3 className="montserrat-semibold-italic">
                    Aktivnost: {dashboardData.activityTypeName ?? ''}
                </h3>
            </div>
            <div className="activity-window">
                <div className="activities montserrat-regular">
                </div>
                <div className="window">
                    {dashboardData.termini && dashboardData.termini.length > 0 ? (
                        dashboardData.termini.map((term, index) => (
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
                            </div>
                        ))
                    ) : (
                        <p>Nema nadolazećih termina za Vašu aktivnost.</p>
                    )}
                </div>
            </div>
        </div>
    );
}

export default LeaderDashboard;