#Hadoop on AWS (HAWS)

This branch extends the file-s3-connector branch and adds distributed nature to the application.

We now have 2 applications.

###Master 
This application functions as a supervisor managing the work done by all jvm instances that are uploading files to s3. Progress information from other jvms is captured and printed on console

###Connector
This application performs the actual upload which sending progress updates to the master application

To test this, start the master instance first then start multiple instances of connector applications and point them to different local directoties. Each connector instance will upload their respective directory and send progress updates to the master. You should see progress from all workers on master console
