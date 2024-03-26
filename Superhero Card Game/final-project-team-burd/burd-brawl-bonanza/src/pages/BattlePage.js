import { React, useState, useEffect } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import "./BattlePage.css";
import { premium } from "./LoginPage";

// Function for dealing with "null" power values
const detAttValue = require("./functions/detAttValue");

// Function for generating a random hero ID
const genRdmHeroId = require("./functions/genRdmHeroId");

function BattlePage() {
  // Call 'navigate' to programmatically move to a different page
  const navigate = useNavigate();

  // Name and power stats of the cpu hero (loaded below)
  const [cpuHero, setCpuHero] = useState({
    name: "",
    imageUrl: "",
    Combat: 0,
    Strength: 0,
    Speed: 0,
    Intelligence: 0,
    Durability: 0,
    Power: 0,
  });

  // Player lives and score
  const [lives, setLives] = useState(3);
  const [score, setScore] = useState(0);

  // Name and power stats of the user's hero (loaded from previous page)
  const location = useLocation();
  const { userHero } = location.state;

  // Defines the names of each of the 6 power categories
  const powerCategories = [
    "Combat",
    "Strength",
    "Speed",
    "Intelligence",
    "Durability",
    "Power",
  ];

  // Mapping of power category name to numeric quantity for the user
  const userStats = {
    Combat: userHero.combat,
    Strength: userHero.strength,
    Speed: userHero.speed,
    Intelligence: userHero.intelligence,
    Durability: userHero.durability,
    Power: userHero.power,
  };

  // Given a passed hero name, get the matching data from the api
  async function getCpuHero() {
    // Make API call for hero data from the passed in name
    const randomId = genRdmHeroId();
    const response = await fetch(`/api/superhero/${randomId}`);
    const tempHero = await response.json(); // Get hero from response data

    // console.log(tempHero); // Output to console the CPU hero, for testing

    // Sets up simplified object and stores it in state
    const heroToSet = {
      ...cpuHero,
      name: tempHero.name,
      imageUrl: tempHero.image.url,
      Combat: detAttValue(tempHero.powerstats.combat),
      Strength: detAttValue(tempHero.powerstats.strength),
      Speed: detAttValue(tempHero.powerstats.speed),
      Intelligence: detAttValue(tempHero.powerstats.intelligence),
      Durability: detAttValue(tempHero.powerstats.durability),
      Power: detAttValue(tempHero.powerstats.power),
    };
    setCpuHero(heroToSet);
  }

  // Initializes cpu hero object
  useEffect(() => {
    getCpuHero();
  }, []);

  // Navigate as appropriate
  useEffect(() => {
    // Determine outcome of battle
    if (score >= 3) {
      navigate(`/victory`);
    } else if (lives <= 0) {
      navigate(`/gameover`);
    }
  }, [score, lives]);

  // Handler for a power button click
  const handlePowerClick = (powerType, powerStat) => {
    // console.log(`${powerType} of user (${powerStat}) vs. ${powerType} of CPU (${cpuHero[powerType]})`);

    // Disable user's buttons
    const userPowerButtons = document.querySelectorAll(
      "#userPanel .powerButton"
    );
    userPowerButtons.forEach((button) => {
      button.disabled = true;
    });

    // Display the CPU's power stats
    const cpuPowerNumbers = document.querySelectorAll(
      "#cpuPanel .powerQuantity"
    );
    cpuPowerNumbers.forEach((element) => {
      element.style.display = "inline-block";
    });

    // Pause before navigation
    setTimeout(() => {
      // Modify lives or score accordingly
      if (powerStat > cpuHero[powerType]) {
        setScore(score + 1);
      } else if (powerStat < cpuHero[powerType]) {
        setLives(lives - 1);
      }

      // Hide CPU power stats
      const cpuPowerNumbers = document.querySelectorAll(
        "#cpuPanel .powerQuantity"
      );
      cpuPowerNumbers.forEach((element) => {
        element.style.display = "none";
      });

      // Reset opponent hero
      getCpuHero();

      // Re-enable user's buttons
      userPowerButtons.forEach((button) => {
        button.disabled = false;
      });
    }, 1000);
  };

  return (
    <div>
      <div className="container4">
        <div id="playerPanels">
          <div id="userPanel">
            <div className="characterData">
              <h2>{userHero.name}</h2>
              <img
                className="heroImage"
                src={userHero.imageUrl}
                onError={(e) => {
                  e.target.onerror = null;
                  e.target.src = "/images/no_card.png"; // Path to placeholder image
                }}
              />
              <h3>
                <em>Score:</em> {score}
              </h3>
              <h3>
                <em>Lives:</em> {lives}
              </h3>
            </div>

            <div className="powerOptions">
              {powerCategories.map((powerType) => (
                // Button and numeric quantity pair
                <div className="powerListing" key={powerType}>
                  <button
                    type="button"
                    onClick={() =>
                      handlePowerClick(
                        powerType,
                        parseInt(userStats[powerType])
                      )
                    }
                    className="powerButton"
                  >
                    {powerType}
                  </button>
                  <p className="powerQuantity">{userStats[powerType]}</p>
                </div>
              ))}
            </div>
          </div>

          {/* CPU's power button list */}
          <div id="cpuPanel">
            <div className="characterData">
              <h2>{cpuHero.name}</h2>
              {/* FIXME: (1) Handle no image DONE */}
              <img
                className="heroImage"
                src={cpuHero.imageUrl}
                onError={(e) => {
                  e.target.onerror = null;
                  e.target.src = "/images/no_card.png"; // Path to placeholder image
                }}
              />
            </div>

            <div className="powerOptions">
              {powerCategories.map((powerType) => (
                // Button and numeric quantity pair
                <div className="powerListing" key={powerType}>
                  <p
                    type="button"
                    className="powerButton" // FIXME: name a different class?
                  >
                    {powerType}
                  </p>
                  <p className="powerQuantity">{cpuHero[powerType]}</p>
                </div>
              ))}
            </div>
          </div>
        </div>
        <h4>
          {premium
            ? "Questions? Comments? Concerns? Proclamations? Inquiries? -Dominic DiFranzo"
            : "Its not dying that you need to be afraid of, its never having lived in the first place.” -The Green Hornet"}
        </h4>
      </div>
    </div>
  );
}

export default BattlePage;
