import {Link} from "react-router-dom";
import "./Header.css";

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
                <div className="nav">
                    {role === "Student" &&
                        <>
                            <Link to={'/dashboard'}>Dashboard</Link>
                            <Link to={'/moji-termini'}>Moji termini</Link>
                            <Link to={'/moji-bodovi'}>Moji bodovi</Link>
                            <Link to={'/profil'}>Profil</Link>
                            <Link to={'/obavijesti'}>Obavijesti</Link>
                            <button onClick={logout}>Odjava</button>
                        </>
                    }
                    {role === "Voditelj" &&
                        <>
                            <Link to={'/dashboard'}>Dashboard</Link>
                            <Link to={'/moja-aktivnost'}>Moja aktivnost</Link>
                            <Link to={'/evidencije'}>Evidencije</Link>
                            <Link to={'/novi-termin'}>Novi termin</Link>
                            <Link to={'/obavijesti'}>Obavijesti</Link>
                            <button onClick={logout}>Odjava</button>
                        </>
                    }
                    {role === "Nastavnik" &&
                        <>
                            <Link to={'/dashboard'}>Dashboard</Link>
                            <Link to={'/studenti'}>Studenti</Link>
                            <Link to={'/aktivnosti'}>Aktivnosti</Link>
                            <button onClick={logout}>Odjava</button>
                        </>
                    }
                </div>
            }
        </header>
    )
}

export default Header;