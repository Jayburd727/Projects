const User = require('../models/User.js');
const express = require('express');
const router = express.Router();

const bcrypt = require('bcrypt');
const saltRounds = 10;

/* GET home page. */
router.post('/', async function(req, res, next) {
    try {
        let user = req.body.username;
        let pass = req.body.password;
      let enablePremium = req.body.premium;

      const docs = await User.findOne({username: user});
      if(docs){
          res.status(409).json({ message: 'User already exists' });
      }
      else{
          bcrypt.hash(pass, saltRounds, function(err, hash) {
              // Store hash in your password DB.
              const newUser = new User({
                  username: user,
                  password: hash,
                  premium: enablePremium,
              });
              newUser.save();
              res.status(200).json({ message: 'Registration successful' });
          });
      }
    }
    catch(err){
        console.log(err);
    }
});


module.exports = router;