import { React, useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import "./SelectionPage.css";
import { premium } from "./LoginPage";
const NUMHEROES = 3; // Number of hero options that the user can select

// Function for dealing with "null" power values
const detAttValue = require("./functions/detAttValue");

// Function for generating a random hero ID
const genRdmHeroId = require("./functions/genRdmHeroId");

//hopefully get the premium value for a user from login
// const premium = require('./LoginPage');

function SelectionPage() {
  // Call 'navigate' to programmatically move to a different page
  const navigate = useNavigate();

  // List of the available heroes for the user to select
  const [heroList, setHeroList] = useState([]);

  // Gets a random hero
  async function getRdmHeroes() {
    let tempHeroList = [];
    // console.log(premium);
    if (premium === false) {
      for (let i = 0; i < NUMHEROES; i++) {
        // making API call to the backend proxy in index.js
        const randomId = genRdmHeroId();
        const response = await fetch(`/api/superhero/${randomId}`);
        const tempHero = await response.json(); // getting hero response data

        // settings up simplified object and stores it in state
        const heroToSet = {
          key: i,
          name: tempHero.name,
          imageUrl: tempHero.image.url,
          combat: detAttValue(tempHero.powerstats.combat),
          strength: detAttValue(tempHero.powerstats.strength),
          speed: detAttValue(tempHero.powerstats.speed),
          intelligence: detAttValue(tempHero.powerstats.intelligence),
          durability: detAttValue(tempHero.powerstats.durability),
          power: detAttValue(tempHero.powerstats.power),
        };
        tempHeroList.push(heroToSet);
      }
    } else if (premium === true) {
      // console.log('jere');
      //adding dominic to the list of superheros
      const fetchHeroesPromises = [];
      for (let i = 0; i < NUMHEROES - 1; i++) {
        // making API call to the backend proxy in index.js
        const randomId = genRdmHeroId();
        const response = await fetch(`/api/superhero/${randomId}`);
        const tempHero = await response.json(); // getting hero response data

        // settings up simplified object and stores it in state
        const heroToSet = {
          key: i,
          name: tempHero.name,
          imageUrl: tempHero.image.url,
          combat: detAttValue(tempHero.powerstats.combat),
          strength: detAttValue(tempHero.powerstats.strength),
          speed: detAttValue(tempHero.powerstats.speed),
          intelligence: detAttValue(tempHero.powerstats.intelligence),
          durability: detAttValue(tempHero.powerstats.durability),
          power: detAttValue(tempHero.powerstats.power),
        };
        tempHeroList.push(heroToSet);
      }
      fetchHeroesPromises.push(
        fetch("http://localhost:3001/character", { method: "GET" }) //GET for get all
          .then((res) => res.json())
          .then((data) => {
            const heroToSet = {
              key: NUMHEROES - 1,
              name: data[1].name,
              imageUrl: "https://difranzo.com/images/profile.jpg", //data.image.url,
              combat: detAttValue(data[1].combat),
              strength: detAttValue(data[1].strength),
              speed: detAttValue(data[1].speed),
              intelligence: detAttValue(data[1].intelligence),
              durability: detAttValue(data[1].durability),
              power: detAttValue(data[1].power),
            };
            tempHeroList.push(heroToSet);
            // console.log((data[0].speed));
          })
      );
      await Promise.all(fetchHeroesPromises);
    }
    setHeroList(tempHeroList);
  }

  // Navigate to battle page with the selected hero's info
  const handleHeroClick = (index) => {
    navigate(`/battle`, { state: { userHero: heroList[index] } });
  };

  // On page startup: generate 3 random heroes
  useEffect(() => {
    getRdmHeroes();
  }, []);

  return (
    <div>
      <div className="container3">
        <h1>Selection Page</h1>

        <h2>Select a hero!</h2>

        <div id="heroSelectionPanel">
          {heroList.map((hero, index) => (
            <div
              className="heroPanel"
              key={index}
              onClick={() => handleHeroClick(index)}
            >
              <h3>{hero.name}</h3>
              <img
                className="heroImage"
                src={hero.imageUrl}
                onError={(e) => {
                  e.target.onerror = null;
                  e.target.src = "/images/no_card.png"; // Path to placeholder image
                }}
              />
              {/* <img className="baseImage" src="images/blank_card.png" alt="Base" /> */}
            </div>
          ))}
        </div>
        <h4>
          Heroes are made by the path they choose, not the powers they are
          graced with -Iron Man
        </h4>
      </div>
    </div>
  );
}

export default SelectionPage;
