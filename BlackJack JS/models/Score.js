const mongoose = require("mongoose");

const scoreSchema = new mongoose.Schema({
  winner: String, // 'player' or 'dealer'
  winningHand: [String], // Array of card codes like ['KH', 'QC']
});

const Score = mongoose.model("Score", scoreSchema);

module.exports = Score;
