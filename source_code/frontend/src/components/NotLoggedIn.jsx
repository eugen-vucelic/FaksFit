import {Link} from "react-router-dom";
import "./NotLoggedIn.css";

function NotLoggedIn({isLoggedIn, props}) {

    return (
        !isLoggedIn &&
            <div className="container">
                <p className="montserrat-medium">Aplikacija za nastavu Tjelesne i zdravstvene kulture na Sveučilištu u Zagrebu</p>
                <div className="arrow-box">
                    <p className="montserrat-medium-italic">Niste prijavljeni u sustav.</p>
                    <p className="montserrat-bold"><Link to={'/prijava'}>Prijava</Link> | <Link to={'/registracija'}>Registracija</Link></p>
                </div>
            </div>
    )
}

export default NotLoggedIn;