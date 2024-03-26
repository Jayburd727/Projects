var createError = require("http-errors");
var path = require("path");
var cookieParser = require("cookie-parser");
var logger = require("morgan");
const mongoose = require("mongoose");
const dotenv = require("dotenv");
const fetch = require("node-fetch");  //added instead of cors
const cors = require("cors");

dotenv.config({ path: ".env" });

const express = require("express");

const app = express();

app.use(cors());

var loginRouter = require("./routes/login");
var registerRouter = require("./routes/register");
var endScreenRouter = require("./routes/endScreen");
var characterRouter = require("./routes/character");

const PORT = process.env.PORT || 3001;

mongoose.Promise = global.Promise;

mongoose.connect(process.env.MONGODB_URI, { useNewUrlParser: true });
mongoose.connection.on("error", (err) => {
  console.error(err);
  console.log("MongoDB connection error. Please make sure MongoDB is running.");
  process.exit();
});

app.use(logger("dev"));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, "public")));

// Proxy route
app.get("/api/superhero/:id", async (req, res) => {
  const apiToken = process.env.REACT_APP_HERO_API_TOKEN; // Your API token
  const heroId = req.params.id;
  const url = `https://superheroapi.com/api/${apiToken}/${heroId}`;

  try {
    const apiResponse = await fetch(url);
    const data = await apiResponse.json();
    res.json(data);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

app.use("/login", loginRouter);
app.use("/register", registerRouter);
app.use("/endScreen", endScreenRouter);
app.use("/character", characterRouter);

// Test route
app.get("/api", (req, res) => {
  res.json({ message: "Hello Señor Jonah!" });
});

// 404 error handler
app.use(function (req, res, next) {
  next(createError(404));
});

app.listen(PORT, () => {
  console.log(`Server listening on ${PORT}`);
});

module.exports = app;
