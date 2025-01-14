import React, { useEffect, useState } from "react";
import { API_URL } from "../config";
import "./Notification.css";

function Obavijesti() {
    const [notifications, setNotifications] = useState([]);
    const [selectedNotification, setSelectedNotification] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchNotifications = async () => {
        try {
            const response = await fetch(`${API_URL}/student/obavijesti`, {
            method: "GET",
            credentials: "include",
            headers: {
                "Content-Type": "application/json",
            },
            });

            if (!response.ok) {
            throw new Error(`Error: ${response.statusText}`);
            }

            const result = await response.json();
            const sortedNotifications = result.notifications.sort(
                (a, b) => new Date(b.date) - new Date(a.date)
            );
            setNotifications(sortedNotifications);
            setSelectedNotification(sortedNotifications[0]);
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
        };

        fetchNotifications();
    }, []);


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

    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {error}</div>;

    return (
    <div className="notification-flex montserrat-regular" style={{display: "flex"}}>
        <div className="" style={{flex: 1, border: "1px solid #blue", borderRadius: "8px", height: "40px", marginLeft: "20px"}}>
            <h2>Obavijesti</h2>
            {notifications.map((notification, index) => (
                <div key={index} className="" style={{cursor: "pointer"}} onClick={() => setSelectedNotification(notification)}>
                    <h3 className="">{notification.title}</h3>
                </div>
            ))}
        </div>
        <div style={{flex: 2}}>
            {selectedNotification ? (
                <>
                    <h2>{selectedNotification.title}</h2>
                    <p className="">{selectedNotification.text}</p>
                    <p className="">
                    { formatDate(selectedNotification.date)}{" "}
                    { formatTime(selectedNotification.date)}
                    </p>
                </> ):(
                    <p>Odaberite obavijest za prikaz</p>
                )}
        </div>
    </div>
  );
}

export default Obavijesti;