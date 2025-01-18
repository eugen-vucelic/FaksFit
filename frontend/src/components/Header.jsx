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
                    {role == "Student" && (
                        <>
                            <Link to={'/student/dashboard'} className={location.pathname === '/student/dashboard' ? "link current montserrat-regular" : "link montserrat-regular"}>Dashboard</Link>
                            <Link to={'student/moji-termini'} className={location.pathname === '/moji-termini' ? "link current montserrat-regular" : "link montserrat-regular"}>Moji termini</Link>
                            <Link to={'/student/moji-bodovi'} className={location.pathname === '/moji-bodovi' ? "link current montserrat-regular" : "link montserrat-regular"}>Moji bodovi</Link>
                            <Link to={'/student/profile'} className={location.pathname === '/profil' ? "link current montserrat-regular" : "link montserrat-regular"}>Profil</Link>
                            <Link to={'/student/obavijesti'} className={location.pathname === '/obavijesti' ? "link current montserrat-regular" : "link montserrat-regular"}>Obavijesti</Link>
                            <button className="montserrat-regular link" onClick={Logout}>Odjava</button>
                        </>
                    )}
                    {role === "Voditelj" &&(
                        <>
                            <Link to={'/dashboard/voditelj'} className="link">Dashboard</Link>
                            <Link to={'/moja-aktivnost'} className="link">Moja aktivnost</Link>
                            <Link to={'/evidencije'} className="link">Evidencije</Link>
                            <Link to={'/novi-termin'} className="link">Novi termin</Link>
                            <Link to={'/obavijesti'} className="link">Obavijesti</Link>
                            <button className="montserrat-regular" onClick={Logout}>Odjava</button>
                        </>
                    )}
                    {role === "Nastavnik" && (
                        <>
                            <Link to={'/dashboard/nastavnik'} className="link">Dashboard</Link>
                            <Link to={'/studenti'} className="link">Studenti</Link>
                            <Link to={'/aktivnosti'} className="link">Aktivnosti</Link>
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