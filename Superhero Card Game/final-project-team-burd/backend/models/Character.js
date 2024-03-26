const mongoose = require('mongoose');

const characterSchema = new mongoose.Schema({
  name: {
    type: String,
    required: true
  },
  combat: {
    type: Number,
    required: true
  },
  strength: {
    type: Number,
    required: true
  },
  speed: {
    type: Number,
    required: true
  },
  intelligence: {
    type: Number,
    required: true
  },
  durability: {
    type: Number,
    required: true
  },
  power: {
    type: Number,
    required: true
  },
  imageUrl: {
    type: String,
    required: false
  }
});

const Character = mongoose.model('Character', characterSchema);

module.exports = Character;
