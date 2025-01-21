import * as React from "react";
import './App.css';
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
import ListaTermina from "./components/PrijavaTermina.jsx";
import { API_URL } from './config';
import { jwtDecode } from 'jwt-decode';
import { useLocation, useNavigate } from "react-router-dom";

const useURLToken = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { handleLogin } = React.useContext(AuthContext);

  React.useEffect(() => {
    // Get token from URL parameters
    const params = new URLSearchParams(location.search);
    const token = params.get('token');
    
    if (token) {
      // Store the token
      localStorage.setItem('jwt_token', token);
      handleLogin(token);
      
      // // Clean up the URL by removing the token
      // const newURL = window.location.pathname;
      // navigate(newURL, { replace: true });
      const decodedToken = AuthUtils.getDecodedToken(token);
      const userRole = decodedToken?.role?.toLowerCase();
      if (userRole) {
        navigate(`/${userRole}/dashboard`, { replace: true });
      } else {
        navigate('/', { replace: true });
      }
    }
  }, [location, navigate, handleLogin]);
};

const AuthContext = React.createContext(null);

// JWT and Auth utilities
const AuthUtils = {
  TOKEN_KEY: 'jwt_token',
  ROLES: {
    STUDENT: 'STUDENT',
    LEADER: 'ACTIVITY_LEADER',
    TEACHER: 'TEACHER'
  },

  getToken: () => localStorage.getItem(AuthUtils.TOKEN_KEY),

  setToken: (token) => {
    if (token) {
      localStorage.setItem(AuthUtils.TOKEN_KEY, token);
    } else {
      localStorage.removeItem(AuthUtils.TOKEN_KEY);
    }
  },

  getDecodedToken: () => {
    const token = AuthUtils.getToken();
    if (!token) return null;
    try {
      return jwtDecode(token);
    } catch (error) {
      console.error('Token decode error:', error);
      return null;
    }
  },

  isTokenValid: (token) => {
    if (!token) return false;
    try {
      const decoded = jwtDecode(token);
      return decoded.exp * 1000 > Date.now();
    } catch {
      return false;
    }
  },

  getRole: () => {
    const decoded = AuthUtils.getDecodedToken();
    return decoded?.role?.toLowerCase() || null;
  },

  getEmail: () => {
    const decoded = AuthUtils.getDecodedToken();
    return decoded?.sub || null;
  }
};

function App() {
  const [isLoggedIn, setIsLoggedIn] = React.useState(false);
  const [loadingUser, setLoadingUser] = React.useState(true);
  const [role, setRole] = React.useState(null);
  const [userEmail, setUserEmail] = React.useState(null);
  const [passedOauth, setPassedOAuth] = React.useState(false);

  const handleLogin = React.useCallback((token) => {
    AuthUtils.setToken(token);
    const decodedToken = AuthUtils.getDecodedToken();
    const userRole = decodedToken?.role?.toLowerCase();
    setRole(userRole);
    setIsLoggedIn(true);
    setUserEmail(decodedToken?.sub);
  }, []);

  const handleLogout = () => {
    AuthUtils.setToken(null);
    setIsLoggedIn(false);
    setRole(null);
    setUserEmail(null);
    setPassedOAuth(false);
    localStorage.removeItem("jwt_token");
  };

  // Protected Route wrapper component
  const ProtectedRoute = ({ allowedRoles, children }) => {
    if (loadingUser) return <div>Loading...</div>;
    if (!isLoggedIn) return <Navigate to="/" replace />;
    if (allowedRoles && !allowedRoles.includes(role?.toLowerCase())) {
      return <Navigate to="/" replace />;
    }
    return children;
  };

  const router = createBrowserRouter([
    {
      path: "/",
      element: (
        <AppContainer
          isLoggedIn={isLoggedIn}
          setIsLoggedIn={setIsLoggedIn}
          role={role}
          passedOauth={passedOauth}
          setPassedOAuth={setPassedOAuth}
          handleLogout={handleLogout}
          handleLogin={handleLogin}
        />
      ),
      children: [
        {
          path: "/",
          element: !loadingUser && (
            // isLoggedIn && role ? (
            isLoggedIn ? (
              <Navigate to={`/${role}/dashboard`} replace />
            ) : (
              <NotLoggedIn
                isLoggedIn={isLoggedIn}
                setIsLoggedIn={setIsLoggedIn}
                passedOauth={passedOauth}
                setPassedOAuth={setPassedOAuth}
                onLogin={handleLogin}
              />
            )
          ),
        },
        {
          path: "registracija",
          element: !isLoggedIn ? (
            <Registration
              isLoggedIn={isLoggedIn}
              setIsLoggedIn={setIsLoggedIn}
              passedOauth={passedOauth}
              setPassedOAuth={setPassedOAuth}
              onLogin={handleLogin}
            />
          ) : (
            <Navigate to={`/${role}/dashboard`} replace />
          ),
        },
        // Student Routes
        {
          path: "student/*",
          element: (
            <ProtectedRoute allowedRoles={['student']}>
              <Outlet />
            </ProtectedRoute>
          ),
          children: [
            {
              path: "dashboard",
              element: <Dashboard />,
            },
            {
              path: "moji-termini",
              element: <MojiTermini />,
            },
            {
              path: "prijava-termina",
              element: <ListaTermina />,
            },
            {
              path: "profile",
              element: <Profile />,
            },
            {
              path: "moji-bodovi",
              element: <MojiBodovi />,
            },
            {
              path: "obavijesti",
              element: <Obavijesti />,
            },
          ],
        },
        // Activity Leader Routes
        {
          path: "activity_leader/*",
          element: (
            <ProtectedRoute allowedRoles={['activity_leader']}>
              <Outlet />
            </ProtectedRoute>
          ),
          children: [
            {
              path: "dashboard",
              element: <LeaderDashboard />,
            },
            {
              path: "noviTermin",
              element: <PrijavaTermina />,
            },
            {
              path: "obavijesti",
              element: <Obavijesti />,
            },
          ],
        },
        // Teacher Routes
        {
          path: "teacher/*",
          element: (
            <ProtectedRoute allowedRoles={['teacher']}>
              <Outlet />
            </ProtectedRoute>
          ),
          children: [
            {
              path: "dashboard",
              element: <TeacherDashboard />,
            },
            {
              path: "obavijesti",
              element: <Obavijesti />,
            },
          ],
        },
      ],
    },
  ]);

  React.useEffect(() => {
    const checkUserStatus = async () => {
      try {
        const token = AuthUtils.getToken();
        
        if (!token || !AuthUtils.isTokenValid(token)) {
          handleLogout();
          setLoadingUser(false);
          return;
        }

        const decodedToken = AuthUtils.getDecodedToken();
        const userRole = decodedToken?.role?.toLowerCase();
        setRole(userRole);
        setIsLoggedIn(true);
      } catch (error) {
        console.error('Auth check error:', error);
        handleLogout();
      } finally {
        setLoadingUser(false);
      }
    };

    checkUserStatus();

    // Token expiration checker
    const tokenCheckInterval = setInterval(() => {
      const token = AuthUtils.getToken();
      if (token && !AuthUtils.isTokenValid(token)) {
        handleLogout();
      }
    }, 60000); // Check every minute

    return () => clearInterval(tokenCheckInterval);
  }, []);

  if (loadingUser) {
    return <div>Loading...</div>;
  }

  return (
    <AuthContext.Provider value={{ handleLogin, isLoggedIn, role }}>
      <RouterProvider router={router} />
    </AuthContext.Provider>
  );
}

function AppContainer({
  isLoggedIn,
  setIsLoggedIn,
  role,
  passedOauth,
  setPassedOAuth,
  handleLogout,
  handleLogin,
  }) {
  useURLToken();

  console.log(isLoggedIn);

  return (
    <div className="min-h-screen flex flex-col">
      <Header 
        isLoggedIn={isLoggedIn} 
        setIsLoggedIn={setIsLoggedIn} 
        role={role} 
        onLogout={handleLogout}
      />
      
      <main className="flex-grow">
        <Outlet />
      </main>

      <Footer />
    </div>
  );
}

export default App;