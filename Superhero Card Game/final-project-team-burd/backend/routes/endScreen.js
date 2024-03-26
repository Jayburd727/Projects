const express = require('express');
const User = require('../models/User.js');
const router = express.Router();

router.put('/', async function(req, res, next) {
    let username = req.body.user;
    let gameResult = req.body.gameResult;

    let user = await User.findOne({username: username});

    if(!user){
        res.status(404).send("User not found");
    }

    if(gameResult === "victory"){
        user.victory += 1;
    }
    else if (gameResult === "defeat"){
        user.defeat += 1;
    }
    else{
        res.status(404).send("Game Result Not Found");
    }

    await user.save();
    res.status(200).send("User updated");
});

module.exports = router;