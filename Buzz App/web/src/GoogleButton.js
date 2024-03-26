import React from 'react';
import GoogleLogin from 'react-google-login';

class GoogleButton extends React.Component {
  responseGoogle = (response) => {
    console.log(response);
    // Check if the response contains a valid access token
    if (response.accessToken) {
      // Redirect to the app page
      window.location.href = '/app';
    }
  };

  render() {
    return (
      <div>
        <GoogleLogin
          clientId="310744269504-d1mjhrpco5ff3jr24u1llpvf0idpb32v.apps.googleusercontent.com"
          buttonText="Login with Google"
          onSuccess={this.responseGoogle}
          onFailure={this.responseGoogle}
          cookiePolicy={'single_host_origin'}
          className="google-button"
        />
      </div>
    );
  }
}

export default GoogleButton;
