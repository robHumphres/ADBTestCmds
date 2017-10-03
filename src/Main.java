import ADB.ADBCommand;

public class Main {

    public static void main(String [] args) throws InterruptedException {


        //This is with only one device connected....


        ADBCommand adbCommand = new ADBCommand();

        adbCommand.lockRotation();
        Thread.sleep(8000);
        adbCommand.lockLandscape();
        System.out.println(adbCommand.currentScreenOrientation());//1
        Thread.sleep(8000);
        adbCommand.lockPortrait();
        System.out.println(adbCommand.currentScreenOrientation());//0
        Thread.sleep(8000);
        adbCommand.lockReverseLandscape();
        System.out.println(adbCommand.currentScreenOrientation());//3
        Thread.sleep(8000);
        adbCommand.lockReversePortrait();
        System.out.println(adbCommand.currentScreenOrientation());//2
        Thread.sleep(8000);
        adbCommand.lockPortrait();
        System.out.println(adbCommand.currentScreenOrientation());//0
        adbCommand.enableAutoRotation();
//        adbCommand.screenShot();
//
//        Thread.sleep(8000);
//
//        ADBInterface obj = new ADBCommand();
//
//        adbCommand.enableAirplaneMode();
//        Thread.sleep(10000);
//
//        adbCommand.disableAirplaneMode();


    }

}
