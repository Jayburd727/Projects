import React from 'react';

const VictoryMusic = ({ children }) => {
  return (
    <div>
      <audio autoPlay loop>
        <source src="music/victory.mp3" type="audio/mpeg" />
      </audio>
      {children}
    </div>
  );
};

export default VictoryMusic;
