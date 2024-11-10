import * as React from "react";
import './App.css'
import {createBrowserRouter, Outlet, RouterProvider} from "react-router-dom";
import Header from "./components/Header.jsx";
//import Login from "./components/Login.jsx";

function App() {
    const [isLoggedIn, setIsLoggedIn] = React.useState(false);
    const [loadingUser, setLoadingUser] = React.useState(true);
    const [role, setRole] = React.useState(null);

    const router = createBrowserRouter([
        {
            path: "/",
            element: <AppContainer isLoggedIn={isLoggedIn} role={role}/>,
            children: [
                {
                    path: "students",
                    //element: <StudentList/>
                },
                {
                    path: "students/new",
                    //element: <StudentForm/>
                },
            ]
        }
    ]);

    React.useEffect(() => {
        fetch("/api/user")
            .then(response => {
                setLoadingUser(false);

                if (response.status === 200) {
                    setIsLoggedIn(true);
                } else {
                    setIsLoggedIn(false);
                }
            })
    }, []);

    if (loadingUser) {
        return <div>Loading...</div>
    }

    function onLogin() {
        setIsLoggedIn(true)
    }

    function onLogout() {
        setIsLoggedIn(false);
        setRole(null);
    }

    if (!isLoggedIn) {
        return (
            <div className="App">
                {/*<Login onLogin={onLogin}/>*/}
            </div>
        )
    }

    return (
        <RouterProvider router={router}/>
    )
}

export default App

function AppContainer(props) {
    console.log(props)
    return (
        <div>
            <Header isLoggedIn={props.isLoggedIn, props.role}/>
            <div className="App">
                <Outlet/>
            </div>
        </div>
    )
}