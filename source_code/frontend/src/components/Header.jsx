import {Link} from "react-router-dom";
import "./Header.css";
import React from 'react';

function Header({isLoggedIn, role, props}) {

    function logout() {
        fetch("/api/logout").then(() => {
            props.onLogout();
        });
    }

    return (
        <header>
            <div className="title">
                <h1 className={"montserrat-semibold-italic"}>FaksFit</h1>
            </div>
            {isLoggedIn &&
                <div className="nav montserrat-regular">
                    {role === "Student" &&
                        <>
                            <Link to={'/dashboard'} className="link">Dashboard</Link>
                            <Link to={'/moji-termini'} className="link">Moji termini</Link>
                            <Link to={'/moji-bodovi'} className="link">Moji bodovi</Link>
                            <Link to={'/profil'} className="link">Profil</Link>
                            <Link to={'/obavijesti'} className="link">Obavijesti</Link>
                            <button onClick={logout}>Odjava</button>
                        </>
                    }
                    {role === "Voditelj" &&
                        <>
                            <Link to={'/dashboard'} className="link">Dashboard</Link>
                            <Link to={'/moja-aktivnost'} className="link">Moja aktivnost</Link>
                            <Link to={'/evidencije'} className="link">Evidencije</Link>
                            <Link to={'/novi-termin'} className="link">Novi termin</Link>
                            <Link to={'/obavijesti'} className="link">Obavijesti</Link>
                            <button onClick={logout} className="link">Odjava</button>
                        </>
                    }
                    {role === "Nastavnik" &&
                        <>
                            <Link to={'/dashboard'} className="link">Dashboard</Link>
                            <Link to={'/studenti'} className="link">Studenti</Link>
                            <Link to={'/aktivnosti'} className="link">Aktivnosti</Link>
                            <button onClick={logout}>Odjava</button>
                        </>
                    }
                </div>
            }
            <div className="line"></div>
        </header>
    )
}

export default Header;