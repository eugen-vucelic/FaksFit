import {Link} from "react-router-dom";
import React, { useEffect } from 'react';
import "./NotLoggedIn.css";
import { useLocation } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';

function NotLoggedIn(props) {

    const {isLoggedIn,setIsLoggedIn} = props;
    const location = useLocation();
    const navigate = useNavigate();

    useEffect(() => {
        setIsLoggedIn(false);
        if (isLoggedIn) {
            //navigate('/dashboard/student');
        }
    }, [isLoggedIn, navigate]);

    return (
        (!isLoggedIn && location.pathname === '/') &&
        <div className="container">
            <p className="montserrat-medium">Aplikacija za nastavu Tjelesne i zdravstvene kulture na Sveučilištu u Zagrebu</p>
            <div className="arrow-box">
                <p className="montserrat-medium-italic">Niste prijavljeni u sustav.</p>
                <p className="montserrat-bold"><a href="http://localhost:8080/dashboard/student">Prijava</a></p>
            </div>
        </div>
    )
}

export default NotLoggedIn;