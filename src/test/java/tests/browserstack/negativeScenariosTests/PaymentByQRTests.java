package tests.browserstack.negativeScenariosTests;

import io.appium.java_client.AppiumBy;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import static com.codeborne.selenide.Selenide.$;
import static io.qameta.allure.Allure.step;

@Tag("Negative Scenarios")
public class PaymentByQRTests extends TestBase {

    @Test
    void scanQRTest(){

        step("Open QR scanner", () ->{
            $(AppiumBy.id("club.innopolis.customer:id/button/scannerQR")).click();
        });

        URL url;
        try{
            String link = "https://dev.innopolis.club/purchase?posId=aa59e4fb-8ed4-438b-acce-eb37cd5b142d&orderId=c0a13c0c-047e-4f72-a01f-5573ead31f71&amount=100";
            url = new URL(link);
            URLConnection conn = url.openConnection();
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                System.out.println(inputLine);
            }
            br.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

