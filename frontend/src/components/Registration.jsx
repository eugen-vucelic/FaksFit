import React from 'react'
import { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import './Registration.css';

function Registration({ isLoggedIn, setIsLoggedIn }) {
  const [ime, setIme] = useState('');
  const [prezime, setPrezime] = useState('');
  const [email, setEmail] = useState('');
  const [jmbag, setJmbag] = useState('');
  const [Fakultet, setSelectedFakultet] = useState('FER');
  const [Semestar, setSelectedSemestar] = useState('1');
  // const {isLoggedIn,setIsLoggedIn} = props;

  const location = useLocation(); // Access query params
  const navigate = useNavigate();

  useEffect(() => {
    const queryParams = new URLSearchParams(location.search);
    const emailFromQuery = queryParams.get('email');
    if (emailFromQuery) {
      setEmail(decodeURIComponent(emailFromQuery));
    }
  }, [location.search]);

  const handleGoBack = () => {
    navigate('/');
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const user = {
      firstName: ime,
      lastName: prezime,
      email: email,
      JMBAG: jmbag,
      userFaculty: Fakultet,
      semester: Semestar
    };

    try {
      const response = await fetch('http://localhost:8080/student/register', {
        method: 'POST',
        mode: 'cors',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(user),
        credentials: 'include',
      });

      if (response.ok) {
        console.log('Korisnik uspješno poslan na backend');
        setIsLoggedIn(true);
        navigate('/dashboard/student');
      } else {
        console.log(response);
        console.error('Greška pri slanju podataka');
      }
    } catch (error) {
      console.error('Greška:', error);
    }
  };

  return (
    !isLoggedIn ?
    <div className="registration-grid">
      <div className="left-container">
        <p className="montserrat-regular blue">Potrebno je odabrati fakultet na kojem polažete predmet Tjelesne i zdravstvene kulture.</p>
        <p className="montserrat-regular blue">Nakon uspješne registracije kao student, moguće je registrirati sportsku aktivnost i slati ponude nastavnicima.</p>
      </div>
      <div className="right-container">
        <h2 className="montserrat-semibold-italic">Registracija (za studente)</h2>
        <form method="POST" className="montserrat-regular registration-form" onSubmit={handleSubmit}>
          <div className="form-row">
            <label htmlFor="ime">Ime:</label>
            <input type="text" id="ime" value={ime} onChange={(e) => setIme(e.target.value)} required />
          </div>
          <div className="form-row">
            <label htmlFor="prezime">Prezime:</label>
            <input type="text" id="prezime" value={prezime} onChange={(e) => setPrezime(e.target.value)} required />
          </div>
          <div className="form-row">
            <label htmlFor="email">Email:</label>
            <input type="text" id="email" value={email} required />
          </div>
          <div className="form-row">
            <label htmlFor="jmbag">Jmbag:</label>
            <input type="text" id="jmbag" value={jmbag} onChange={(e) => setJmbag(e.target.value)} required />
          </div>
          <div className="form-row">
            <label htmlFor="fakultet">Fakultet:</label>
            <select id="fakultet" value={Fakultet} onChange={(e) => setSelectedFakultet(e.target.value)}>
              <option value="fer">FER</option>
              <option value="pmf">PMF</option>
              <option value="fsb">FSB</option>
              <option value="fkit">FKIT</option>
            </select>
          </div>
          <div className="form-row">
            <label htmlFor="semestar">Semestar:</label>
            <select id="semestar" value={Semestar} onChange={(e) => setSelectedSemestar(e.target.value)}>
              <option value="1">1.</option>
              <option value="2">2.</option>
              <option value="3">3.</option>
              <option value="4">4.</option>
            </select>
          </div>
          <div className="form-row">
            <input type="checkbox" id="suglasnost" required />
            <label htmlFor="suglasnost">Suglasan/na sam s uvjetima korištenja i GDPR privolama.</label>
          </div>
          <div className="form-row">
            <button type="button" onClick={handleGoBack}>Povratak</button>
            <button type="submit">Registracija</button>
          </div>
        </form>
      </div>
    </div>
  : navigate('/dashboard/student'));
}

export default Registration;
