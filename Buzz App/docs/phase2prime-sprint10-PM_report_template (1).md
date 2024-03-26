# Phase 2' Sprint 10 - PM Report Template
Use this form to provide your project manager report for Phase 2' (Prime).  Please give detailed answers.
In addition to uploading to coursesite, version control this in the `master` branch under the `docs` folder.

## Team Information [10 points]

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


## Beginning of Phase 2' [20 points]
Report out on the Phase 2 backlog and any technical debt accrued during Phase 2.

1. What required Phase 2 functionality was not implemented and why? 
    * Web working in dokku because routes were not implemented appropiatly
    * Web routes overall and their associated functionalities
1. What technical debt did the team accrue during Phase 2?
    * Web routes, OUATH failing only button implemented, only UI/UX for POST


## End of Phase 2' [20 points]
Report out on the Phase 2' backlog.

1. What required Phase 2 functionality still has not been implemented or is not operating properly and why?
    * Web working in dokku because routes were not implemented appropiatly
    * Web routes overall and their associated uses only UI/UX
    * Web Oauth bugs after the attempt does not succesfully validates with backend
1. What technical debt remains?
    * Web routes and ouath improvement
    * Restructure all code because is hard to read/scale

Screenshot of Trello board (at the close of phase 2'):
(should match/support list of backlog and tech debt items)

## Role reporting [50 points total, 10 points each (teams of 4 get 10 free points)]
Report-out on each team members' activity, from the PM perspective (you may seek input where appropriate, but this is primarily a PM related activity).

1. If there was any remaining Phase 2 functionality that needed to be implemented in Phase 2', what did the PM do to assist in the effort of getting this functionality implemented and operating properly?

PM did pair programming with all roles in class and some off class and in average worked +20 hours.

### Back-end

1. What did the back-end developer do during Phase 2'?
* Minor SQL improvement to solve bugs that appeared in very specific situations
* Improved Unit Testing

1. Describe the engagement of this component's developer with the team (How effective was the process? How was communication with the team - use of Slack and Trello, attendance and participation in meetings, etc. How were tasks created? How was completion of tasks verified?)
* Very Effective, she started very early and was always updating. Good programming techniques. Did many hours of pair programming.Participated in all meetings.

1. Assess the completeness of this component (list remaining backlog item(s), if any)
* All SQL, Oauth, and routes working correctly.

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
* /authenticate

1. Assess the quality of the back-end code
* Good, clean and scalable code. Might need some further documentation though

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
* None

### Admin

1. What did the admin front-end developer do during Phase 2'?
* Add commands to manage posts and restrict activity of evil users

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
* Read Pull Request small modification and approved. Also got a photo sent of how it looked in the interface

1. What was the biggest issue that came up in code review of the admin app?
* Nothing

1. Is the admin app code appropriately organized into files / classes / packages?
* Yes

1. Are the dependencies in the pom.xml file appropriate? Were there any unexpected dependencies added to the program?
* Yes and No

1. Evaluate the quantity and quality of the unit tests for the admin app
* Good amount to check the essentials

1. Describe any technical debt you see in the admin app, if any
* No tech debt

### Web

1. What did the web front-end developer do during Phase 2'?
* Improve UI/UX for comments and OUATH. Attemp route for Oauth

1. Describe the engagement of this component's developer with the team (How effective was the process? How was communication with the team - use of Slack and Trello, attendance and participation in meetings, etc. How were tasks created? How was completion of tasks verified?)
* Good communication, although some skill deficiencies to find some crucial bugs that will complicate the web situation at the last minute

1. Assess the completeness of this component (list remaining backlog item(s), if any)
* Web Ouath routes, all web routes and re-strcuture code for scalability and unit tests updates

1. Describe the different models and other templates used to provide the web front-end's user interface
* Three main ones including the login google page, the profile one and the main one after loggin which contains the interaction with the posts

1. Assess the quality of the Web front-end code
* Multiple bugs and requires restructured

1. Describe the code review process you employed for the Web front-end
* Did pair-programming for 16 hours, code review was live

1. What was the biggest issue that came up in code review of the Web front-end?
* Front-end requires more practice in React but did his effort. Misunderstanding in technical terms led to slow pair programming

1. Is the Web front-end code appropriately organized into files / classes / packages?
* Yes

1. Are the dependencies in the package.json file appropriate? Were there any unexpected dependencies added to the program?
* Yes

1. Evaluate the quantity and quality of the unit tests for the Web front-end
* Not working appropiatly although interesting points to be tackled in unit tests

1. Describe any technical debt you see in the Web front-end, if any
* Web Ouath routes, all web routes and re-strcuture code for scalability and unit tests updates. Also connection to Dokku

### Mobile

1. What did the mobile front-end developer do during Phase 2'?
* Fixed Oauth
1. Describe the engagement of this component's developer with the team (How effective was the process? How was communication with the team - use of Slack and Trello, attendance and participation in meetings, etc. How were tasks created? How was completion of tasks verified?)
* Perfect Very active, tackled directly his part
1. Assess the completeness of this component (list remaining backlog item(s), if any)
* All complete
1. Describe the activities that comprise the Mobile app
* Nothing
1. Assess the quality of the Mobile code
* Needs better in-line documentation but good
1. Describe the code review process you employed for the Mobile front-end
* Pull Request and small pair programming
1. What was the biggest issue that came up in code review of the Mobile front-end?
* None
1. Is the Mobile front-end code appropriately organized into files / classes / packages?
* Yes
1. Are the dependencies in the pubspec.yaml (or build.gradle) file appropriate? Were there any unexpected dependencies added to the program?
* Yes
1. Evaluate the quantity and quality of the unit tests for the Mobile front-end here
* Good enought to test essential functionalities
1. Describe any technical debt you see in the Mobile front-end, if any
* None

### Project Management
Self-evaluation of PM performance

1. When did your team meet with your mentor, and for how long?
* One for around 15 minutes on Friday
1. Describe how the team worked together in Phase 2'. Were all members engaged? Was the work started early in the week or was there significant procrastination?
* All good, web started a bit late and was not alert of some serious bugs.
1. Describe your use of Trello.  Did you have too much detail?  Too little?  Just enough? Did you implement policies around its use (if so, what were they?)?
* Enough detail for members to understand their parts
1. How did you conduct team meetings?  How did your team interact outside of these meetings?
* Online and In person, in and outside class
1. What techniques (daily check-ins/scrums, team programming, timelines, Trello use, group design exercises) did you use to mitigate risk?
* Team programming and checkins
1. Describe any difficulties you faced in managing the interactions among your teammates? Were there any team issues that arose?
* None
1. How well did you estimate time during the early part of the phase?  How did your time estimates change as the phase progressed?
* All good. Problem focused a lot in backend when it should have been in web

1. What aspects of the project would cause concern for your customer right now, if any?
* Web overall except for its UI/UX

1. Describe any concerns you may have about the prospects for success moving forward? What steps can the team take to reduce your concern?
* Fixing web will take many hours and the ones that will be able to manage that complex responsability are extremely busy last weeks
1. Describe the most significant obstacle or difficulty your team faced.
* Web