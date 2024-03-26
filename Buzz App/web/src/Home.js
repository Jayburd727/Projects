import React from 'react';
import { Link } from 'react-router-dom';
import './Home.css'; 

import logo from './beee.png'; 
import GoogleButton from './GoogleButton';

function Home() {
  return (
    <div className="home-container">
        <img src={logo} alt="Logo" className="logo" /> 
      <h1>Welcome to The Buzz!</h1>
      <Link to="/app">
        <GoogleButton /> 
      </Link>    
    </div>
  );
}

export default Home;
