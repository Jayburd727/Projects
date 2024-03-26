import React from 'react';

const MusicLayout = ({ children }) => {
  return (
    <div>
      <audio autoPlay loop>
        <source src="music/menu_music.mp3" type="audio/mpeg" />
      </audio>
      {children}
    </div>
  );
};

export default MusicLayout;
