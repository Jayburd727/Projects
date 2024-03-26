import React, { useEffect, useState } from 'react';
import './Post.css';
import Comment from './Comment';
import { v4 as uuidv4 } from 'uuid';

//Post Object
const Post = ({ postId }) => {

  //Start Post object
  const [post, setPost] = useState(null);
  const [image, setImage] = useState(null);
  const [link, setLink] = useState(null);

  //Function to get the post and image
  useEffect(() => {
    const fetchPost = async () => {
      try {
        const response = await fetch(`http://localhost:4567/messages/${postId}`, { cache: "no-store" });
        const data = await response.json();
        setPost(data.mData);


        const linkResponse = await fetch(`http://localhost:4567/Links`, { cache: "no-store" });
        const linkData = await linkResponse.json();
        setLink(linkData.mUrl);


        const imageResponse = await fetch("http://localhost:4567/files", { cache: "no-store" });
        const imageData = await imageResponse.json();
        setImage(imageData.mImage);

      } catch (error) {
        console.error(error);
      }
    };

    // Check if post is already set before fetching new data
    if (!post) {
      fetchPost();
    }

  }, [postId, post]);

  //Function to Update a post
  const updateMessage = (id, title, content) => {
    const requestOption = {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ mTitle: title, mContent: content })
    };
    fetch(`/messages/${id}`, requestOption)
      .then(response => response.json())
      .then(data => console.log(data))
      .catch(error => console.log(error));
  };

  //Delete Post NOT able to do by user but just in case
  const handleDelete = async (id) => {
    try {
      const response = await fetch(`/messages/${id}`, {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
        },
      });

      const responseData = await response.json();
      console.log(responseData);
    } catch (error) {
      console.error(error);
    }
  };


  //Loading
  if (!post) {
    return <p>Loading...</p>;
  }

  //Manage Upvote
  const handleUpVote = () => {
    /*
    const currentUser = localStorage.getItem("username");
    const voteData = {
      mPostID: postID,
      mUsername: currentUser,
    };

    try {
      const response = await fetch(`/upVote/update/${currentUser}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(voteData),
      });

      const responseData = await response.json();
      console.log(responseData);
    } catch (error) {
      console.error(error);
    }
    */
    setPost(prevPost => {
      return {
        ...prevPost,
        mUpVote: prevPost.mUpVote + 1,
      };
    });
  };

  //Manage Downvote
  const handleDownVote = () => {
    /*
    const currentUser = localStorage.getItem("username");
    const voteData = {
      mPostID: postID,
      mUsername: currentUser,
    };

    try {
      const response = await fetch(`/downVote/update/${currentUser}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(voteData),
      });

      const responseData = await response.json();
      console.log(responseData);
    } catch (error) {
      console.error(error);
    }
    */
    setPost(prevPost => {
      return {
        ...prevPost,
        mDownVote: prevPost.mDownVote + 1,
      };
    });
  };

  //Post HTML return
  return (
    <div className="post-container">
      <h2 className="post-title">{post.mTitle}</h2>
      <p className="post-content">{post.mContent}</p>
      <img src={`data:image/png;base64,${image}`} alt="Post image" style={{ maxWidth: '10%' }} />
      {link && <a href={link} target="_blank" rel="noreferrer">Attached Link</a>}
      <div className="post-votes">
        <p className="vote-count">{post.mUpVote}</p>
        <button className="upvote-button" onClick={handleUpVote}></button>
        <p className="vote-count">{post.mDownVote}</p>
        <button className="downvote-button" onClick={handleDownVote}></button>
      </div>
      <p className="username">Author: {post.mUsername}</p>
      <hr />
      <Comment postId={postId} />
    </div>
  );
};

export default Post;
