# Phase 1 Sprint 6 - PM Report Template
Use this form to provide your project manager report for Phase 1 Sprint 6.  Please give detailed answers.
In addition to uploading to coursesite, version control this in the `master` branch under the `docs` folder.

## Team Information [25 points]

Team Information:  
* Number: 17  
* Name: Power  
* Mentor: Ayon Browick, ayb224@lehigh.edu  

Team Roles:  
* Project Manger: Mackenzie Kramer, mjk224@lehigh.edu  
* Backend developer: Alonso Cornejo Leon, dac323@lehigh.edu  
* Admin developer: Jack Mishkin, jsm225@lehigh.edu  
* Web developer: Jonah Burd, job224@lehigh.edu  
* Mobile developer: Mo Wu, mow225@lehigh.edu   

Essential links for this project:  
* Team's Dokku URL(s) (live web front-end link)  
    * http://2023-group17.dokku.cse.lehigh.edu   
    * http://192.168.0.167:3000   
* Team's software repo (bitbucket)  
    * https://bitbucket.org/jsm225/buzzrepo/src/master/  
* Team's Trello board  
    * https://trello.com/invite/b/dsq853yg/ATTIed0f703de086d126546c4ae44fdfe80043733BB6/team-power-phase-1   


## Role reporting [75 points total, 15 points each (teams of 4 get 15 free points)]
Report-out on each role, from the PM perspective.
You may seek input where appropriate, but this is primarily a PM related activity.

### Back-end

1. Overall evaluation of back-end development (how was the process? was Trello used appropriately? how were tasks created? how was completion of tasks verified?)
    * Alonso did a great job with the backend development. He started early and had everything completed by Tuesday night. He met with our mentor to ensure he met the requirements and communicated really well with our team. I created the tasks on Trello for him to follow. I made sure that each of the tasks were small enough to be done within a day so he could see progress and keep our team in the loop of exactly where he was at. He kept me in the loop so I was able to update the Trello board accordingly. I met with him over zoom to verify the completion of the tasks
1. List your back-end's REST API endpoints
    * /
    * /messages
    * /messages/:id
    * /likes/:id
    * /updlikes/:id
1. Assess the quality of the back-end code
    * The backend code is very high quality. There could be a little bit of improvement to documentation that will be added moving forward, but the code itself is good. It has been evaluated by our mentor as well and everything was approved. There doesn’t appear to be any debt involved with this code so far, which is a good start for our team. 
1. Describe the code review process you employed for the back-end
    * Alonso and I met over zoom on Tuesday night to perform a code review on the routes and to start looking at the tests he plans to implement. He already had approval from our mentor so the code review was fairly simple. He talked through his code and I checked to make sure he had solid documentation of things so others can understand moving forward.
1. What was the biggest issue that came up in code review of the back-end server?
    * All of the unit tests were not implemented at the time of the first code review so we were not able to test everything to make sure they all worked. Alonso is going to continue working on them throughout the week, but wanted to get some things up and running so the rest of the team could connect to the backend as needed.
1. Is the back-end code appropriately organized into files / classes / packages?
    * Yes, the backend code is organized properly and functions correctly.
1. Are the dependencies in the pom.xml file appropriate? Were there any unexpected dependencies added to the program?
    * The dependencies are all appropriate. There don’t appear to be any extra or unnecessary dependencies.
1. Evaluate the quality of the unit tests for the back-end
    * The quality of the unit tests are good (this was evaluated in a later code review than the initial one where the unit tests were not in place). Alonso met with our mentor and made sure they were high quality as well.
1. Describe any technical debt you see in the back-end
    * There doesn’t appear to be any technical debt thus far into the backend.

### Admin

1. Overall evaluation of admin app development (how was the process? was Trello used appropriately? how were tasks created? how was completion of tasks verified?)
    * Admin development has been okay so far. There was some confusion on where to put each type of code, and what the structure should look like. Struggled with the fact that RowData was changed to PostData, but we worked through a lot of these issues in our code review.
1. Describe the tables created by the admin app
    * Tables are created like the ones from the tutorial. When a table is created and the user tries to create another one there is an error. The character limit for the messages/titles follows what is required in the deliverables. 
1. Assess the quality of the admin code
    * The admin code is high quality. Jack met with both our mentor and another TA in office hours to show the code and check its quality. It performs the desired functions and matches the backend’s code as desired for moving forward. 
1. Describe the code review process you employed for the admin app
    * I met Jack on zoom on Thursday. We walked through his work and had to make some edits so that it more closely resembled the code from the backend. After fixing the code in App.java and Database.java we talked through how he could add some tests and where in his code there needs to be better documentation.
1. What was the biggest issue that came up in code review of the admin app?
    * Jack wasn’t sure where to put the proper files. We had to move his POM.xml, add some dependencies, and make changes to his database code so that it better reflected the backend. It was a challenge for me to figure out where his errors were coming from, but looking back at past tutorials really helped. 
1. Is the admin app code appropriately organized into files / classes / packages?
    * Now the code is properly organized. We had to make changes for it to get to that point, but it seems to be correct now.
1. Are the dependencies in the pom.xml file appropriate? Were there any unexpected dependencies added to the program?
    * We have fixed the dependency issues. There were originally missing dependencies for maven that made it impossible to compile correctly. After adding that and changing another one, the dependencies are now correct.
1. Evaluate the quality of the unit tests for the admin app
    * The unit tests appear to be high quality. The tests were approved by a TA and by our mentor, so I believe they cover everything that needs to be tested.
1. Describe any technical debt you see in the admin app
    * I don’t see any technical debt at this point in the admin app. 

### Web

1. Overall evaluation of Web development (how was the process? was Trello used appropriately? how were tasks created? how was completion of tasks verified?)
    * I cannot speak on web development. Jonah was not able to make any commits during the week and didn’t update me when I would reach out to him. He did not update the Trello board so I really have no idea where he is at since he didn’t respond to my check in’s with information on what he had done. On Sunday he had an allergic reaction so he wasn’t able to attend the code review, group meeting, or perform a pull request. 
1. Describe the different models and other templates used to provide the web front-end's user interface
    * Unsure due to circumstances
1. Assess the quality of the Web front-end code
    * Unsure due to circumstances
1. Describe the code review process you employed for the Web front-end
    * He wasn’t able to attend or send code due to the Urgent Care trip.
1. What was the biggest issue that came up in code review of the Web front-end?
    * He didn’t show up or send code. Couldn’t review anything. 
1. Is the Web front-end code appropriately organized into files / classes / packages?
    * Unsure due to circumstances
1. Are the dependencies in the package.json file appropriate? Were there any unexpected dependencies added to the program?
    * Unsure due to circumstances
1. Evaluate the quality of the unit tests for the Web front-end 
    * Unsure due to circumstances
1. Describe any technical debt you see in the Web front-end
    * IUnsure due to circumstances

### Mobile

1. Overall evaluation of Mobile development (how was the process? was Trello used appropriately? how were tasks created? how was completion of tasks verified?)
    * Mo was great with mobile development. By Tuesday night he had the ability to create, edit, and delete ideas. He continued working through the week and chipping away until he met all the requirements. Trello could have been used more frequently, but that is a theme across our team. I am hoping we improve on that moving forward. Instead of Trello Mo texted me a lot to keep me updated and would send videos of the newest functionality once it was created.
1. Describe the activities that comprise the Mobile app
    * Our mobile app allows the user to see a list of ideas, create a list of ideas, delete ideas, and like ideas.
1. Assess the quality of the Mobile code
    * The quality of the mobile front-end code appears to be good. There are a few areas that Mo is planning to refine during the phase 1’ week, but overall his MVP meets the requirements and has our group in a very good spot to build from moving forward.
1. Describe the code review process you employed for the Mobile front-end
    * I met with Mo over zoom on Friday to review his code. We walked through the different files and I asked him to explain each function to me. I looked over his code to check for proper documentation, and made recommendations for improvements where appropriate. We met for about 20 minutes.
1. What was the biggest issue that came up in code review of the Mobile front-end?
    * There was a problem when we merged the mobile file into the pre-master branch. There was a target folder that was put in an improper location from another branch that had to be adjusted before we could test everything.
1. Is the Mobile front-end code appropriately organized into files / classes / packages?
    * Yes, it is appropriately organized.
1. Are the dependencies in the pubspec.yaml (or build.gradle) file appropriate? Were there any unexpected dependencies added to the program?
    * Yes, the dependencies are appropriate and there do not seem to be any unexpected dependencies present.
1. Evaluate the quality of the unit tests for the Mobile front-end here
    * The unit tests for mobile are of high quality. Mo wasn’t quite sure how to implement tests, so we worked together and with our mentor on Friday to talk that through. With the help and guidance of our mentor we were able to ensure that the tests covered what they needed to and were of high quality.
1. Describe any technical debt you see in the Mobile front-end here
    * I do not see any technical debt in the mobile front-end thus far into the process of creation.

### Project Management
Self-evaluation of PM performance

1. When did your team meet with your mentor, and for how long?
    * We met with Ayon over zoom on Friday morning at 10am.
1. Describe your use of Trello.  Did you have too much detail?  Too little?  Just enough? Did you implement policies around its use (if so, what were they?)?
    * Our team’s use of Trello is not where I think it needs to be moving forward. I continue to ask people where they are at because they are not updating Trello, so then I update it. I think it could cut down on a lot of unnecessary texts if we could do a better job keeping that updated individually. Overall, the tasks themselves were better created this week. Each task was small enough to be digestible by each team member and created within a day. I think this helped ease people’s minds by allowing them to see progress being made.
1. How did you conduct team meetings?  How did your team interact outside of these meetings?
    * We talked a lot in class and made plans for when we were going to get things done. Outside of class I held zoom meetings to go over code reviews and checked in on teammates' progress daily. We met with our mentor on Friday morning, and had another zoom meeting on Saturday. 
1. What techniques (daily check-ins/scrums, team programming, timelines, Trello use, group design exercises) did you use to mitigate risk?
    * I used daily check in for the most part. When I noticed people weren’t keeping Trello up to date I made sure to start checking in more frequently so I could gain a true grasp of where everyone was at. We also had short zoom meetings throughout the week to verify code progress.
1. Describe any difficulties you faced in managing the interactions among your teammates? Were there any team issues that arose?
    * This week was a little bit rushed due to spring break. My basketball team also was traveling for playoffs, so it was hard to bring everyone together in person. However, I think wanting to be done before spring break was motivating for the team and we worked together really well and efficiently. 
1. How well did you estimate time during the early part of the phase?  How did your time estimates change as the phase progressed?
    * I think our team estimated our time well. Meeting in class on Tuesday and splitting up the tasks bit by bit helped us manage our time. We were able to have goals for each person to have done each day, which prevented people from holding off until the end of the week to do all of it. 
1. What aspects of the project would cause concern for your customer right now, if any?
    * There is no way to track user ID at this point so it isn’t possible to make sure that users can only delete their own posts, or not like a post more than once right now.
1. What is your biggest concern as you think ahead to the next phase of the project?
    * Making sure we have proper documentation so that when we pass the code along to our partners they are able to understand it and move forward efficiently. 
1. Describe the most significant obstacle or difficulty your team faced.
    * The most challenging part of this week was the time crunch. We had some team members leaving on Thursday and Friday for spring break so we tried to have most of the work done by then.