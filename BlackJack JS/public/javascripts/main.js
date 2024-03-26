/*
 * This files holds all the code to for your card game
 */

//Run once broswer has loaded everything
window.onload = function () {
  //! Introduction MODAL
  console.log("is DOM loaded???");

  //! Display the modal
  updateScoreTallyDealer();
  updateScoreTallyPlayer();
  let modal = document.getElementById("startModal");
  modal.style.display = "flex";

  //! BustModal NONE at START
  let bustModal = document.getElementById("bustModal");
  bustModal.style.display = "none";

  //! LoserModal NONE at START
  let loserModal = document.getElementById("loserModal");
  loserModal.style.display = "none";

  //! WinModal NONE at START
  let winModal = document.getElementById("winModal");
  winModal.style.display = "none";

  //! TieModal NONE at START
  let tieModal = document.getElementById("tieModal");
  tieModal.style.display = "none";

  //! BlackJackModal NONE at START
  let blackjackModal = document.getElementById("blackjackModal");
  blackjackModal.style.display = "none";

  //! Play Background Music
  let backgroundMusic = document.getElementById("casinoAudio");

  //! Play Card Flip Sound Effect
  let cardflipsoundeffect = document.getElementById("cardFlipAudio");

  //! Event listener for the Deal button
  var dealButton = document.getElementById("dealButton");
  dealButton.addEventListener("click", function () {
    modal.style.display = "none";
    backgroundMusic.play();
    getDeck().then(dealCardBegin); //Starts GAME
  });

  //! Event listener for the Play Again button for if Player BUSTS
  var playAgain = document.getElementById("playAgain");
  playAgain.addEventListener("click", function () {
    bustModal.style.display = "none";
    resetGame();
    backgroundMusic.play();
    getDeck().then(dealCardBegin); //Starts GAME
  });

  //! Event listener for the Play Again button for if Player LOSES
  var playAgain2 = document.getElementById("playAgain2");
  playAgain2.addEventListener("click", function () {
    loserModal.style.display = "none";
    resetGame();
    backgroundMusic.play();
    getDeck().then(dealCardBegin); //Starts GAME
  });

  //! Event listener for the Play Again button for if Player WINS
  var playAgain3 = document.getElementById("playAgain3");
  playAgain3.addEventListener("click", function () {
    winModal.style.display = "none";
    resetGame();
    backgroundMusic.play();
    getDeck().then(dealCardBegin); //Starts GAME
  });

  //! Event listener for the Play Again button for if Player and Dealer Ties
  var playAgain4 = document.getElementById("playAgain4");
  playAgain4.addEventListener("click", function () {
    tieModal.style.display = "none";
    resetGame();
    backgroundMusic.play();
    getDeck().then(dealCardBegin); //Starts GAME
  });

  //! Event listener for the Play Again button for if Player gets BLACKJACK
  var playAgain5 = document.getElementById("playAgain5");
  playAgain5.addEventListener("click", function () {
    blackjackModal.style.display = "none";
    resetGame();
    backgroundMusic.play();
    getDeck().then(dealCardBegin); //Starts GAME
  });

  //! Event listiner for the Hitme button
  document.getElementById("hit-button").addEventListener("click", function () {
    drawAnother();
  });

  //! Event listiner for the Stay button
  document.getElementById("stay-button").addEventListener("click", function () {
    revealDealerSecondCard();
  });

  //! GET Update Score Tally MongoDB Player
  async function updateScoreTallyPlayer(totalPlayer) {
    try {
      const response = await fetch("http://localhost:3000/score");
      const data = await response.json();
      document.getElementById("player-wins").textContent =
        "Player " + data.playerWins; //+ totalPlayer.toString() + " ";
    } catch (error) {
      console.error("Error fetching score tally:", error);
    }
  }

  //! GET Update Score Tally MongoDB Dealer
  async function updateScoreTallyDealer(totalDealer) {
    try {
      const response = await fetch("http://localhost:3000/score");
      const data = await response.json();

      document.getElementById("dealer-wins").textContent =
        "Dealer " + data.dealerWins + " "; //+ totalDealer.toString() + " ";
    } catch (error) {
      console.error("Error fetching score tally:", error);
    }
  }
  //! POST Record Game Round
  async function recordGameRound(
    winner,
    winningHand,
    totalDealer,
    totalPlayer
  ) {
    try {
      await fetch("http://localhost:3000/score", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ winner, winningHand }),
      });

      // Fetch and update the score tally
      updateScoreTallyDealer(totalDealer);
      updateScoreTallyPlayer(totalPlayer);
    } catch (error) {
      console.error("Error recording game round:", error);
    }
  }

  //! Shuffle the Cards:
  const deckUrl =
    "https://deckofcardsapi.com/api/deck/new/shuffle/?deck_count=6";
  let deckId;

  async function getDeck() {
    const res = await fetch(deckUrl);
    const deckDetails = await res.json();
    deckId = deckDetails.deck_id;
    // console.log("Card Deck ID: " + deckId); //Card ID
  }

  //! Deal Cards At The Beginning
  async function dealCardBegin() {
    let dealerCards = await getCard(2); //draw 2 cards for dealer at the beginnning
    let playerCards = await getCard(2); //draw 2 cards for player at the beginning

    //update UI
    displayCards(dealerCards, "dealer");
    displayCards(playerCards, "player");
  }

  //! Deal Cards
  async function dealCard() {
    let dealerCards = await getCard(1); //draw 2 cards for dealer
    let playerCards = await getCard(1); //draw 2 cards for player

    //update UI
    displayCards(dealerCards, "dealer");
    displayCards(playerCards, "player");
  }

  //! Draw a Card:
  async function getCard(number_of_cards) {
    const cardUrl =
      "https://deckofcardsapi.com/api/deck/" +
      deckId +
      "/draw/?count=" +
      number_of_cards;
    const res = await fetch(cardUrl);
    const cardDetails = await res.json();
    // console.log("Card Details: " + cardDetails); //Card Details
    return cardDetails.cards;
  }

  //! Draw Another
  async function drawAnother() {
    let playerCards = await getCard(1); // Draw one card for the player
    displayCards(playerCards, "player"); // Display the new card
  }

  let scoreDealer = 0;
  let scorePlayer = 0;
  let totalDealer = 0;
  let totalPlayer = 0;
  let coveredCard = null;
  let coveredCardImg = null;
  let counter = 0;
  let deckAcesPlayer = 0;
  let deckAcesDealer = 0;
  let playerHands = [];
  let dealerHands = [];

  //! Display Cards
  function displayCards(cards, role) {
    const displayArea = document.getElementById(`${role}-cards`);
    // console.log(`${role}-cards`);

    cards.forEach((card, index) => {
      // console.log((JSON.stringify(card.code)));
      const cardImage = document.createElement("img"); //image element is created for the card visual
      if (role === "dealer" && cards.length === 2 && index === 1) {
        coveredCardImg = card.image;
        coveredCard = getCardValue(card.value, "dealer");
        cardImage.src = "https://deckofcardsapi.com/static/img/back.png"; //show the back image
        dealerHands.push(card.code); //push card code
      } else {
        cardImage.src = card.image; //Otherwise, show the card's image

        if (role === "player") {
          scorePlayer += getCardValue(card.value, "player");
          counter++; //counter for how many cards have been drawn
          playerHands.push(card.code); //push card code
        } else if (role === "dealer") {
          scoreDealer += getCardValue(card.value, "dealer");
          dealerHands.push(card.code); //push card code
        }
      }

      displayArea.appendChild(cardImage);
      // console.log(JSON.stringify(card.image));

      // console.log("# Of Cards:" + counter + " Players Score: " + scorePlayer);
      if (role === "player" && counter >= 2 && scorePlayer === 21) {
        // revealDealerSecondCard();
        blackjackModal.style.display = "flex";
        totalPlayer += 1;
        recordGameRound("player", playerHands, totalDealer, totalPlayer);
        // updateScoreTallyPlayer(totalPlayer); //!HERE
        console.log("BLACKJACK");
      }
    });

    // console.log("Player Hands Code: " + playerHands);
    // console.log("Dealer Hands Code: " + dealerHands);

    // Update scores
    if (role === "player") {
      // console.log("Deck Aces Player: " + deckAcesPlayer);
      // console.log("Deck Aces Dealer: " + deckAcesDealer);
      if (deckAcesPlayer >= 1 && scorePlayer - 10 > 10 && scorePlayer !== 21) {
        //!CHANGING VALUE OF ACE DEPENDING ON SUIT
        // console.log("DeckAcesPlayer: " + deckAcesPlayer);
        // console.log(scorePlayer);
        // console.log("AM I HERE");
        deckAcesPlayer -= 1;
        scorePlayer -= 10;
        // console.log(scorePlayer);
      } else if (scorePlayer > 21) {
        bustModal.style.display = "flex";
        totalDealer += 1;
        recordGameRound("dealer", dealerHands, totalDealer, totalPlayer);
        // updateScoreTallyDealer(totalDealer); //!HERE
        console.log("BUST");
      }
      document.getElementById(`${role}-sum`).textContent =
        scorePlayer.toString();
    } else if (role === "dealer") {
      // if (deckAcesDealer >= 1 && scoreDealer - 10 > 10) {
      //   //!CHANGING VALUE OF ACE DEPENDING ON SUIT
      //   console.log("DeckAcesDealer: " + deckAcesDealer);
      //   console.log(scoreDealer);
      //   console.log("AM I HERE2");
      //   deckAcesDealer -= 1;
      //   scoreDealer -= 10;
      // }
      document.getElementById(`${role}-sum`).textContent =
        scoreDealer.toString();
    }
  }

  //! Reveal Dealers Secret Card
  function revealDealerSecondCard() {
    scoreDealer += coveredCard;

    document.getElementById("dealer-sum").textContent = scoreDealer.toString(); // Update dealer's score display

    // Find the image element representing the hidden card and update its source to the actual card image
    const dealerCardsArea = document.getElementById("dealer-cards");
    const hiddenCardImage = dealerCardsArea.getElementsByTagName("img")[1]; // Second card image

    hiddenCardImage.classList.add("flip-card"); //flip animation applied

    setTimeout(() => {
      hiddenCardImage.src = coveredCardImg; //I changed the image source 1/2 through animation
      cardflipsoundeffect.play();

      setTimeout(dealersTurn, 1000); //dealers turn but only after the card is done flipping
    }, 600); //1s animation duration
  }

  //! Getting Cards Values
  function getCardValue(value, role) {
    // console.log("Score Player getCardValue: " + scorePlayer);
    // console.log("Score Dealer: getCardValue " + scoreDealer);
    if (["JACK", "QUEEN", "KING"].includes(value)) {
      return 10;
    } else if (value === "ACE" && (scorePlayer <= 10 || scoreDealer <= 10)) {
      if (role === "player") {
        deckAcesPlayer += 1;
      } else if (role === "dealer") {
        deckAcesDealer += 1;
      }
      return 11; //!ACE 11
      // } else if (value === "ACE") {
      //   if (role === "player") {
      //     deckAcesPlayer += 1;
      //   } else if (role === "dealer") {
      //     deckAcesDealer += 1;
      //   }
      //   return 1; //!ACE 1
    } else {
      return parseInt(value);
    }
  }

  //! Reset Game (Cards and Score)
  function resetGame() {
    // cards set to 0
    document.getElementById("player-cards").innerHTML = "";
    document.getElementById("dealer-cards").innerHTML = "";

    // Reset scores
    scorePlayer = 0;
    scoreDealer = 0;

    // Reset Hands
    playerHands = [];
    dealerHands = [];

    // score set to 0
    document.getElementById("player-sum").textContent = "0";
    document.getElementById("dealer-sum").textContent = "0";
  }

  //! Dealers Turn
  async function dealersTurn() {
    if (deckAcesDealer >= 1 && scoreDealer - 10 > 10 && scoreDealer !== 21) {
      //!CHANGING VALUE OF ACE DEPENDING ON SUIT
      // console.log("DeckAcesDealer: " + deckAcesDealer);
      // console.log(scoreDealer);
      // console.log("AM I HERE2");
      deckAcesDealer -= 1;
      scoreDealer -= 10;
    }
    while (scoreDealer < 17) {
      let dealerCards = await getCard(1); // draw a card for the dealer
      displayCards(dealerCards, "dealer"); // display new card
      document.getElementById("dealer-sum").textContent =
        scoreDealer.toString();
      if (scoreDealer > 21) {
        winModal.style.display = "flex";
        totalPlayer += 1;
        recordGameRound("player", playerHands, totalDealer, totalPlayer);
        // updateScoreTallyPlayer(totalPlayer); //!HERE
        console.log("Dealer Busted!"); // Handle dealer bust
        break; // Exit the loop if dealer busts
      }
    }

    // After the loop, check if dealer has won or if it's a tie, etc.
    gameOutcome(); // You need to implement this function based on your game rules
  }

  //! Check Game Outcome
  async function gameOutcome() {
    if (scoreDealer > scorePlayer && scoreDealer <= 21) {
      loserModal.style.display = "flex";
      totalDealer += 1;
      recordGameRound("dealer", dealerHands, totalDealer, totalPlayer);
      // updateScoreTallyDealer(totalDealer); //!HERE
      console.log("Dealer Won!");
    } else if (scorePlayer > scoreDealer && scorePlayer < 21) {
      winModal.style.display = "flex";
      totalPlayer += 1;
      recordGameRound("player", playerHands, totalDealer, totalPlayer);
      // updateScoreTallyPlayer(totalPlayer); //!HERE
      console.log("Player Won!");
    } else if (scorePlayer === scoreDealer) {
      tieModal.style.display = "flex";
      console.log("Tie!");
    }
  }

  //All your Front end code should be here!
};
