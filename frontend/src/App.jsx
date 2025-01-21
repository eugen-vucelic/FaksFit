import * as React from "react";
import './App.css'
import { createBrowserRouter, Outlet, RouterProvider, Navigate } from "react-router-dom";
import Header from "./components/Header.jsx";
import Footer from "./components/Footer.jsx";
import NotLoggedIn from "./components/NotLoggedIn.jsx";
import Dashboard from "./components/Dashboard.jsx";
import Registration from "./components/Registration.jsx";
import MojiBodovi from "./components/MojiBodovi.jsx";
import MojiTermini from "./components/MojiTermini.jsx";
import Profile from "./components/Profile.jsx";
import Obavijesti from "./components/Notification.jsx";
import PrijavaTermina from "./components/NoviTermin.jsx";
import LeaderDashboard from "./components/LeaderDashboard.jsx";
import TeacherDashboard from "./components/TeacherDashboard.jsx";
import { API_URL } from './config';
import { useLocation } from "react-router-dom";
import { useNavigate } from "react-router-dom";

function App() {
    const [isLoggedIn, setIsLoggedIn] = React.useState(false);
    const [loadingUser, setLoadingUser] = React.useState(true);
    const [role, setRole] = React.useState(null);
    const [passedOauth, setPassedOAuth] = React.useState(false);

    const router = createBrowserRouter([
        {
            path: "/",
            element: <AppContainer isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} role={role} passedOauth={passedOauth} setPassedOAuth={setPassedOAuth} />,
            children: [
                {
                    path: "registracija",
                    element: <Registration isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} passedOauth={passedOauth} setPassedOAuth={setPassedOAuth} />
                },
                {
                    path: "student/dashboard",
                    element: (
                        isLoggedIn ? (
                            <Dashboard isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} />
                        ) : (
                            loadingUser ? <div>Loading...</div> : <Navigate to="/" replace />
                        )
                    )
                },
                {
                    path: "student/moji-termini",
                    element: (
                        isLoggedIn ? (
                            <MojiTermini isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} />
                        ) : (
                            <Navigate to="/" replace />
                        )
                    )
                },
                {
                    path: "student/profile",
                    element: (
                        isLoggedIn ? (
                            <Profile isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} />
                        ) : (
                            <Navigate to="/" replace />
                        )
                    )
                },
                {
                  path: "student/moji-bodovi",
                  element: (
                      isLoggedIn ? (
                          <MojiBodovi isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} />
                      ) : (
                          <Navigate to="/" replace />
                      )
                  )
                },
                {
                  path: "student/obavijesti",
                  element: (
                      isLoggedIn ? (
                          <Obavijesti isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} />
                      ) : (
                          <Navigate to="/" replace />
                      )
                  )
                },
                {
                  path: "voditelj/dashboard",
                  element: (
                      isLoggedIn ? (
                          <LeaderDashboard isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} />
                      ) : (
                          loadingUser ? <div>Loading...</div> : <Navigate to="/" replace />
                      )
                  )
                },
                {
                    path: "voditelj/noviTermin",
                    element: (
                      isLoggedIn ? (
                        <PrijavaTermina />
                      ) : (
                        <Navigate to="/" replace />
                      )
                    ),
                  },
                  {
                    path: "voditelj/obavijesti",
                    element: (
                      isLoggedIn ? (
                        <Obavijesti isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} />
                      ) : (
                        <Navigate to="/" replace />
                      )
                    ),
                  },
                {
                    path: "nastavnik/dashboard",
                    element: (
                        isLoggedIn ? (
                            <TeacherDashboard isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} />
                        ) : (
                            loadingUser ? <div>Loading...</div> : <Navigate to="/" replace />
                        )
                    )
                }
            ]
        }
    ]);

    React.useEffect(() => {
        const checkUserStatus = async () => {
            try {
                const response = await fetch(`${API_URL}/student/current`, {
                    credentials: 'include',
                    headers: { 'Accept': 'application/json' }
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
                setLoadingUser(false);  // Ensure loading ends even if fetch fails
            }
        };

        checkUserStatus();

        // Fallback to stop loading after 5 seconds
        const timeout = setTimeout(() => setLoadingUser(false), 5000);
        return () => clearTimeout(timeout);
    }, []);

    // Prevent dashboard flash during user status check
    if (loadingUser) {
        return <div>Loading...</div>;
    }

    return <RouterProvider router={router} />;
}

function AppContainer({ isLoggedIn, setIsLoggedIn, role, passedOauth, setPassedOAuth }) {
    const location = useLocation();
    const navigate = useNavigate();
  
    React.useEffect(() => {
      if (isLoggedIn && location.pathname === '/') {
        navigate('/student/dashboard');
      }
    }, [isLoggedIn, location.pathname, navigate]);
  
    return (
      <div>
        {/* Header is shown on every route by default */}
        <Header isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} role={role} />
  
        {/* Show NotLoggedIn only if user is not logged in on root */}
        {location.pathname === '/' && !isLoggedIn && (
          <NotLoggedIn
            isLoggedIn={isLoggedIn}
            setIsLoggedIn={setIsLoggedIn}
            passedOauth={passedOauth}
            setPassedOAuth={setPassedOAuth}
          />
        )}
        
        {/* Child pages (e.g., Dashboard, Profile) are rendered here */}
        <div className="App">
          <Outlet />
        </div>
  
        <Footer />
      </div>
    );
  }
  
export default App;
