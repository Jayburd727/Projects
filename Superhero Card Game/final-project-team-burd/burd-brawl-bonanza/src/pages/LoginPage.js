import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import "./LoginPage.css";

let premium;

function LoginPage() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const navigate = useNavigate();

  const handleSubmit = async (event) => {
    event.preventDefault();
    let data_to_send = { username: username, password: password };
    const response = await fetch("http://localhost:3001/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      // body: JSON.stringify({ username, password })
      body: JSON.stringify(data_to_send),
    });

    const data = await response.json();

    if (response.ok) {
      console.log("Login successful", data);
      premium = data.premium;
      navigate("/selection"); // navigate to the selection page
    } else {
      console.log("Login failed", data);
    }
  };

  const playHoverSound = () => {
    const sound = document.getElementById("hover-sound");
    if (sound) {
      sound.play();
    }
  };

  return (
    <div>
      <div className="container">
        <audio id="hover-sound" src="music/click.mov" preload="auto"></audio>
        <img src="images/Logo.png" alt="Logo Text" />
        <h1>Login To Play</h1>
        <form onSubmit={handleSubmit}>
          <input
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            placeholder="Username"
            required
          />
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            placeholder="Password"
            required
          />
          <button
            type="submit"
            className="bubbly-button"
            onMouseEnter={playHoverSound}
          >
            Login
          </button>
        </form>
        {/* <div>
          <Link to={`/selection`}>Go to the selection page</Link>
        </div> */}
        <div className="link-container">
          <Link to={`/register`}>Not Registered? Click Here!</Link>
        </div>
      </div>
    </div>
  );
}

export default LoginPage;
export { premium };
