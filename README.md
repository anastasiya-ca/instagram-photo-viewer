Instagram Photo Viewer - android app
======================

Week 1 Project: Instagram Photo Viewer

Instagram Photo Viewer is a basic android app that allows a user to check out popular photos from Instagram.
The app utilizes Instagram API to upload photos and displays images and basic photo information to the user. The app includes the following functionality:

* [x]	User can **scroll through current popular photos** from Instagram.
* [x]	User can **see basis photo information**:
- Photo header with user profile image, user name, location (if any) and elapsed time since photo added to Instagram.
- Additional photo details: caption (if any), number of likes and latest 2 comments (if any). 
* [x]	User can **pull-to-refresh** popular photo list to get the latest popular photos displayed.

The app was tested on HTC One (Android 4.1.2) and on AVDs.

Walkthrough of implemented user stories:


![Video Walkthrough](instagram_photo_viewer_app_demo.gif)

GIF created with [LiceCap](http://www.cockos.com/licecap/).


The following open-source libraries were used for the project:
-	[Android Asynchronous Http Client] (http://loopj.com/android-async-http/)

-	[Picasso - image downloading and caching library for Android] (http://square.github.io/picasso/)

Points to consider for future development:
-	Shorter elapsed time representation to leave more space for username
