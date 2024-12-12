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

    const router = createBrowserRouter([
        {
            path: "/",
            element: <AppContainer isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} role={role}/>,
            children: [
                {
                    path: "registracija",
                    element: <Registration isLoggedIn={isLoggedIn}/>
                },
                {
                    path: "/dashboard/student",
                    element: <Dashboard isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn}/>
                }
            ]
        }
    ]);

    React.useEffect(() => {
        fetch(`${API_URL}/api/user`, {
            credentials: 'include'
        })
            .then(response => {
                setLoadingUser(false);

                if (response.status === 200) {
                    setIsLoggedIn(true);
                    setRole("Student");
                } else {
                    setIsLoggedIn(false);
                }
            })
            .catch(error => {
                console.error('Error fetching user:', error);
                setLoadingUser(false);
                setIsLoggedIn(false);
            });
    }, []);

    if (loadingUser) {
        return <div>Loading...</div>
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