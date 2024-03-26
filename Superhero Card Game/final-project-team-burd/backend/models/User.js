const mongoose = require('mongoose');

const userSchema = new mongoose.Schema({
    username: String, 
    password: String,
    premium: Boolean,
    victory: Number,
    defeat: Number,
});

const User = mongoose.model('User', userSchema);

module.exports = User;