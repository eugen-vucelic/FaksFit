import * as React from "react";
import './App.css'
import {createBrowserRouter, Outlet, RouterProvider, Navigate} from "react-router-dom";
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
                        <Navigate to="/" replace/>
                }
            ]
        }
    ]);

    React.useEffect(() => {
        fetch(`${API_URL}/api/user`, {
            credentials: 'include',
            headers: {
                'Accept': 'application/json'
            }
        })
            .then(async response => {
                if (response.status === 200) {
                    const userData = await response.json();
                    // Check if user has completed registration
                    if (userData.registrationComplete) {
                        setIsLoggedIn(true);
                        setRole("Student");
                    } else {
                        setIsLoggedIn(false);
                    }
                } else {
                    setIsLoggedIn(false);
                }
            })
            .catch(error => {
                console.error('Error fetching user:', error);
                setIsLoggedIn(false);
            })
            .finally(() => {
                setLoadingUser(false);
            });
    }, []);

    if (loadingUser) {
        return <div>Loading...</div>
    }

    return (
        <RouterProvider router={router}/>
    )
}

function AppContainer(props) {
    return (
        <div>
            <Header isLoggedIn={props.isLoggedIn} setIsLoggedIn={props.setIsLoggedIn} role={props.role}/>
            {!props.isLoggedIn && <NotLoggedIn isLoggedIn={props.isLoggedIn} setIsLoggedIn={props.setIsLoggedIn}/>}
            <div className="App">
                <Outlet />
            </div>
            <Footer/>
        </div>
    )
}

export default App