/**
A React component that allows users to create and display posts with text and likes.
@component
@return {JSX.Element} A React component that renders the post application.
*/
import React, { useState, useEffect } from 'react';
import './App.css';
import Post from './Post';
import Header from './Header';
import NewPost from './NewPost';

function App() {
  //Storage for all Posts
  const [posts, setPosts] = useState([]);

  //Extract All Posts
  useEffect(() => {
    const fetchPosts = async () => {
      try {
        const response = await fetch('http://localhost:4567/messages',{ cache: "no-store" });
        const data = await response.json();
        setPosts(data.mData);
      } catch (error) {
        console.error(error);
      }
    };
    fetchPosts();
  }, []);

  //Add a new POST
  const handleNewPostSubmit = async (newPost) => {
    try {
      const response = await fetch('http://localhost:4567/messages', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newPost),
      });
      const data = await response.json();
      setPosts([...posts, data]);
    } catch (error) {
      console.error(error);
    }
  };

  //Return List of All Posts in page where user can also insert new posts
  return (
    <div>
      <Header />
      <NewPost onSubmit={handleNewPostSubmit} />
      <p style={{ color: 'turquoise', fontSize: '45px', textAlign: 'center', marginTop: '200px',marginBottom: '20px', fontWeight: 'bold' }}>CURRENT POSTS</p>
      {posts.map(post => <Post key={post.mId} postId={post.mId} />)}
    </div>
  );
}


export default App;