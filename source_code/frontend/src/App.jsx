import * as React from "react";
import './App.css'
import {createBrowserRouter, Outlet, RouterProvider} from "react-router-dom";
import Header from "./components/Header.jsx";
import Footer from "./components/Footer.jsx";
import NotLoggedIn from "./components/NotLoggedIn.jsx";

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

    // HANDLE LOGIN HERE AND CALL ONLOGIN
    React.useEffect(() => {
        fetch("/api/user")
            .then(response => {
                setLoadingUser(false);

                if (response.status === 200) {
                    setIsLoggedIn(false);
                    setRole("Student");
                } else {
                    setIsLoggedIn(false);
                }
            })
    }, []);

    if (loadingUser) {
        return <div>Loading...</div>
    }

    // HANDLE ROLE ASSIGNMENT HERE
    function onLogin() {
        setIsLoggedIn(true);
    }

    function onLogout() {
        setIsLoggedIn(false);
        setRole(null);
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
            <Header isLoggedIn={props.isLoggedIn} role={props.role}/>
            <NotLoggedIn isLoggedIn={props.isLoggedIn}/>
            <Footer/>
            <div className="App">
                <Outlet/>
            </div>
        </div>
    )
}