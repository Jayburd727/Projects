const express = require("express"); //imports express
const Character = require("../models/Character.js");
const router = express.Router(); //creates router

router
  .route("/")
  //! Gets ALL Characters
  .get(async (req, res, next) => {
    try {
        const characters = await Character.find();
        res.json(characters);
      } catch (error) {
        console.error('Error fetching characters:', error);
        res.status(500).json({ error: 'Could not fetch characters' });
      }
      //! Creates NEW CHARACTER
  }).post(async (req, res, next) => {
    const characterData = req.body;
    try {
        const newCharacter = new Character(characterData);
        const savedCharacter = await newCharacter.save();
        res.status(201).json(savedCharacter);
      } catch (error) {
        console.error('Error creating character:', error);
        res.status(500).json({ error: 'Could not create character' });
      }
  });


  module.exports = router; //bruh