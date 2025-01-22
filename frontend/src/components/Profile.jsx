import "./Profile.css";
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { API_URL } from '../config';

function Profile({ isLoggedIn, setIsLoggedIn }) {
    const [profileData, setProfileData] = useState(null);
    const [error, setError] = useState(null);
    const [submitError, setSubmitError] = useState(null);
    const navigate = useNavigate();
    
    // Form State
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [gender, setSelectedGender] = useState('M');
    const [nationality, setNationality] = useState('');
    const [birthDate, setBirthDate] = useState('');
    const [phoneNumber, setPhoneNumber] = useState('');
    const [semester, setSemester] = useState('');

    const [initialValues, setInitialValues] = useState({});

    // Fetch user data on component mount
    useEffect(() => {
        fetch(`${API_URL}/student/current`, {
            method: 'GET',
            credentials: 'include',
            headers: {
                'Accept': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('jwt_token')}`
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch profile data');
            }
            return response.json();
        })
        .then(data => {
            setProfileData(data);
            setInitialValues({
                firstName: data.firstName,
                lastName: data.lastName,
                gender: data.gender,
                nationality: data.nationality,
                birthDate: data.birthDate,
                phoneNumber: data.phoneNumber,
                semester: data.semester,
            });

            // Populate state with existing data
            setFirstName(data.firstName || '');
            setLastName(data.lastName || '');
            setSelectedGender(data.gender || 'M');
            setNationality(data.nationality || '');
            setBirthDate(data.birthDate || '');
            setPhoneNumber(data.phoneNumber || '');
            setSemester(data.semester || '');
        })
        .catch(error => {
            console.error('Error fetching profile data:', error);
            setIsLoggedIn(false);
            setError(error.message);
        });
    }, [setIsLoggedIn]);

    // Handle form submission
    const handleSubmit = async (e) => {
        e.preventDefault();
        setSubmitError(null);

        // Basic validation
        if (!firstName || !lastName || !semester) {
            setSubmitError("Please fill out all required fields.");
            return;
        }

        const updatedFields = {
            firstName,
            lastName,
            gender,
            nationality,
            birthDate,
            phoneNumber,
            semester,
        };

        try {
            const response = await fetch(`${API_URL}/student/patch`, {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem('jwt_token')}`
                },
                body: JSON.stringify(updatedFields),
                credentials: 'include',
            });

            if (response.ok) {
                console.log('User successfully updated');
                // setIsLoggedIn(true);
                navigate('/student/dashboard');
            } else {
                const errorData = await response.json();
                console.error('Error updating profile:', errorData);
                setSubmitError(errorData.message || 'Failed to update profile. Please try again.');
            }
        } catch (error) {
            console.error('Error:', error);
            setSubmitError('An unexpected error occurred. Please try again.');
        }
    };

    // Handle form reset
    const handleReset = () => {
        setFirstName(initialValues.firstName || '');
        setLastName(initialValues.lastName || '');
        setSelectedGender(initialValues.gender || 'M');
        setNationality(initialValues.nationality || '');
        setBirthDate(initialValues.birthDate || '');
        setPhoneNumber(initialValues.phoneNumber || '');
        setSemester(initialValues.semester || '');
        setSubmitError(null);
    };

    // Display error or loading screen
    if (error) {
        return <div>Error: {error}</div>;
    }

    if (!profileData) {
        return <div>Loading...</div>;
    }

    return (
        <div className="profile-grid montserrat-regular">
            {/* Static display of current data on the left */}
            <div className="profile-data-left">
                <h2>Trenutni Podaci</h2>
                <p><strong>Ime:</strong> {profileData.firstName}</p>
                <p><strong>Prezime:</strong> {profileData.lastName}</p>
                <p><strong>Spol:</strong> {profileData.gender}</p>
                <p><strong>Nacionalnost:</strong> {profileData.nationality}</p>
                <p><strong>Datum rođenja:</strong> {profileData.birthDate}</p>
                <p><strong>Broj mobitela:</strong> {profileData.phoneNumber}</p>
                <p><strong>Semestar:</strong> {profileData.semester}</p>
            </div>

            {/* Form for editing on the right */}
            <form method="PATCH" onSubmit={handleSubmit}>
                <div className="form-row">
                    <label htmlFor="ime">Ime:</label>
                    <input
                        type="text"
                        id="ime"
                        value={firstName}
                        onChange={(e) => setFirstName(e.target.value)}
                        required
                    />
                </div>
                <div className="form-row">
                    <label htmlFor="prezime">Prezime:</label>
                    <input
                        type="text"
                        id="prezime"
                        value={lastName}
                        onChange={(e) => setLastName(e.target.value)}
                        required
                    />
                </div>
                <div className="form-row">
                    <label htmlFor="spol">Spol:</label>
                    <select
                        id="spol"
                        value={gender}
                        onChange={(e) => setSelectedGender(e.target.value)}
                    >
                        <option value="M">M</option>
                        <option value="Ž">Ž</option>
                    </select>
                </div>
                <div className="form-row">
                    <label htmlFor="nacionalnost">Nacionalnost:</label>
                    <input
                        type="text"
                        id="nacionalnost"
                        value={nationality}
                        onChange={(e) => setNationality(e.target.value)}
                    />
                </div>
                <div className="form-row">
                    <label htmlFor="datRod">Datum rođenja:</label>
                    <input
                        type="date"
                        id="datRod"
                        value={birthDate}
                        onChange={(e) => setBirthDate(e.target.value)}
                    />
                </div>
                <div className="form-row">
                    <label htmlFor="broj">Broj mobitela:</label>
                    <input
                        type="text"
                        id="broj"
                        value={phoneNumber}
                        onChange={(e) => setPhoneNumber(e.target.value)}
                        required
                    />
                </div>

                    <div className="form-row">
                    <label htmlFor="semestar">Semestar:</label>
                    <select
                        id="semestar"
                        value={semester}
                        onChange={(e) => setSemester(e.target.value)}
                        required
                    >
                        <option value="">Odaberite semestar</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                    </select>
                </div>

                
                {submitError && <p className="error-message">{submitError}</p>}
                
                <div className="form-row">
                    <button type="button" onClick={handleReset}>
                        Resetiraj
                    </button>
                    <button type="submit">
                        Promijeni podatke
                    </button>
                </div>
            </form>
        </div>
    );
}

export default Profile;
