# Getting Started with Create React App

This project was bootstrapped with [Create React App](https://github.com/facebook/create-react-app).

buzzrepo/web/src/App.js
/Users/jayburd/Desktop/Buzz 3/buzzrepo/web/src/App.js
https://bitbucket.org/jsm225/buzzrepo/src/pre-master/web/src/App.js

## Available Scripts

In the project directory, you can run:

### `npm start`

Runs the app in the development mode.\
Open [http://localhost:3000](http://localhost:3000) to view it in your browser.

The page will reload when you make changes.\
You may also see any lint errors in the console.

Note that if Google does not work just change the path in your browser to http://localhost:3000/app
### `npm test`

Launches the test runner in the interactive watch mode.\
See the section about [running tests](https://facebook.github.io/create-react-app/docs/running-tests) for more information.

## Updated overview
- The web Front-end application consist of three webpages (Home,App, and Profile) whose routes are listed in routes.js
- The profile webpage shows user information
- The Home is the start page when initiating the app and allows for signing with google account
- The app contains the interaction with all posts and its comments
- App webpage is composed of multiple subsections each which consists of a javascript file with its correspondant css file

## Backend Note
Due to issues with backend this front-end uses an improvised backend done by front-end developer of phase 3 BUT ARE NOT in backend branch hence not in master branch.
Extra line of app.java  are the following

```java
        /*Changes to Spark*/
        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
            response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }
    
            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if(accessControlRequestMethod != null){
            response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
    
            return "OK";
        });
    
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.type("application/json");
        });

        /*Extra Routes for image/links for comments and posts*/
        public static void getFile(Gson gson,Database db){
            Spark.get("/files", (req, res) -> {
                String imageUrl = "https://cdn.britannica.com/77/116677-050-194755AD/Packard-Laboratory-Lehigh-University-Bethlehem-Pa.jpg";
                byte[] imageBytes = GoogleImagesHelper.getImage(imageUrl);
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                int random = (int)(Math.random() * 2); // generates 0 or 1 randomly
                String mstatus = (random == 0) ? "healthy" : "infected";
                res.header("Content-Type", "application/json");
                //return "{\"mStatus\": \"" + mstatus + "\"}";
                return "{\"mImage\": \"" + base64Image + "\", \"mStatus\": \"" + mstatus + "\"}";
            });
        }

        public static void getLink(Gson gson,Database db){
            Spark.get("/Links", (req, res) -> {
                String imageUrl = "https://www1.lehigh.edu/";
                int random = (int)(Math.random() * 2); // generates 0 or 1 randomly
                String mstatus = (random == 0) ? "healthy" : "infected";
                res.header("Content-Type", "application/json");
                return "{\"mUrl\": \"" + imageUrl + "\", \"mStatus\": \"" + mstatus + "\"}";
            });
        }

```
