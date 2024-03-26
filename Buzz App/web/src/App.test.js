import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';

describe('App component', () => {
  test('renders without crashing', () => {
    const div = document.createElement('div');
    ReactDOM.render(<App />, div);
    ReactDOM.unmountComponentAtNode(div);
  });

  test('updates state correctly when posting', () => {
    const app = new App();
    const postText = 'This is a test post.';
    app.setState({ text: postText, likes: 0, posts: [] });
    app.handlePostClick();
    expect(app.state.posts.length).toBe(1);
    expect(app.state.posts[0].text).toBe(postText);
    expect(app.state.posts[0].likes).toBe(0);
  });

  // Test case for Post component
  test("renders post text and likes", () => {
    const post = {
      title: "My Post",
      text: "This is my first post",
      likes: 5,
    };
    render(<Post post={post} />);
    const titleElement = screen.getByText("My Post");
    const textElement = screen.getByText("This is my first post");
    const likesElement = screen.getByText("Likes: 5");
    expect(titleElement).toBeInTheDocument();
    expect(textElement).toBeInTheDocument();
    expect(likesElement).toBeInTheDocument();
  });

  // Test case for handleTextChange function
  test("updates text state when typing in textarea", () => {
    const { getByPlaceholderText } = render(<App />);
    const textarea = getByPlaceholderText("Type your text here");
    fireEvent.change(textarea, { target: { value: "Hello world!" } });
    expect(textarea.value).toBe("Hello world!");
  });

  // Test case for handlePostClick function
  test("adds new post to the list when post button is clicked", () => {
    const { getByPlaceholderText, getByText } = render(<App />);
    const textarea = getByPlaceholderText("Type your text here");
    const postButton = getByText("Post");
    fireEvent.change(textarea, { target: { value: "My new post" } });
    fireEvent.click(postButton);
    const postText = screen.getByText("My new post");
    expect(postText).toBeInTheDocument();
  });

  // Test case for handleLikeClick function
  test("updates likes state when like button is clicked", () => {
    const { getByText } = render(<App />);
    const likeButton = getByText("Like (0)");
    fireEvent.click(likeButton);
    expect(likeButton).toHaveTextContent("Like (1)");
  });
});
/* 
// Test for editable comments on ideas
describe('Editable comments on ideas', () => {
  it('should allow the user to edit their comments', () => {
    // Set up the test data
    const comment = { id: 1, text: 'Original comment', author: 'Alice' };
    const updatedComment = { id: 1, text: 'Updated comment', author: 'Alice' };
    const idea = { id: 1, title: 'My great idea', comments: [comment] };

    // Render the component that displays the comment
    const { getByText, getByTestId } = render(<IdeaDetails idea={idea} />);

    // Find the comment in the UI and click the edit button
    const editButton = getByTestId('edit-comment-button');
    fireEvent.click(editButton);

    // Find the textarea for editing the comment and change the text
    const textarea = getByTestId('comment-textarea');
    fireEvent.change(textarea, { target: { value: 'Updated comment' } });

    // Find the save button and click it
    const saveButton = getByTestId('save-comment-button');
    fireEvent.click(saveButton);

    // Check that the comment has been updated in the UI
    const updatedCommentText = getByText('Updated comment');
    expect(updatedCommentText).toBeInTheDocument();
  });
});

// Test for the profile page
describe('Profile page', () => {
  it('should display user information', () => {
    // Set up the test data
    const user = { name: 'Alice', email: 'alice@example.com', sexualOrientation: 'bisexual' };

    // Render the component that displays the user information
    const { getByText } = render(<Profile user={user} />);

    // Check that the user information is displayed in the UI
    const name = getByText('Name: Alice');
    expect(name).toBeInTheDocument();
    const email = getByText('Email: alice@example.com');
    expect(email).toBeInTheDocument();
    const sexualOrientation = getByText('Sexual orientation: bisexual');
    expect(sexualOrientation).toBeInTheDocument();
  });
});

// Test for the login and logout area via Google OAuth
describe('Google OAuth', () => {
  it('should allow the user to log in and log out', async () => {
    // Mock the Google OAuth API
    const googleMock = {
      auth: {
        async signIn() {
          return { user: { name: 'Alice', email: 'alice@example.com' } };
        },
        async signOut() {
          return true;
        },
      },
    };
    jest.spyOn(window, 'gapi').mockReturnValue(googleMock);

    // Render the component that displays the login and logout area
    const { getByTestId } = render(<LoginArea />);

    // Find the login button and click it
    const loginButton = getByTestId('login-button');
    fireEvent.click(loginButton);

    // Wait for the login to complete
    await waitFor(() => expect(getByTestId('logout-button')).toBeInTheDocument());

    // Find the logout button and click it
    const logoutButton = getByTestId('logout-button');
    fireEvent.click(logoutButton);

    // Check that the user is logged out and the login button is displayed again
    expect(getByTestId('login-button')).toBeInTheDocument();
    expect(queryByTestId('logout-button')).not.toBeInTheDocument();
  });
});

//Tech Debt and phase 3 tests
describe('Phase2-3', () => {
  //Valid POST_ID
  it('renders a post with correct data when given a postId prop', () => {
    const postId = 1;
    const post = {
      mTitle: 'Test Post',
      mContent: 'This is a test post',
      mUpVote: 0,
      mDownVote: 0,
      mUsername: 'testuser'
    };
    jest.spyOn(global, 'fetch').mockImplementation(() =>
      Promise.resolve({
        json: () => Promise.resolve({ mData: post })
      })
    );
    const { getByText, getByRole } = render(<Post postId={postId} />);
    expect(getByText(post.mTitle)).toBeInTheDocument();
    expect(getByText(post.mContent)).toBeInTheDocument();
    expect(getByText(`Author: ${post.mUsername}`)).toBeInTheDocument();
    expect(getByRole('button', { name: 'Upvote' })).toBeInTheDocument();
    expect(getByRole('button', { name: 'Downvote' })).toBeInTheDocument();
    expect(getByText('0')).toBeInTheDocument();
    expect(global.fetch).toHaveBeenCalledTimes(1);
    expect(global.fetch).toHaveBeenCalledWith(`http://localhost:4567/messages/${postId}`);
    global.fetch.mockRestore();
  });

  //Loading Messages
  it('renders a loading message when post data is not yet available', () => {
    const { getByText } = render(<Post postId={1} />);
    expect(getByText('Loading...')).toBeInTheDocument();
  });

  //Upvote tests
  it('increments the upvote count when upvote button is clicked', () => {
    const post = {
      mTitle: 'Test Post',
      mContent: 'This is a test post',
      mUpVote: 0,
      mDownVote: 0,
      mUsername: 'testuser'
    };
    jest.spyOn(global, 'fetch').mockImplementation(() =>
      Promise.resolve({
        json: () => Promise.resolve({ mData: post })
      })
    );
    const { getByText, getByRole } = render(<Post postId={1} />);
    fireEvent.click(getByRole('button', { name: 'Upvote' }));
    expect(getByText('1')).toBeInTheDocument();
    fireEvent.click(getByRole('button', { name: 'Upvote' }));
    expect(getByText('2')).toBeInTheDocument();
    expect(global.fetch).toHaveBeenCalledTimes(1); // Make sure fetch is not called again
    global.fetch.mockRestore();
  });

  //Dowvote Votes
  it('increments the upvote count when upvote button is clicked', () => {
  const post = {
    mTitle: 'Test Post',
    mContent: 'This is a test post',
    mUpVote: 0,
    mDownVote: 0,
    mUsername: 'testuser'
  };
  jest.spyOn(global, 'fetch').mockImplementation(() =>
    Promise.resolve({
      json: () => Promise.resolve({ mData: post })
    })
  );
  const { getByText, getByRole } = render(<Post postId={1} />);

  fireEvent.click(getByRole('button', { name: 'Downvote' }));
  expect(getByText('1')).toBeInTheDocument();
  fireEvent.click(getByRole('button', { name: 'Downvote' }));
  expect(getByText('2')).toBeInTheDocument();
  
  expect(global.fetch).toHaveBeenCalledTimes(1); // Make sure fetch is not called again
  global.fetch.mockRestore();

  //Comments
  it('adds a new comment to the post when submitted', async () => {
    const post = {
      mTitle: 'Test Post',
      mContent: 'This is a test post',
      mUpVote: 0,
      mDownVote: 0,
      mUsername: 'testuser',
      mComments: []
    };
    jest.spyOn(global, 'fetch').mockImplementation((url, options) => {
      if (url === 'http://localhost:4567/messages/1') {
        return Promise.resolve({
          json: () => Promise.resolve({ mData: post })
        });
      } else if (url === 'http://localhost:4567/messages/1/comments') {
        const comment = JSON.parse(options.body);
        post.mComments.push(comment);
        return Promise.resolve({
          json: () => Promise.resolve(comment)
        });
      }
    });
    const { getByText, getByLabelText } = render(<Post postId={1} />);
    const newCommentText = 'This is a new comment';
    fireEvent.change(getByLabelText('Add a comment'), { target: { value: newCommentText } });
    fireEvent.click(getByText('Submit'));
    await waitFor(() => expect(getByText(newCommentText)).toBeInTheDocument());
    expect(global.fetch).toHaveBeenCalledTimes(2); // Make sure both fetch calls were made
    expect(post.mComments.length).toBe(1); // Make sure the comment was added to the post
    global.fetch.mockRestore();
  });

  //Links in Posts
    it('inserts a link in a post', () => {
    const post = {
      mTitle: 'Test Post',
      mContent: 'This is a test post with a link to Google: google.com',
      mUpVote: 0,
      mDownVote: 0,
      mUsername: 'testuser'
    };
    jest.spyOn(global, 'fetch').mockImplementation(() =>
      Promise.resolve({
        json: () => Promise.resolve({ mData: post })
      })
    );
    const { getByText } = render(<Post postId={1} />);
    const linkElement = getByText('google.com');
    expect(linkElement.href).toBe('http://google.com/');
    expect(linkElement.target).toBe('_blank');
    global.fetch.mockRestore();
  });

  //Files in Posts
  it('adds a file to a post', () => {
  const post = {
    mTitle: 'Test Post',
    mContent: 'This is a test post with a file attachment',
    mUpVote: 0,
    mDownVote: 0,
    mUsername: 'testuser'
  };
  const file = new File(['test file content'], 'test-file.txt', { type: 'text/plain' });
  jest.spyOn(global, 'fetch').mockImplementation(() =>
    Promise.resolve({
      json: () => Promise.resolve({ mData: post })
    })
  );
  const { getByLabelText, getByText } = render(<Post postId={1} />);
  const inputElement = getByLabelText('Upload file');
  fireEvent.change(inputElement, { target: { files: [file] } });
  const fileElement = getByText('test-file.txt');
  expect(fileElement.href).toBe('http://localhost/test-file.txt');
  expect(fileElement.download).toBe('test-file.txt');
  global.fetch.mockRestore();
});

//Link and Files in comments
it('allows links and file attachments in comments', () => {
  const comment = {
    mContent: 'This is a comment with a link to Google: google.com',
    mFiles: [
      {
        mFileName: 'test_file.jpg',
        mFileSize: 1024,
        mFileType: 'image/jpeg',
        mFileUrl: 'https://example.com/test_file.jpg'
      }
    ],
    mUsername: 'testuser'
  };
  jest.spyOn(global, 'fetch').mockImplementation(() =>
    Promise.resolve({
      json: () => Promise.resolve({ mData: comment })
    })
  );
  const { getByText, getByRole } = render(<Comment commentId={1} />);
  const linkElement = getByText('google.com');
  expect(linkElement.href).toBe('http://google.com/');
  expect(linkElement.target).toBe('_blank');
  const fileElement = getByRole('link', { name: 'test_file.jpg' });
  expect(fileElement.href).toBe('https://example.com/test_file.jpg');
  expect(fileElement.download).toBe('test_file.jpg');
  global.fetch.mockRestore();
});



});



});*/




