# ADBTestCmds

This API was built for automation testing some of the following:
- Lifecycle of a program by switching between (portrait, reverse portrait, landscape, reverse landscape).
- Cause interrupts for imitaaasdfting network failures switching from a connection to airplane mode
- Allows for screenshots
- Reading notifications, and swiping them away. 
- Screen Recording
- Locking & Unlocking rotation
- As well as gives you the ability to execute an ADB command directly.

There's an interface built to clarify the functionalities of this api. They are as followed:

public interface ADBInterface {
- void lockPortrait();
- void lockReversePortrait();
- void lockLandscape();
- void lockReverseLandscape();
- void lockRotation();
- void enableAutoRotation();
- void screenShot();
- void startScreenRecord();
- void stopScreenRecord();
- void enableAirplaneMode();
- void disableAirplaneMode();
- void swipeNotificationAway();
- boolean checkForMedbridgeNotification();
 }
 
 
 # Example of running a command
 
 - ADBInterface interface = new ADBInterface();
 
 - interface.lockRotation();
 - interface.lockReverseLandscape()

- Will change the current phones orientation to landscape, and then the api will check to see if the orientation changed.
- If the orientation command didn't go through, will recursively try to go again.



# To Get Started
To get started all you need to do is clone the project and copy out the ADB Package to the test_suite you're wanting to use. After that as long as you import the package you should be able to create the instance and run the function that you're wanting to use.
