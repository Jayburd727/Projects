const express = require('express');
const User = require('../models/User.js');
const router = express.Router();
const bcrypt = require('bcrypt');

router.post('/', async function(req, res, next) {
    try {
      let user = req.body.username;
      let pass = req.body.password;
      const docs = await User.findOne({username: user});
      if (docs && await bcrypt.compare(pass, docs.password)) {
        res.status(200).json({'premium': docs.premium});
      } else {
        res.status(404).send("Login Failed");
      }
    } catch(err) {
      console.log(err);
    }
  });

module.exports = router;