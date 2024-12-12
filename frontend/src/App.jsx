import * as React from "react";
import './App.css'
import {createBrowserRouter, Outlet, RouterProvider} from "react-router-dom";
import Header from "./components/Header.jsx";
import Footer from "./components/Footer.jsx";
import NotLoggedIn from "./components/NotLoggedIn.jsx";
import Dashboard from "./components/Dashboard.jsx";
import Registration from "./components/Registration.jsx"
import { API_URL } from './config';

function App() {
    const [isLoggedIn, setIsLoggedIn] = React.useState(false);
    const [loadingUser, setLoadingUser] = React.useState(true);
    const [role, setRole] = React.useState(null);
    const [error, setError] = React.useState(null);

    const router = createBrowserRouter([
        {
            path: "/",
            element: <AppContainer isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} role={role}/>,
            children: [
                {
                    path: "registracija",
                    element: <Registration isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn}/>
                },
                {
                    path: "dashboard/student",
                    element: isLoggedIn ? 
                        <Dashboard isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn}/> : 
                        <Navigate to="/" replace />
                }
            ]
        }
    ]);

    React.useEffect(() => {
        console.log('Checking authentication status...');
        fetch(`${API_URL}/api/user`, {
            credentials: 'include',
            headers: {
                'Accept': 'application/json'
            }
        })
            .then(async response => {
                console.log('Auth response status:', response.status);
                
                if (response.status === 200) {
                    const userData = await response.json();
                    console.log('User data:', userData);
                    setIsLoggedIn(true);
                    setRole("Student");
                } else {
                    setIsLoggedIn(false);
                    setRole(null);
                }
            })
            .catch(error => {
                console.error('Auth error:', error);
                setError(error.message);
                setIsLoggedIn(false);
                setRole(null);
            })
            .finally(() => {
                setLoadingUser(false);
            });
    }, []);

    if (loadingUser) {
        return (
            <div className="loading-container">
                <div>Loading...</div>
                {error && <div className="error-message">Error: {error}</div>}
            </div>
        );
    }

    function onLogin() {
        setIsLoggedIn(true);
    }

    function onLogout() {
        setIsLoggedIn(false);
    }

    return (
        <RouterProvider router={router}/>
    )
}


function AppContainer(props) {
    return (
        <div>
            <Header isLoggedIn={props.isLoggedIn} setIsLoggedIn={props.setIsLoggedIn} role={props.role}/>
            <NotLoggedIn isLoggedIn={props.isLoggedIn} setIsLoggedIn={props.setIsLoggedIn}/>
            <div className="App">
                <Outlet isLoggedIn={props.isLoggedIn} role={props.role}/>
            </div>
            <Footer/>
        </div>
    )
}

export default App