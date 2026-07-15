package test.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ADBCommandExecutor {

    public static String executeADBCommand(String command) {
        String output = "";
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder outputBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                outputBuilder.append(line).append("\n");
            }
            process.waitFor();
            output = outputBuilder.toString();
        } catch (Exception e) {
        	Log4jLogger.loggerError(e.getMessage());
        }
        return output.trim();
    }
    
    public static void forceStopApp(String packageName) {
        String command = "adb shell am force-stop " + packageName;
        String output = ADBCommandExecutor.executeADBCommand(command);
        Log4jLogger.loggerInfo("ADB Output: " + output);
    }
    
    public static void launchApp(String packageName, String activityName) {
    	try {
    		 String command = "adb shell am start -n " + packageName + "/" + activityName;
    	        Runtime.getRuntime().exec(command);
    	        Log4jLogger.loggerInfo("Launching : "+ packageName);
		} catch (Exception e) {
			 Log4jLogger.loggerError(e.getMessage());
		}
       
    }
    
}
