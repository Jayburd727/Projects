import React from 'react';
import GoogleLogout from 'react-google-login';

function SignOutButton() {

  const logout = () => {
    console.log('User logged out');
    window.location.href = '/app';
  };

  return (
    <GoogleLogout
      clientId="310744269504-d1mjhrpco5ff3jr24u1llpvf0idpb32v.apps.googleusercontent.com"
      buttonText="Sign out"
      onLogoutSuccess={logout}
      className="google-button-logout"
    />
  );
}

export default SignOutButton;
