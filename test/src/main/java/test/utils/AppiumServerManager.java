package test.utils;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import java.io.File;

public class AppiumServerManager {
	private static AppiumDriverLocalService service;
	public static void startServer(String ipAddress, int port) {

		try {
			Log4jLogger.loggerInfo("Starting Appium server on IP: " + ipAddress + " and Port: " + port);

			// Build the Appium server
			AppiumServiceBuilder builder = new AppiumServiceBuilder()
					.withIPAddress(ipAddress)
					.usingPort(port)
					.withLogFile(new File("appium_logs.log")); // Logs Appium output to a file

			// Start the service
			service = AppiumDriverLocalService.buildService(builder);
			service.start();

			if (service.isRunning()) {
				Log4jLogger.loggerInfo("Appium server started successfully.");
			} else {
				Log4jLogger.loggerError("Failed to start Appium server.");
			}
		} catch (Exception e) {
			Log4jLogger.loggerError("Error starting Appium server: " + e.getMessage());
		}
	}

	public static void stopServer() {
		if (service != null && service.isRunning()) {
			Log4jLogger.loggerInfo("Stopping Appium server...");
			service.stop();
			Log4jLogger.loggerInfo("Appium server stopped.");
		}
	}

	public static String getServerUrl() {
		if (service != null && service.isRunning()) {
			return service.getUrl().toString();
		}
		return null;
	}
}
