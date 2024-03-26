import React from 'react';
import './Header.css';
import SignOutButton from './SignOutButton';
import { Link } from 'react-router-dom';

class Header extends React.Component {
  render() {
    //Simple HTML header
    return (
      <header className="header">
        <h1 className="header__title">The Buzz</h1>
        
        <div className='logout'>
          <button><Link to="/profile">Profile</Link></button>
          <SignOutButton />
        </div>
        
      </header>
    );
  }
}

export default Header;
