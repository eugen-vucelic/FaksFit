import "./Dashboard.css"
import React from "react";
import {Link} from "react-router-dom";
import { useLocation } from 'react-router-dom';

function Dashboard(props) {

    const {isLoggedIn} = props;
    const location = useLocation();

    return (
        (isLoggedIn && location.pathname === '/') ?
        <div className="Dashboard">
            <div className="personal-info">
                <h2 className={"montserrat-medium"}>Ime Prezime (0036545654)</h2>
                <h3 className={"montserrat-semibold-italic"}>prof. Ime Prezime <Link to={'mail'}>mail</Link></h3>
            </div>
            <div className="bar">
                <div className="progress-bar-container">
                    <div className="progress-bar"></div>
                </div>
                <p>x/100 bodova</p>
            </div>
            <div className="activities montserrat-regular">
                <p>Dostupne aktivnosti:</p>
            </div>
            <div className="activity-buttons" >
                <button className="activity-button">Sljemenske ture</button>
                <button className="activity-button">Nogomet</button>
                <button className="activity-button">Veslanje</button>
                <button className="activity-button">Rukomet</button>
            </div>
            <div className="activity-window">
                <div className="window">
                    <h3>Nadolazeći termini za ....  :</h3>
                    <div className="activity montserrat-regular">
                        <p>pon 17.11.2024</p>
                        <p>17:00-18:00</p>
                        <p>x slobodnih mjesta</p>
                        <a>prijavi</a>
                    </div>
                </div>
            </div>
        </div> : null
    )
}

export default Dashboard