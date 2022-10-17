package drivers;

import com.codeborne.selenide.WebDriverProvider;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.AutomationName;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;


public class LocalMobileDriver implements WebDriverProvider {

    public static URL getAppiumServerUrl() {
        try {
            return new URL("http://localhost:4723/wd/hub");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public WebDriver createDriver(Capabilities capabilities) {
        File app = getApp();

        UiAutomator2Options options = new UiAutomator2Options();
        options.merge(capabilities);
        options.setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2);
        options.setPlatformName("Android");
//        options.setDeviceName("RFCR90ZMNQP");
        options.setDeviceName("Pixel 5 API 31");
        options.setPlatformVersion("12.0");
//        options.setPlatformVersion("11.0");
        options.setApp(app.getAbsolutePath());
        options.setAppPackage("club.innopolis.customer");
        options.setAppActivity("club.innopolis.customer.scenes.main.MainActivity");

        return new AndroidDriver(getAppiumServerUrl(), options);
    }

    private File getApp() {
        String appPath = "src/test/resources/app/my-app-release.apks";
        return new File(appPath);
    }

}