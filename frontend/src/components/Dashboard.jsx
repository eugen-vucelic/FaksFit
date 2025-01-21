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
                    'Content-Type': 'application/json'
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
    

    // const useURLToken = () => {
    //     const location = useLocation();
    //     const navigate = useNavigate();

    //     React.useEffect(() => {
    //         // Get token from URL parameters
    //         const params = new URLSearchParams(location.search);
    //         const token = params.get('token');
            
    //         if (token) {
    //         // Store the token
    //         localStorage.setItem('jwt_token', token);
            
    //         // Clean up the URL by removing the token
    //         const newURL = window.location.pathname;
    //         navigate(newURL, { replace: true });
    //         }
    //     }, [location, navigate]);
    // };

    // const AuthUtils = {
    //     TOKEN_KEY: 'jwt_token',
    //     ROLES: {
    //       STUDENT: 'STUDENT',
    //       LEADER: 'ACTIVITY_LEADER',
    //       TEACHER: 'TEACHER'
    //     },
      
    //     getToken: () => localStorage.getItem(AuthUtils.TOKEN_KEY),
      
    //     setToken: (token) => {
    //       if (token) {
    //         localStorage.setItem(AuthUtils.TOKEN_KEY, token);
    //       } else {
    //         localStorage.removeItem(AuthUtils.TOKEN_KEY);
    //       }
    //     },
      
    //     getDecodedToken: () => {
    //       const token = AuthUtils.getToken();
    //       if (!token) return null;
    //       try {
    //         return jwtDecode(token);
    //       } catch (error) {
    //         console.error('Token decode error:', error);
    //         return null;
    //       }
    //     },
      
    //     isTokenValid: (token) => {
    //       if (!token) return false;
    //       try {
    //         const decoded = jwtDecode(token);
    //         return decoded.exp * 1000 > Date.now();
    //       } catch {
    //         return false;
    //       }
    //     },
      
    //     getRole: () => {
    //       const decoded = AuthUtils.getDecodedToken();
    //       return decoded?.role?.toLowerCase() || null;
    //     },
      
    //     getEmail: () => {
    //       const decoded = AuthUtils.getDecodedToken();
    //       return decoded?.sub || null;
    //     }
    // };

    // React.useEffect(() => {
    //     const checkUserStatus = async () => {
    //         try {
    //             const token = AuthUtils.getToken();
                
    //             if (!token || !AuthUtils.isTokenValid(token)) {
    //             //   handleLogout();
    //             //   setLoadingUser(false);
    //             return;
    //             }
        
    //             // Verify token with backend
    //             // const response = await fetch(`localhost:8080/auth/verify`, {
    //             //   headers: {
    //             //     'Accept': 'application/json',
    //             //     'Authorization': `Bearer ${token}`,
    //             //   },
    //             // });
            
    //             const userRole = AuthUtils.getRole();
                
    //             // setIsLoggedIn(true);
    //             setRole(userRole);
    //         } catch (error) {
    //             console.error('Auth check error:', error);
    //             // handleLogout();
    //         } finally {
    //             // setLoadingUser(false);
    //       }
    //     };
    
    //     checkUserStatus();
    
    //     // Token expiration checker
    //     const tokenCheckInterval = setInterval(() => {
    //       const token = AuthUtils.getToken();
    //       if (token && !AuthUtils.isTokenValid(token)) {
    //         handleLogout();
    //       }
    //     }, 60000); // Check every minute
    
    //     return () => clearInterval(tokenCheckInterval);
    // }, []);

    // useURLToken();

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
