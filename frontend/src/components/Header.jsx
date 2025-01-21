import {Link, useLocation, useNavigate} from "react-router-dom";
import "./Header.css";
import React, { useEffect } from "react";

function Header({ isLoggedIn, setIsLoggedIn, role }) {
    const navigate = useNavigate();
    const location = useLocation();
    
    const Logout = async () => {
        setIsLoggedIn(false);
        localStorage.removeItem("jwt_token");
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
                            <Link to={'/student/obavijesti'} className={location.pathname === '/obavijesti' ? "link current montserrat-regular" : "link montserrat-regular"}>Obavijesti</Link>
                            <button className="montserrat-regular link" onClick={Logout}>Odjava</button>
                        </>
                    )}
                    {role == "activity_leader" &&(
                        <>
                            <Link to={'/voditelj/dashboard'} className="link">Dashboard</Link>
                            <Link to={'/voditelj/moja-aktivnost'} className="link">Moja aktivnost</Link>
                            <Link to={'/voditelj/evidencije'} className="link">Evidencije</Link>
                            <Link to={'/voditelj/novi-termin'} className="link">Novi termin</Link>
                            <Link to={'/voditelj/obavijesti'} className="link">Obavijesti</Link>
                            <button className="montserrat-regular" onClick={Logout}>Odjava</button>
                        </>
                    )}
                    {role == "teacher" && (
                        <>
                            <Link to={'/nastavnik/dashboard'} className="link">Dashboard</Link>
                            <Link to={'/nastavnik/studenti'} className="link">Studenti</Link>
                            <Link to={'/nastavnik/aktivnosti'} className="link">Aktivnosti</Link>
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