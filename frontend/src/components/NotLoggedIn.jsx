import React from 'react';
import "./NotLoggedIn.css";
import { useLocation } from 'react-router-dom';
import { useNavigate, Navigate } from 'react-router-dom';
import { API_URL } from '../config';

function NotLoggedIn(props) {
    const { isLoggedIn, passedOauth, setPassedOAuth } = props;
    const location = useLocation();
    const navigate = useNavigate();

    if (isLoggedIn) {
        return <Navigate to="/dashboard/student" />;
    }

    if (passedOauth) {
        return <Navigate to="/registracija" />;
    }

    const handleLoginClick = () => {
        setPassedOAuth(true); // Update OAuth status
        // Redirect to external URL for login
        window.location.href = `${API_URL}/dashboard/student`;
    };

    return (
        (!isLoggedIn && location.pathname === '/') &&
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
