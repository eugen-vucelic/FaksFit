import React from 'react';
import "./NotLoggedIn.css";
import { useNavigate, Navigate } from 'react-router-dom';
import { API_URL } from '../config';

function NotLoggedIn({ isLoggedIn, passedOauth }) {
    const navigate = useNavigate();
    if (isLoggedIn) {
        return <Navigate to="/student/dashboard" />;
    }

    if (passedOauth) {
        return <Navigate to="/registracija" />;
    }

    const handleLoginClick = () => {
        window.location.href = `${API_URL}/oauth2/authorization/google`; // ovo je sad dobro, vise nije callback url
    };

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
