import React, { useState, useEffect } from 'react';
import "./NotLoggedIn.css";
import { useNavigate, Navigate } from 'react-router-dom';
import { API_URL } from '../config';

function NotLoggedIn({ isLoggedIn, passedOauth, setPassedOAuth }) {
    const navigate = useNavigate();
    const [shouldCheck, setShouldCheck] = useState(false);

    useEffect(() => {
        const timer = setTimeout(() => {
            setShouldCheck(true);
        }, 5000); // 5 seconds delay

        return () => clearTimeout(timer);
    }, []);

    useEffect(() => {
        if (shouldCheck && isLoggedIn) {
            navigate('/registracija', { replace: true });
        }
    }, [shouldCheck, isLoggedIn, navigate]);

    const handleLoginClick = () => {
        setPassedOAuth(true);
        window.location.href = `${API_URL}/oauth2/authorization/google`;
    };

    // Early return if logged in but still within 5-second window
    if (isLoggedIn && !shouldCheck) {
        return <div>Checking authentication...</div>;
    }

    return (
        !isLoggedIn &&
        <div className="container">
            <p className="montserrat-medium desc">Aplikacija za nastavu Tjelesne i zdravstvene kulture na Sveučilištu u Zagrebu</p>
            <div className="arrow-box">
                <p className="montserrat-medium-italic">Niste prijavljeni u sustav.</p>
                <p className="montserrat-bold">
                    <button onClick={handleLoginClick} className="link-button montserrat-bold">
                        Prijava
                    </button>
                </p>
            </div>
        </div>
    );
}

export default NotLoggedIn;