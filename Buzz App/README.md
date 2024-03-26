# Master Branch README Document  

## Team Information  
    * Team 17  
    * Team Power  

## Group Members    
    * Mackenzie Kramer: mjk224@lehigh.edu    
    * Jack Mishkin: jsm225@lehigh.edu   
    * Jonah Burd: job224@lehigh.edu  
    * Alonso Cornejo Leon: dac323@lehigh.edu    
    * Mo Wu: mow225@lehigh.edu    

## Project Description
    * The goal is to create a cloud-hosted web system, plus a mobile app, where employees can post short ideas, post comments and other employees can upvote/downvote from that idea.

## Functionality in Latest Release:  
    * Web front-end allows users to see a list of ideas, create ideas, delete ideas, and upvote/downvote ideas, and generate comments. Files and links are allowed for commnets and ideas. 
    * Mobile app allows users to see a list of ideas, create ideas, generate comments and upvote/downvote ideas. Files and links are allowed for commnets and ideas. 
    * Admin's java program allows for the creation of tables, deletion of tables, and maintenance of tables as well as to manage users. Admin can invalidate an file and list all the documents which is valid from google drive.
    * Backend has a dynamic web server with appropriate routes and allows for upvote/downvote of ideas and the creation and deletion of posts and comments. Caching and store files in a seperate google acoount is allowed.
    * There are bugs with Dokku for the web and mobile parts. Still working through them. 
    * Everything is able to be ran locally and remotely on Dokku.  
    

## Instructions for Running Locally:
    * Web
        * Make sure REACT is downloaded on your device
        * Be on the web branch and cd into the web folder
        * In the command line, type npm start. This will launch the web-front end locally. 
        * Run npm test to launch the unit test of the server
        * In the backend folder run POSTGRES_IP=castor.db.elephantsql.com POSTGRES_PORT=5432 POSTGRES_USER=ozhubnpc POSTGRES_PASS=**** mvn exec:java to get the HTTP protocols working. 
    * Backend (Note *** stands for secret)
        * mvn clean
        * PORT=8998 POSTGRES_IP=castor.db.elephantsql.com POSTGRES_PORT=5432 POSTGRES_USER=ozhubnpc POSTGRES_PASS=*** CLIENT_ID=*** mvn package 
        * PORT=8998 POSTGRES_IP=castor.db.elephantsql.com POSTGRES_PORT=5432 POSTGRES_USER=ozhubnpc POSTGRES_PASS=*** CLIENT_ID=*** mvn exec:java
    * Mobile
        * Open a emulator from the bottom right of vscode.
        * Run start debugging from main.dart
    * Admin
        * Be in the admin folder
        * Run mvn clean
        * Run mvn package
        * Run POSTGRES_IP=castor.db.elephantsql.com POSTGRES_PORT=5432 POSTGRES_USER=ozhubnpc POSTGRES_PASS=**** mvn exec:java

## Instructions for Running on Dokku:
    * Backend
        * Do any Updates and then add/commit updates
        * git push dokku backend-dokku:master
        * http://2023sp-group17.dokku.cse.lehigh.edu/messages (Current Main Page)  
    * Mobile
        * Open a emulator from the bottom right of vscode.
        * Run start debugging from main.dart 
    * Web Front-End
        * Make sure to run the backend instructions to get Dokku running. 
        * Put the link: http://2023sp-group17.dokku.cse.lehigh.edu into web browser. 

## Code Documentation
    * Admin
        * https://drive.google.com/drive/u/1/folders/0AOcf2o2dmdwOUk9PVA
    * Mobile
       * https://docs.google.com/document/d/1CuLOajm8qJDpxphUfsibyR91C-we0yXbNvezMODGEgk/edit?usp=sharing
    * Web
        * https://bitbucket.org/jsm225/submit-repo/src/master/web/README.md
    * Backend
        * https://docs.google.com/document/d/1SL3OKm-KcbZURtYFeD8qPqZPp0uqF_p0jY-H6gRb_ms/edit

## Project Design and Planning Artifacts 
    * https://bitbucket.org/jsm225/buzzrepo/src/master/docs/README-Phase3.md