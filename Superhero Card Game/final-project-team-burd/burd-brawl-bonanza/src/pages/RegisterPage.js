import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import "./RegisterPages.css";

function RegisterPage() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [premium, setPremium] = useState(false);

  const navigate = useNavigate();

  const handleSubmit = async (event) => {
    event.preventDefault();

    const response = await fetch("http://localhost:3001/register", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ username, password, premium }),
    });

    const data = await response.json();

    if (response.ok) {
      console.log("Registration successful", data);
      setUsername("");
      setPassword("");
      setPremium(false);
      navigate("/");
    } else {
      console.log("Registration failed", data);
      // Show error message here
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
      <div className="wrapper">
        <img src="images/batman.png" alt="Left" className="side-image" />
        <div className="container2">
          <audio id="hover-sound" src="music/click.mov" preload="auto"></audio>
          <h1>Register</h1>

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
            <label class="label">
              <input
                type="checkbox"
                checked={premium}
                onChange={(e) => setPremium(e.target.checked)}
              />
              Check Box For A Exclusive Premium Card!
            </label>
            <button
              type="submit"
              className="bubbly-button2"
              onMouseEnter={playHoverSound}
            >
              Register
            </button>
          </form>
          <div className="link-container">
            <Link to={`/`}>Go back to the login page</Link>
          </div>
        </div>
        <img src="images/flash.png" alt="Right" class="side-image" />
      </div>
    </div>
  );
}

export default RegisterPage;
