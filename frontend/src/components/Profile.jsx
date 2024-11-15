import "./Profile.css";
import React, { useEffect, useState } from "react";
import { Link, useNavigate, useLocation } from "react-router-dom";

function Profile(props) {
    const { isLoggedIn, setIsLoggedIn } = props;
    const [profileData, setProfileData] = useState(null);
    const [error, setError] = useState(null);
    const navigate = useNavigate();
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [gender, setSelectedGender] = useState('M');
    const [nationality, setNationality] = useState('');
    const [birthDate, setBirthDate] = useState('');
    const [phoneNumber, setPhoneNumber] = useState('');
    const location = useLocation();

    const [initialValues, setInitialValues] = useState({});

    useEffect(() => {
        fetch('https://faksfit.onrender.com/student/current', {
            method: 'GET',
            credentials: 'include',
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
            });

            setFirstName(data.firstName || '');
            setLastName(data.lastName || '');
            setSelectedGender(data.gender || 'M');
            setNationality(data.nationality || '');
            setBirthDate(data.birthDate || '');
            setPhoneNumber(data.phoneNumber || '');
        })
        .catch(error => {
            console.error('Error fetching profile data:', error);
            setIsLoggedIn(false);
            setError(error);
        });
    }, [setIsLoggedIn]);

    const handleSubmit = async (e) => {
        e.preventDefault();

        const updatedFields = {};
        if (firstName !== initialValues.firstName) updatedFields.firstName = firstName;
        if (lastName !== initialValues.lastName) updatedFields.lastName = lastName;
        if (gender !== initialValues.gender) updatedFields.gender = gender;
        if (nationality !== initialValues.nationality) updatedFields.nationality = nationality;
        if (birthDate !== initialValues.birthDate) updatedFields.birthDate = birthDate;
        if (phoneNumber !== initialValues.phoneNumber) updatedFields.phoneNumber = phoneNumber;

        try {
            const response = await fetch('https://faksfit.onrender.com/student/patch', {
                method: 'PATCH',
                mode: 'cors',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(updatedFields),
                credentials: 'include',
            });

            if (response.ok) {
                console.log('User successfully updated');
                setIsLoggedIn(true);
                navigate('/dashboard/student');
            } else {
                console.error('Error updating profile');
            }
        } catch (error) {
            console.error('Error:', error);
        }
    };

    if (error) {
        return <div>Error: {error.message}</div>;
    }

    if (!profileData) {
        return <div>Loading...</div>;
    }

    const handleReset = () => {
        setFirstName(initialValues.firstName || '');
        setLastName(initialValues.lastName || '');
        setSelectedGender(initialValues.gender || 'M');
        setNationality(initialValues.nationality || '');
        setBirthDate(initialValues.birthDate || '');
        setPhoneNumber(initialValues.phoneNumber || '');
    };

    return (
        <div className="profile-grid montserrat-regular">
            <div className="profile-data">
                <div className="data-row">
                    <p className="montserrat-bold">{profileData.firstName} {profileData.lastName}</p>
                    <p>Spol: {profileData.gender}</p>
                    <p>Nacionalnost: {profileData.nationality}</p>
                    <p>Datum rođenja: {profileData.birthDate}</p>
                    <p>Broj mobitela: {profileData.phoneNumber}</p>
                </div>
            </div>
            <form method="PATCH" onSubmit={handleSubmit}>
                <div className="form-row">
                    <label htmlFor="ime">Ime:</label>
                    <input type="text" id="ime" value={firstName} onChange={(e) => setFirstName(e.target.value)} />
                </div>
                <div className="form-row">
                    <label htmlFor="prezime">Prezime:</label>
                    <input type="text" id="prezime" value={lastName} onChange={(e) => setLastName(e.target.value)} />
                </div>
                <div className="form-row">
                    <label htmlFor="spol">Spol:</label>
                    <select id="spol" value={gender} onChange={(e) => setSelectedGender(e.target.value)}>
                        <option value="M">M</option>
                        <option value="Ž">Ž</option>
                    </select>
                </div>
                <div className="form-row">
                    <label htmlFor="nacionalnost">Nacionalnost:</label>
                    <input type="text" id="nacionalnost" value={nationality} onChange={(e) => setNationality(e.target.value)} />
                </div>
                <div className="form-row">
                    <label htmlFor="datRod">Datum rođenja:</label>
                    <input type="date" id="datRod" value={birthDate} onChange={(e) => setBirthDate(e.target.value)} />
                </div>
                <div className="form-row">
                    <label htmlFor="broj">Broj mobitela:</label>
                    <input type="text" id="broj" value={phoneNumber} onChange={(e) => setPhoneNumber(e.target.value)} />
                </div>
                <div className="form-row">
                    <button type="button" onClick={handleReset}>Resetiraj</button>
                    <button type="submit">Promijeni podatke</button>
                </div>
            </form>
        </div>
    );
}

export default Profile;
