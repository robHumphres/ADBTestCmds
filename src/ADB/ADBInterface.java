package ADB;

public interface ADBInterface {
    void lockPortrait();
    void lockReversePortrait();
    void lockLandscape();
    void lockReverseLandscape();
    void lockRotation();
    void enableAutoRotation();
    void screenShot();
    void startScreenRecord();
    void stopScreenRecord();
    void enableAirplaneMode();
    void disableAirplaneMode();
    void swipeNotificationAway();
    boolean checkForMedbridgeNotification();
}




