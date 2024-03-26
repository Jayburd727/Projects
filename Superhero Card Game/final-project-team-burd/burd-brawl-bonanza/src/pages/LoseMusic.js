import React from 'react';

const LoseMusic = ({ children }) => {
  return (
    <div>
      <audio autoPlay>
        <source src="music/lose.mp3" type="audio/mpeg" />
      </audio>
      {children}
    </div>
  );
};

export default LoseMusic;
