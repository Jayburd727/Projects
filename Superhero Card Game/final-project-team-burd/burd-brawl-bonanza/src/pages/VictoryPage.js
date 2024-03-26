import React from "react";
import ConfettiGenerator from "confetti-js";
// import "./VictoryPage.css";

function VictoryPage() {
  React.useEffect(() => {
    const confettiSettings = { target: "my-canvas" };
    const confetti = new ConfettiGenerator(confettiSettings);
    confetti.render();

    return () => confetti.clear();
  }, []);
  return (
    <div class="container">
      <img src="images/victory_dance.gif" id="myGif" />
      <h1>You win! Hooray!</h1>
      <canvas id="my-canvas"></canvas>
    </div>
  );
}

export default VictoryPage;
