import React from 'react';

const BattleMusic = ({ children }) => {
  return (
    <div>
      <audio autoPlay loop>
        <source src="music/battle_music.mp3" type="audio/mpeg" />
      </audio>
      {children}
    </div>
  );
};

export default BattleMusic;
