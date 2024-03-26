import React, { useState } from 'react';
import './NewPost.css';

function NewPost(props) {
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [Link, setLink] = useState('');
  const [file, setFile] = useState(null);

  const handleTitleChange = (event) => {
    setTitle(event.target.value);
  };

  const handleContentChange = (event) => {
    setContent(event.target.value);
  };

  const handleLinkChange = (event) => {
    setLink(event.target.value);
  };

  const handleFileChange = (event) => {
    setFile(event.target.files[0]);
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    let encodedFile = null;
    if (file) {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => {
        encodedFile = reader.result.split(',')[1];

        const newPost = {
          mTitle: title,
          mContent: content,
          mLink: Link,
          mFile: encodedFile,
          mUser: 'Stephen',
        };
        props.onSubmit(newPost);
        setTitle('');
        setContent('');
        setLink('');
        setFile(null);
        document.getElementById('file-upload').value = '';
      };
    } else {
      const newPost = {
        mTitle: title,
        mContent: content,
        mLink: Link,
        mUser: 'Stephen',
      };
      props.onSubmit(newPost);
      setTitle('');
      setContent('');
      setLink('');
      setFile(null);
      document.getElementById('file-upload').value = '';
    }
  };

  return (
    <div className="form-container">
      <img
        src="https://www.clker.com/cliparts/v/4/p/F/L/S/happy-bee-hi.png"
        alt=""
      />
      <form onSubmit={handleSubmit}>
        <h1>Post a NEW IDEA!</h1>
        <label htmlFor="title-input">
          <input
            type="text"
            placeholder="Enter Title"
            className="title-input"
            value={title}
            onChange={handleTitleChange}
            id="title-input"
          />
        </label>
        <br />
        <label htmlFor="content-input">
          <textarea
            className="content-input"
            placeholder="Enter Message"
            value={content}
            onChange={handleContentChange}
            id="content-input"
          ></textarea>
        </label>
        <br />
        <label htmlFor="Link-input">
          <input
            type="text"
            placeholder="Enter Link (Optional)"
            className="Link-input"
            value={Link}
            onChange={handleLinkChange}
            id="Link-input"
          />
        </label>
        <br />
        <label>
          <input type="file" onChange={handleFileChange} id="file-upload" />
        </label>
        <br />
        <button type="submit" className="submit-button">
          Submit
        </button>
      </form>
    </div>
  );
}

export default NewPost;
