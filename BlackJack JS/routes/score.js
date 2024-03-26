const express = require("express"); //imports express
const Score = require("../models/Score.js");
const router = express.Router(); //creates router
router
  .route("/")
  .get(async (req, res, next) => {
    try {
      // Fetch all the game documents
      const games = await Score.find({});

      // Count wins
      const dealerWins = games.filter(
        (game) => game.winner === "dealer"
      ).length;
      const playerWins = games.filter(
        (game) => game.winner === "player"
      ).length;

      // Include hands and winner
      const winnerDetails = games.map((game) => {
        return {
          winner: game.winner,
          winningHand: game.winningHand,
        };
      });

      res.json({
        dealerWins,
        playerWins,
        winnerDetails, // Additional detail about each game
      });
    } catch (err) {
      res.status(500).send(err.message);
    }
  })

  .post(async (req, res, next) => {
    try {
      const { winner, winningHand } = req.body;

      // Create a new score round record
      const newScore = new Score({ winner, winningHand });
      await newScore.save();

      res.status(201).json(newScore);
    } catch (err) {
      res.status(500).send(err.message);
    }
  });

module.exports = router;
