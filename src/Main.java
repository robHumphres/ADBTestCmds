import ADB.ADBCommand;

public class Main {

    public static void main(String [] args) throws InterruptedException {


        //This is with only one device connected....

        String s;
        ADBCommand adbCommand = new ADBCommand();

        System.out.println("Is there a notification? " +adbCommand.checkForMedbridgeNotification());

        adbCommand.swipeNotificationAway();

    }

}
