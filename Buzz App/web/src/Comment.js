import React, { useState, useEffect } from 'react';
import './Comment.css';

const Comment = ({ postId }) => {
    const [comments, setComments] = useState([]);
    const [newComment, setNewComment] = useState('');
    const [newLink, setNewLink] = useState('');
    const [file, setFile] = useState(null);
    const [base64, setBase64] = useState('');
    const [image, setImage] = useState(null);
    const [link, setLink] = useState(null);

    const fetchComments = async () => {
        try {
            const response = await fetch(`http://localhost:4567/commentByPost/${postId}`, { cache: "no-store" });
            const data = await response.json();

            const linkResponse = await fetch(`http://localhost:4567/Links`, { cache: "no-store" });
            const linkData = await linkResponse.json();
            setLink(linkData.mUrl);


            const imageResponse = await fetch("http://localhost:4567/files", { cache: "no-store" });
            const imageData = await imageResponse.json();
            setImage(imageData.mImage);

            if (data.mStatus === 'ok') {
                const transformedData = data.mData.map((comment, index) => ({
                    id: index + 1,
                    text: comment,
                    f_image: image,
                }));

                setComments(transformedData);
            } else {
                console.error(data.mMessage);
            }
        } catch (error) {
            console.error(error);
        }
    };

    useEffect(() => {
        fetchComments();
    }, []);

    const handleAddComment = async () => {
        try {
            const dataToSend = { mP_ID: postId, mUsername: 'Stephen', mContent: newComment, mLink: newLink, mImage: base64 };
            const response = await fetch('http://localhost:4567/addComment', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(dataToSend)
            });

            const data = await response.json();

            if (data.mStatus === 'ok') {
                const newCommentObject = { id: comments.length + 1, text: newComment };
                setComments([...comments, newCommentObject]);
                setNewComment('');
                setNewLink('');
                setFile(null);
                setBase64('');
            } else {
                console.error(data.mMessage);
            }
        } catch (error) {
            console.error(error);
        }
    };

    const handleFileChange = async (event) => {
        const file = event.target.files[0];
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onloadend = () => {
            setFile(file);
            setBase64(reader.result.split(',')[1]);
        };
    };

    //Function to Update a comment
    const updateComment = async (commentId, content) => {
        const commentData = {
            mId: commentId,
            mContent: content,
        };

        try {
            const response = await fetch(`/addComment/${commentId}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(commentData),
            });

            const responseData = await response.json();
            console.log(responseData);
        } catch (error) {
            console.error(error);
        }
    };

    return (
        <div className="comment-container">
            <h2>Comments</h2>
            {comments.length === 0 && <p>No comments yet</p>}
            {comments.length > 0 && (
                <ul>
                    {comments.map(comment => (
                        <li key={comment.id}>

                            {comment.f_image && <img src={`data:image/jpeg;base64,${comment.f_image}`} style={{ maxWidth: '5%' }} />}
                            {link && <a href={link} target="_blank" rel="noreferrer">Attached Link Comment</a>}
                            <br></br>
                            {comment.text}
                        </li>
                    ))}
                </ul>
            )}
            <div className="comment-form">
                <input
                    type="text"
                    placeholder="Add a comment"
                    value={newComment}
                    onChange={event => setNewComment(event.target.value)}
                />
                <input
                    type="text"
                    placeholder="Add a link (Optional)"
                    value={newLink}
                    onChange={event => setNewLink(event.target.value)}
                />
                <input type="file" onChange={handleFileChange} />

                <button onClick={handleAddComment}>Add Comment</button>
                <button onClick={() => window.location.href = 'http://localhost:4567/addLink'}>Add Link (Optional)</button>
            </div>
        </div>
    );
};

export default Comment;
