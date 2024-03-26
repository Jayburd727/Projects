# Burd Brawl Bonanza

## Team Members

- Jonah BURD - job224@lehigh.edu
- Joey Frith - jrf224@lehigh.edu

## Description

A superhero battle game. The user logs in, picks a superhero, and can then fight against other heroes. The user fights against random heroes (controlled by the computer.) The combat system is based on Top Trumps: the user picks a stat for their hero, compares it to the hero of the other user, and the higher is the winning hero. If the user dies, a "Game Over" message appears. If the user gets 3 points, a "You Win" message appears.

## Directions on how to run:
* Clone the repository: ```git clone https://github.com/cse264/final-project-team-burd.git```
* Once git cloned from repository, you have "final-project-team-burd"
* Inside this you have the backend folder and the burd-brawl-bonanza folder

#### Backend
* Copy .env.example to .env in the backend folder
* Modify the .env with correct MongoDB information from Atlas
* Next, go to https://superheroapi.com/. Follow their instructions on getting an access token via Facebook
* Add this token to the .env under the "REACT_APP_HERO_API_TOKEN"

#### Frontend
* Go into frontend folder and copy .env.example to .env
* Modify .env to include the access token created previously

#### Directions to run:
  - Lastly, do npm install for both frontend and backend folder if you have not done so already
  - In order to run, open up two terminals
  - cd into the root of the backend folder and type "npm run dev"
  - In other terminal, cd into the root of the frontend folder and do "npm start"

