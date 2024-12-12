import React from 'react';
import "./NotLoggedIn.css";
import { useLocation } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import { API_URL } from '../config';

function NotLoggedIn(props) {
    const {isLoggedIn} = props;
    const location = useLocation();
    const navigate = useNavigate();

    return (
        (!isLoggedIn && location.pathname === '/') &&
        <div className="container">
            <p className="montserrat-medium">Aplikacija za nastavu Tjelesne i zdravstvene kulture na Sveučilištu u Zagrebu</p>
            <div className="arrow-box">
                <p className="montserrat-medium-italic">Niste prijavljeni u sustav.</p>
                <p className="montserrat-bold">
                    <a href={`${API_URL}/dashboard/student`}>Prijava</a>
                </p>
            </div>
        </div>
    )
}

export default NotLoggedIn