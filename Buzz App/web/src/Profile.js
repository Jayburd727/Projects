import { Link } from 'react-router-dom';
import './Profile.css';
import React, { useState } from 'react';
import axios from 'axios';

function Profile() {
  const [mGenderIdentity, setGenderIdentity] = useState('');
  const [mSexualOrientation, setSexualOrientation] = useState('');
  const [mNote, setNote] = useState('');

  const handleGenderIdentityChange = (e) => {
    setGenderIdentity(e.target.value);
  };

  const handleSexualOrientationChange = (e) => {
    setSexualOrientation(e.target.value);
  };

  const handleNoteChange = (e) => {
    setNote(e.target.value);
  };

  const handleSave = async () => {
    try {
        const username = 'Stephen'; // Replace with actual username
        if (mGenderIdentity) {
            await axios.put(`http://localhost:4567/updateUser/genderIdentity/${username}`, { mGenderIdentity });
        }
        if (mSexualOrientation) {
            await axios.put(`http://localhost:4567/updateUser/sexualOrientation/${username}`, { mSexualOrientation });
        }
        if (mNote) {
            await axios.put(`http://localhost:4567/updateUser/note/${username}`, {mNote });
        }
        alert('Profile updated successfully');
        setGenderIdentity('');
        setSexualOrientation('');
        setNote('');

    } catch (error) {
      console.error(error);
      alert('Error updating profile');
    }
  };

  return (
    <div className="profile">
      <h1>Stepehen</h1>
            <div className="profile__picture">
                <img src="https://www.lehighvalleylive.com/resizer/DJvRqcH_MyIRqA_V7uHjscuIEjw=/1280x0/smart/advancelocal-adapter-image-uploads.s3.amazonaws.com/image.lehighvalleylive.com/home/lvlive-media/width2048/img/thebrownandwhiteblog/photo/2sd1d-ozdq5omc3o-fgwsnutij5c-spk8mlvifnmrvajpeg-a081820fc46a079e.jpeg" alt="Profile Picture" />
            </div>
            <div className="profile__info">
                <ul>
                    <li>Email: stephen@lehigh.edu</li>
                </ul>
            </div>
                <div className="input_part">
                <label htmlFor="genderIdentity">Gender Identity:</label>
                <input type="text" id="genderIdentity" value={mGenderIdentity} onChange={handleGenderIdentityChange} />
                <label htmlFor="sexualOrientation">Sexual Orientation:</label>
                <input type="text" id="sexualOrientation" value={mSexualOrientation} onChange={handleSexualOrientationChange} />
                <label htmlFor="note">Note:</label>
                <input type="text" id="note" value={mNote} onChange={handleNoteChange} />
                <button onClick={handleSave}>Save</button>
            </div>

            <div className="profile__button">
                <Link to="/app"><button>Back to Dashboard</button></Link>
            </div>

    </div>
  );
}

export default Profile;

