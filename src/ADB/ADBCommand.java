package ADB;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;


public class ADBCommand implements ADBInterface {
    /*
    user_rotation Values:
    0           # Protrait
    1           # Landscape
    2           # Protrait Reversed
    3           # Landscape Reversed

    accelerometer_rotation Values:
    0           # Stay in the current rotation
    1           # Rotate the content of the screen

    Ex.
    adb shell settings put system accelerometer_rotation 0
    adb shell settings put system user_rotation 3

     */
    private final String
            _lockAccelerometer = "shell settings put system accelerometer_rotation 0",
            _unlockAccelerometer = "shell settings put system accelerometer_rotation 1",
            _sliderDown = "shell input swipe 0 0 0 300",
            _sliderUp = "shell input swipe 0 300 0 0";

    private final int portrait = 0, landscape = 1, portraitRev = 2, landscapeRev = 3;
    private boolean SliderDown = false;

    public void enableAirplaneMode() {
        String enableGlobalAirplaneMode = "shell settings put global airplane_mode_on 1",
                broadcastAirplaneModeChange = "shell am broadcast -a android.intent.action.AIRPLANE_MODE";

        executeADBCmd(enableGlobalAirplaneMode);
        executeADBCmd(broadcastAirplaneModeChange);
    }

    public void disableAirplaneMode() {
        String disableGlobalAirplaneMode = "shell settings put global airplane_mode_on 0",
                broadcastAirplaneModeChange = "shell am broadcast -a android.intent.action.AIRPLANE_MODE";

        executeADBCmd(disableGlobalAirplaneMode);
        executeADBCmd(broadcastAirplaneModeChange);
        String hostIP = "";
        int count = 0;
        try {

            while (hostIP.equals("") && count < 5) {
                Thread.sleep(5000);
                System.out.println(hostIP = executeADBCmd("shell ip route"));
                hostIP = hostIP.trim();
                count++;

            }
            if (hostIP.equalsIgnoreCase(""))
                System.out.println("There was an error getting the ip address.... Problem inside ADBCommand.java Disable AirplaneMode");

                //Means that it was able to grab a host name... trimming is done in ExecuteCMD since i don't need to read the output for anything else
            else {

                try {
                    InetAddress androidIP = InetAddress.getByName(hostIP);

                    //try to connect for 20 seconds....
                    if (!androidIP.isReachable(20000))
                        System.out.println("There was an error trying to connect back after disabling airplane mode.");

                    else
                        System.out.println("Android was able to communicate again after turning airplane mode off");

                } catch (Exception e) {
                    System.out.println("There was an error getting the android ip to work..... " + e.getMessage());
                }


            }
        } catch (Exception e) {
            System.out.println("There was an error getting the android ip to work as a whole..... " + e.getMessage());
        }

    }

    public void lockPortrait() {
        //turn off accelormeter and then lock rotation value
        String lockPort = "shell settings put system user_rotation 0";
        //executeADBCmd(_lockAccelerometer);
        executeADBCmd(lockPort);

        if (currentScreenOrientation() != 0) {
            System.out.println("Error.... Portrait wasn't able to switch!!! ... trying once more");
            executeADBCmd(lockPort);

            if (currentScreenOrientation() != 0)
                System.out.println("Portrait wasn't able to switch... second attempt.");

        }
    }

    public void lockReversePortrait() {
        String locRevPort = "shell settings put system user_rotation 2";
        //executeADBCmd(_lockAccelerometer);
        executeADBCmd(locRevPort);

        if (currentScreenOrientation() != 2) {
            System.out.println("Error.... Portrait Reverse wasn't able to switch!!! ... trying once more");
            executeADBCmd(locRevPort);

            if (currentScreenOrientation() != 2)
                System.out.println("Portrait Reverse wasn't able to switch... second attempt.");

        }
    }

    public void lockLandscape() {
        String lockLand = "shell settings put system user_rotation 1";
        //executeADBCmd(_lockAccelerometer);
        executeADBCmd(lockLand);

        if (currentScreenOrientation() != 1) {
            System.out.println("Error.... Landscape wasn't able to switch!!! ... trying once more");
            executeADBCmd(lockLand);

            if (currentScreenOrientation() != 1)
                System.out.println("Landscape wasn't able to switch... second attempt.");

        }
    }

    public void lockReverseLandscape() {
        String locRevLand = "shell settings put system user_rotation 3";
        //executeADBCmd(_lockAccelerometer);
        executeADBCmd(locRevLand);

        if (currentScreenOrientation() != 3) {
            System.out.println("Error.... Landscape Reverse wasn't able to switch!!! ... trying once more");
            executeADBCmd(locRevLand);

            if (currentScreenOrientation() != 3)
                System.out.println("Landscape Reverse wasn't able to switch... second attempt.");

        }
    }

    public void lockRotation() {
        executeADBCmd(_lockAccelerometer);
    }

    public void enableAutoRotation() {
        executeADBCmd(_unlockAccelerometer);
    }

    public void screenShot() {
        String screenShot = "shell screencap -p /sdcard/screencap.png";
        executeADBCmd(screenShot);
    }

    public void startScreenRecord() {
        String startScreenRecord = "shell screenrecord /sdcard/screen.mp4";
        executeADBCmd(startScreenRecord);
    }

    ;

    public void stopScreenRecord() {
        String stopScreenRecord = "pull /sdcard/screencap.png";

        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            Thread.sleep(200);
            robot.keyPress(KeyEvent.VK_C);
            Thread.sleep(200);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            Thread.sleep(200);
            robot.keyRelease(KeyEvent.VK_C);

        } catch (Exception e) {
            System.out.println("Error on getting the java robot to work");
        }

        executeADBCmd(stopScreenRecord);
    }

    public int currentScreenOrientation() {

        String grabCurrOr = "shell dumpsys input | grep 'SurfaceOrientation' ";

        String[] currOrientation = executeADBCmd(grabCurrOr).split(":");
        //SurfaceOrientation: 2
        try {
            return Integer.parseInt(currOrientation[1].trim());
        } catch (NumberFormatException e) {
            System.out.println("Error trying to parse the format.... " + e.getMessage());
        }

        return -1;

    }

    public void swipeNotificationAway() {
        String swipeNoteAway = "shell input swipe 0 400 300 400";
        try {
            if (!SliderDown) {
                executeADBCmd(_sliderDown);
                SliderDown = true;
                Thread.sleep(4000);
            }

            executeADBCmd(swipeNoteAway);

            Thread.sleep(4000);
            executeADBCmd(_sliderUp);
            SliderDown = false;
            }catch(Exception e) {

        }

    }

    public boolean checkForMedbridgeNotification(){

        String  medbridgePackageName = "pkg=com.medbridgeed.hep.go",
                sysdumpNotifications = "shell dumpsys notification | egrep NotificationRecord",
                notifications;

        notifications = executeADBCmd(sysdumpNotifications);

        String [] tempArray = notifications.split(" ");
        for(String temp : tempArray) {
            if (temp.contains(medbridgePackageName))
                return true;
            System.out.println(temp);
        }

        return false;

    }

    public String executeADBCmd(String command) {

        //Needed for pinging waiting for device to come back on
        if(!command.contains("ping"))
            command = System.getProperty("user.home")+"/Library/Android/sdk/platform-tools/adb " + command;


        StringBuffer output = new StringBuffer();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine())!= null) {
                if(line.contains("10.")) {
                    String [] arr = line.split("src");
                    output.append(arr[1]);
                    break;
                }else{
                    output.append(line + "\n");
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();

    }
}
