# CSE264 Project 4: Making a Blackjack 21 Game using HTML/CSS/DOM

## Jonah Burd
https://drive.google.com/drive/folders/1quHa2x8tzVIgELMfvdWCHS8-IEYobN1c?usp=sharing

In this assignment, you will use HTML, CSS, and JavaScript on the Frontend to create a playable blackjack 21 game. You will also record win and losses to a MongoDB to be displayed on the frontend.

## Important Files

### Routes

routes/index.js
routes/score.js

### Frontend

public/javascripts/main.js
public/stylesheets/style.css

### Database

app.js



## How To Run
npm install
npm start


### REST API for Cards
You will use the [Deck of Card API](http://deckofcardsapi.com/) as a way to create a Deck of Cards, take cards out of that deck, and manage the hands of the player and the dealer. It is a very simple API that provides images of cards, values, creating hands and decks, etc. The listed webpage has all the details on using the API. 

### Blackjack Game
Blackjack is a simple card game between a dealer and a player. The goal is to get 21 points, without going over 21 (busting). A player wins if they:
* get 21 points on the player's first two cards (called a "blackjack" or "natural"), without a dealer blackjack;
* reach a final score higher than the dealer without exceeding 21
* let the dealer draw additional cards until their hand exceeds 21 ("busted").

The game goes as follows:
* Both the dealer and the player get two cards from the deck. The first card from the dealer is visible to the player, but the second is not.
* The player goes first and is allowed to ask for another card (Hit) or end their turn (Stay). If the player goes over 21, the game is over and the dealer wins.
* Next the dealer gets to go. They flip their hidden card and must get new cards until they have at least 17 points. Then they stop once they reach or go beyond 17. If they go over 21, the player wins.
* If neither the player nor the dealer goes over 21, then whoever has the most points wins. If they have the same points, the game is a tie.

Number cards are worth the number listed on the card. Face cards (King, Queen, Jack) are worth 10 points. And Aces are worth 11 or 1 points (use the value that ensures you do not go over 21).

### Install and Run
You must have node.js running on your machine. Once you have cloned this project you can run `npm install` to install all the packages for this project. Then running `npm run dev` will run the dev version of this code, which will run this project with nodemon. Nodemon auto-restarts the node server every time you make a change to a file. Very helpful when you are writing and testing code.

### .env and MongoDB
You need to have a MongoDB server running before launching your API. You can
download MongoDB [here](https://www.mongodb.com/try/download/community), or install it via a package manager.
Windows users, read [Install MongoDB on Windows](https://www.mongodb.com/docs/manual/tutorial/install-mongodb-on-windows/).

You can also use
[MongoDB Atlas](https://www.mongodb.com/atlas/database) instead of downloading and installing MongoDB locally. 

Which ever you do, you will need to cretae a .env from the .env.example 
You can do this by `cp .env.example .env`

Then store your MongoDB URI connection in your  `.env` file.

**Note:** Never EVER push private information (like MongoDB connection URIs) to a Git Repo. We use .env to store this connection inforation and ensure that git (using .gitignore) never pushs this private information in the repo. Never ever store real credentials in `.env.example` or anywhere that is not `.env` as you may push these changes to your git repo.

### Get Hosted with MongoDB Atlas

- Go to [https://www.mongodb.com/cloud/atlas](https://www.mongodb.com/cloud/atlas)
- Click the green **Try Free** button
- Fill in your information then hit **Create your Atlas account**
- You will be redirected to an Overview page.
- Click on the green **Create** button underneath "Create a deployment"
- Select the free **M0** configuration.
- Give Cluster a name (default: Cluster0)
- Click on the green **Create** button
- Now, to access your database you need to create a DB user. You should have been redirected to the **Quickstart** tab in the **Security** section on the left-hand side of the webpage.
- Create a new Mongo user with credentials of your choice (it WILL NOT & SHOULD NOT be shared with anybody besides yourself.)
- Add `0.0.0.0/0` to your IP Access List in the menu below the user creation menu.
- Press `Finish and Close`. This will redurect you back to your **Overview** section.
- Click on **Connect** in the **Database Deployments** card in the **Overview** section.
- In the new screen, select **Node.js** as Driver and version **5.5 or later**.
- Finally, copy the URI connection string and replace the URI in MONGODB_URI of `.env`in your project directory (if you don't see it, create a copy of `.env.example` and name it as `.env`) with this URI string.  Make sure to replace the <PASSWORD> with the db User password that you created under the Security tab.
- Note that after some of the steps in the Atlas UI, you may see a banner stating `We are deploying your changes`.  You will need to wait for the deployment to finish before using the DB in your application.



### Grading
* **70 Points** - Game plays correctly (with correct scores, win and loss states, etc)
* **10 Points - Create a 5-minute video and include a link to it in this README that covers and explains how your code works. 
* **15 Points** - Correctly records each round of the game in MongoDB and update score tally.
* **5 Points** -  Code is well commented and easy to read/follow.
* **BONUS 10 Points** - add animations to show cards moving from deck to player/dealer hands, animations to flip the card, 1 second delays on cards moving/flipping/updating

* If code doesn't run/compile you can get no more than a 65, although this score can be much lower. But please write comments and a README to explain what you were trying to do. 


