import ADB.ADBCommand;

public class Main {

    public static void main(String [] args) throws InterruptedException {


        //This is with only one device connected....

        String s;
        ADBCommand adbCommand = new ADBCommand();

//        System.out.println("Is there a notification? " +adbCommand.checkForMedbridgeNotification());
        adbCommand.lockRotation();
        Thread.sleep(2000);
        adbCommand.lockLandscape();
        Thread.sleep(2000);
        adbCommand.lockReversePortrait();
        Thread.sleep(2000);
        adbCommand.lockReverseLandscape();

        Thread.sleep(2000);

        adbCommand.lockPortrait();





//        adbCommand.swipeNotificationAway();

    }

}
