import {Link, useLocation, useNavigate} from "react-router-dom";
import "./Header.css";
import React, { useEffect } from "react";

function Header({ isLoggedIn, setIsLoggedIn, role }) {
    const navigate = useNavigate();
    const location = useLocation();
    
    const Logout = async () => {
        setIsLoggedIn(false);
        navigate("/");
        }

    return (
        <header>
            <div className="title">
                <h1 className={"montserrat-semibold-italic"}>FaksFit</h1>
            </div>
            {location.pathname !== '/' && location.pathname !== '/registracija' &&
                <div className="nav">
                    {role == "student" && (
                        <>
                            <Link to={'/student/dashboard'} className={location.pathname === '/student/dashboard' ? "link current montserrat-regular" : "link montserrat-regular"}>Dashboard</Link>
                            <Link to={'/student/prijava-termina'} className={location.pathname === '/prijava-termina' ? "link current montserrat-regular" : "link montserrat-regular"}>Prijava termina</Link>                            
                            <Link to={'student/moji-termini'} className={location.pathname === '/moji-termini' ? "link current montserrat-regular" : "link montserrat-regular"}>Moji termini</Link>
                            <Link to={'/student/moji-bodovi'} className={location.pathname === '/moji-bodovi' ? "link current montserrat-regular" : "link montserrat-regular"}>Moji bodovi</Link>
                            <Link to={'/student/profile'} className={location.pathname === '/profil' ? "link current montserrat-regular" : "link montserrat-regular"}>Profil</Link>
                            <button className="montserrat-regular link" onClick={Logout}>Odjava</button>
                        </>
                    )}
                    {role == "activity_leader" &&(
                        <>
                            <Link to={'/activity_leader/dashboard'} className="link">Dashboard</Link>
                            <Link to={'/activity_leader/noviTermin'} className="link">Novi termin</Link>
                            <button className="montserrat-regular" onClick={Logout}>Odjava</button>
                        </>
                    )}
                    {role == "teacher" && (
                        <>
                            <Link to={'/teacher/dashboard'} className="link">Dashboard</Link>
                            <Link to={'/teacher/studenti'} className="link">Studenti</Link>
                            <button className="montserrat-regular" onClick={Logout}>Odjava</button>
                        </>
                    )}
                </div>
            }
            <div className="line"></div>
        </header>
    )
}

export default Header;