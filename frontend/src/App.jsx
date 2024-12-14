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
    const [passedOauth, setPassedOAuth] = React.useState(false);

    const router = createBrowserRouter([
        {
            path: "/",
            element: <AppContainer isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} role={role} passedOauth={passedOauth} setPassedOAuth = {setPassedOAuth}/>,
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
        const checkUserStatus = async () => {
            try {
                const response = await fetch(`${API_URL}/student/current`, {
                    credentials: 'include',
                    headers: {
                        'Accept': 'application/json'
                    }
                });
                if (response.status === 200) {
                    const user = await response.json();
                    setIsLoggedIn(true);
                    setRole(user.role || "Student");
                } else {
                    setIsLoggedIn(false);
                }
            } catch (error) {
                console.error('Error fetching user:', error);
                setIsLoggedIn(false);
            } finally {
                setLoadingUser(false);
            }
        };
    
        checkUserStatus();
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
            {!props.isLoggedIn && <NotLoggedIn isLoggedIn={props.isLoggedIn} setIsLoggedIn={props.setIsLoggedIn} passedOauth={props.passedOauth} setPassedOAuth={props.setPassedOAuth} />}
            <div className="App">
                <Outlet />
            </div>
            <Footer/>
        </div>
    )
}

export default App