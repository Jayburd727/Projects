# Phase 2 Sprint 9 - PM Report Template
Use this form to provide your project manager report for Phase 2 Sprint 9.  Please give detailed answers.
In addition to uploading to coursesite, version control this in the `master` branch under the `docs` folder.

## Team Information [25 points]

Team Information:
* Number: 17
* Name: Team Power
* Mentor: Ayon Bhowmick, ayb224@lehigh.edu

Team Roles:
* Project Manger: Alonso Cornejo Leon, dac323@lehigh.edu
* Backend Developer: Mackenzie Kramer, mjk224@lehigh.edu
* Web developer: Jack Mishkin, jsm225@lehigh.edu 
* Mobile developer: Jonah Burd, job224@lehigh.edu
* Admin developer: Mo Wu, mow225@lehigh.edu

Essential links for this project:
* Team's Dokku URL(s) (live web front-end link)
    * http://2023-group17.dokku.cse.lehigh.edu   
    * http://192.168.0.167:3000   
* Team's software repo (bitbucket)
    * https://bitbucket.org/jsm225/buzzrepo/src/master/
* Team's Trello board
    * https://trello.com/invite/b/dsq853yg/ATTIed0f703de086d126546c4ae44fdfe80043733BB6/team-power-phase-1

Screenshot of Trello board:
(should match/support list of backlog and tech debt items in subheadings below)

## Role reporting [75 points total, 15 points each (teams of 4 get 15 free points)]
Report-out on each role, from the PM perspective.
You may seek input where appropriate, but this is primarily a PM related activity.

### Back-end

1. Describe the engagement of this component's developer with the team (How effective was the process? How was communication with the team - use of Slack and Trello, attendance and participation in meetings, etc. How were tasks created? How was completion of tasks verified?)

* Very Effective, she started very early and was always updating. Good programming techniques. Did many hours of pair programming.Participated in all meetings.
1. Assess the completeness of this component (list remaining backlog item(s), if any)
* All SQL, Oauth, and routes working correctly. Need to add a restrcition to avoid negative number of votes that arise on very specific case scenarios.
1. List your back-end's REST API endpoints
* /
* /messages
* /messages/:id
* /likes/:id
* /addUser
* /updateUser/genderIdentity/:username
* /updateUser/sexualOrientation/:username
* /updateUser/note/:username
* /upVote/update/:username
* /addComment
* /downVote/update/:username
* /comment/:id
* /addComment/:id
* /commentByUsername/:username
1. Assess the quality of the back-end code
* Good, clean and scalable code. Might need some further documentation tho.
1. Describe the code review process you employed for the back-end
* Did pair programming so review was always constant, did multiple manual tests.
1. What was the biggest issue that came up in code review of the back-end server?
* Some queries that will fail given very specific combination of actions.
1. Is the back-end code appropriately organized into files / classes / packages?
* Yes
1. Are the dependencies in the pom.xml file appropriate? Were there any unexpected dependencies added to the program?
* Yes and No
1. Evaluate the quantity and quality of the unit tests for the back-end
* If considering manual tests then there is plenty of test to ensure correctness of code
1. Describe any technical debt you see in the back-end, if any
* Need to add a restrcition to avoid negative number of votes that arise on very specific case scenarios.

### Admin

1. Describe the engagement of this component's developer with the team (How effective was the process? How was communication with the team - use of Slack and Trello, attendance and participation in meetings, etc. How were tasks created? How was completion of tasks verified?)
* Code done in one day, small debug questions and appropiate communication when finished. Participated in all meetings.
1. Assess the completeness of this component (list remaining backlog item(s), if any)
* All done
1. Describe the tables created by the admin app
* Tables are created like the ones from the tutorial. When a table is created and the user tries to create another one there is an error. The character limit for the messages/titles follows what is required in the deliverables. 
* Creation of table actions to record when a user did upvotes/downvotes, comment to store comments, and user to store authenticated user information.
1. Assess the quality of the admin code
* Good,clean, and not redundant
1. Describe the code review process you employed for the admin app
* One-to-one in person meeting, showed me the tests and a quick review was enought to see it was correct
1. What was the biggest issue that came up in code review of the admin app?
* None
1. Is the admin app code appropriately organized into files / classes / packages?
* Yes
1. Are the dependencies in the pom.xml file appropriate? Were there any unexpected dependencies added to the program?
* Yes and No
1. Evaluate the quantity and quality of the unit tests for the admin app
* Good amount to check the essentials
1. Describe any technical debt you see in the admin app, if any
* No tech debt

### Web

1. Describe the engagement of this component's developer with the team (How effective was the process? How was communication with the team - use of Slack and Trello, attendance and participation in meetings, etc. How were tasks created? How was completion of tasks verified?)
* Good communication and participation. Technical problems arise from holiday issues and multiple bugs in the end which led to multiple comments of code that crashed the software.
1. Assess the completeness of this component (list remaining backlog item(s), if any)
* UX/UI is decent but there is work to do on routes.
1. Describe the different models and other templates used to provide the web front-end's user interface
* Three main ones including the login google page, the profile one and the main one after loggin which contains the interaction with the posts
1. Assess the quality of the Web front-end code
* Many bugs, requires better indentation and more documentation
1. Describe the code review process you employed for the Web front-end
* Zoom, review of bugs and proper commenting to prevent front-end to crash
1. What was the biggest issue that came up in code review of the Web front-end?
* Bugs
1. Is the Web front-end code appropriately organized into files / classes / packages?
* Yes
1. Are the dependencies in the package.json file appropriate? Were there any unexpected dependencies added to the program?
* Yes and No
1. Evaluate the quantity and quality of the unit tests for the Web front-end
* Needs more unit tests which can only be tested after fixing the routes issues
1. Describe any technical debt you see in the Web front-end, if any
* Routes FE functionalities
* Google Auth for non-Lehigh Users
* Improve CSS for elements
### Mobile

1. Describe the engagement of this component's developer with the team (How effective was the process? How was communication with the team - use of Slack and Trello, attendance and participation in meetings, etc. How were tasks created? How was completion of tasks verified?)
* Did not answer for two days but when answered had his part done and working in the expected time
1. Assess the completeness of this component (list remaining backlog item(s), if any)
* Fix Oauth issue
1. Describe the activities that comprise the Mobile app
* Our mobile app allows the user to see a list of ideas, create a list of ideas, delete ideas, and like ideas. Also add and edit comments. More allows user google login and to see his or her profile.
1. Assess the quality of the Mobile code
* Clean and well documented
1. Describe the code review process you employed for the Mobile front-end
* In person meeting at class, quick overview and test in-person shown
1. What was the biggest issue that came up in code review of the Mobile front-end?
* None
1. Is the Mobile front-end code appropriately organized into files / classes / packages?
* Yes
1. Are the dependencies in the pubspec.yaml (or build.gradle) file appropriate? Were there any unexpected dependencies added to the program?
* Yes
1. Evaluate the quantity and quality of the unit tests for the Mobile front-end here
* Good enought to test essential functionalities
1. Describe any technical debt you see in the Mobile front-end, if any
* Oauth for non-lehigh email

### Project Management
Self-evaluation of PM performance

1. When did your team meet with your mentor, and for how long?
* One for around 15 minutes on today (4/11)
1. Describe your use of Trello.  Did you have too much detail?  Too little?  Just enough? Did you implement policies around its use (if so, what were they?)?
* One card for each role with subtasks in each card.
1. How did you conduct team meetings?  How did your team interact outside of these meetings?
* Multiple ranging from pair programming for +10hours total in person and zoom. They interact productively. 
1. What techniques (daily check-ins/scrums, team programming, timelines, Trello use, group design exercises) did you use to mitigate risk?
* Interdiary check-ins
1. Describe any difficulties you faced in managing the interactions among your teammates? Were there any team issues that arose?
* No issues we get along each other.
1. How well did you estimate time during the early part of the phase?  How did your time estimates change as the phase progressed?
* Very good timing with backend, but the holidays slowed the process and in the last days there were many bugs front-ends found and some backend had to be restructured
1. What aspects of the project would cause concern for your customer right now, if any?
* The web cannot be used using databases resources
1. What is your biggest concern as you think ahead to the next phase of the project?
* Running out of time
1. Describe the most significant obstacle or difficulty your team faced.
* Time management and dealing with holidays and midterms week