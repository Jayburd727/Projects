## Listing of User Stories for the Admin and "Anonymous User" Personas:    
#### User Story:
!["picture"](UserStory.PNG)  
#### Admin Story
!["picture"](AdminStory.PNG)

## Tests For Each User Story:    
Tests for user stories will include whether a user can login, logout, add a post, like a post, and dislike a post successfully.  
Tests for the admin stories will include whether admin can login, logout, comment on a post, pin a post, delete a post, create a post, add a like to a post, and dislike a post. 

## System Drawing:    
!["picture"](SystemsDiagram.PNG)

## Drawing of Mock Web/Mobile User Interface:
#### Web
!["picture"](WebMock.PNG)    
#### Mobile
!["picture"](MobileMock.PNG)

## Drawing of State Machine:      
!["picture"](StateMachine.PNG)

## Listing of Routes:    
    * /  -> goes to index.html  
        * Default starting page
    * /message/:id -> get, put, delete, post  
        * Allows users to get a specific post
        * Allows users to update a specific post
        * Allows users to delete a specific post
        * Allows users to create a specific post
        * Passes the int ID of the post variable for manipulation of specific entry
    * /messages -> get  
        * Allows users to get all posts
    * /addLike/:id -> post  
        * Allows users to add a like to a specific post 
        * Passes the int ID of the post variable for "Like" manipulation of specific entry
    * /updateLike/:id -> put  
        * Allows user to either add another like or remove one like from a post
        * Passes the int ID of the post variable for "Like" manipulation of specific entry

## Entity Relationship Diagram:    
!["picture"](ERD.PNG)  

## Listing of Tests for Backend, Admin, Web, and Mobile:  
* Mobile Tests:  
    * Tests if user can see list of ideas
    * Tests if user can create new ideas
    * Tests if user can like ideas
    * Tests if user can remove a like
* Admin tests:  
    * Tests if admin can add a create a table
    * Tests if admin can drop/delete a table
    * Tests if admin can remove an entry from a table
* Web Tests:  
    * Tests if user can see list of ideas
    * Tests if user can create new ideas
    * Tests if user can like ideas
    * Tests if user can remove a like
* Backend Tests:  
    * Create/delete/update an entry
    * Copy constuctor success
    * Access an entry from a group of entries
    * Adding like
    * Updating like 

## Code Documentation
* Mobile
    * https://bitbucket.org/jsm225/buzzrepo/src/pre-master/mobile/flutter/my_app/doc/api/index.html  
* Web 
     * https://bitbucket.org/jsm225/buzzrepo/src/pre-master/web/src/App.js 
* Admin
    * https://bitbucket.org/jsm225/buzzrepo/raw/fb86aa327e4b8ddca9669c37ba8dc903d30643eb/docs/MobileMock.png 
    * https://bitbucket.org/jsm225/buzzrepo/src/master/README.md -> section on code documentation
* Backend
    * https://drive.google.com/drive/folders/1nytIR7O-GthNPTK8cNsHIj4RAmGC9S9b?usp=share_link 