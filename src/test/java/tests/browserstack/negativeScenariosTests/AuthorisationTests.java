package tests.browserstack.negativeScenariosTests;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import io.appium.java_client.AppiumBy;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static io.qameta.allure.Allure.step;

@Tag("Negative Scenarios")
public class AuthorisationTests extends TestBase {

    @Test
    void loginTest(){
        step("Enter login data", () -> {
            $(AppiumBy.id("club.innopolis.customer:id/phoneNumber")).click();
            $(AppiumBy.id("club.innopolis.customer:id/phoneNumber")).sendKeys("9063264020");
            $(AppiumBy.id("club.innopolis.customer:id/button"))
                    .shouldHave(Condition.text("Отправить смс")).click();
            $(AppiumBy.id("club.innopolis.customer:id/otp")).sendKeys("1234");
            $(AppiumBy.id("club.innopolis.customer:id/inputField")).sendKeys("QQQqqq123");
            $(AppiumBy.id("club.innopolis.customer:id/button")).shouldHave(Condition.text("Войти")).click();
        });

        step("Ensure Authorisation", () -> {
            $$(AppiumBy.id("club.innopolis.customer:id/page_list_item_title"))
                    .shouldHave(CollectionCondition.sizeGreaterThan(0));
        });
    }

    @Test
    public void registrationTest(){
        step("Pass new client's phone number", () -> {
            $(AppiumBy.id("club.innopolis.customer:id/phoneNumber")).click();
            $(AppiumBy.id("club.innopolis.customer:id/phoneNumber")).sendKeys(randomPhoneNumberGenerator());
            $(AppiumBy.id("club.innopolis.customer:id/button"))
                    .shouldHave(Condition.text("Отправить смс")).click();
        });
        step("Pass new client's OTP", () -> $(AppiumBy.id("club.innopolis.customer:id/otp"))
                .sendKeys("1234"));
        step("Create new client's password", () -> {
            String newPassword = randomPasswordGenerator();
            $(AppiumBy.id("club.innopolis.customer:id/password")).sendKeys(newPassword);
            $(AppiumBy.id("club.innopolis.customer:id/repeat_password")).sendKeys(newPassword);
            $(AppiumBy.id("club.innopolis.customer:id/button"))
                    .shouldHave(Condition.text("Зарегистрироваться")).click();
        });
    }

    @Test
    public void changePasswordTest(){

        step("Enter client's Phone number", () ->{
            $(AppiumBy.id("club.innopolis.customer:id/phoneNumber")).click();
            $(AppiumBy.id("club.innopolis.customer:id/phoneNumber")).sendKeys("0000000071");
            $(AppiumBy.id("club.innopolis.customer:id/button"))
                    .shouldHave(Condition.text("Отправить смс")).click();
            $(AppiumBy.id("club.innopolis.customer:id/otp")).sendKeys("1234");
        });
        step("Change password", ()-> {
            $(AppiumBy.linkText("Не помню пароль")).click();
            String password = randomPasswordGenerator();
            $(AppiumBy.id("club.innopolis.customer:id/password")).sendKeys(password);
            $(AppiumBy.id("club.innopolis.customer:id/repeat_password")).sendKeys(password);
            $(AppiumBy.id("club.innopolis.customer:id/button"))
                    .shouldHave(Condition.text("")).click();
        });
        step("Pass OTP step required for password updating", () ->{
            $(AppiumBy.name("Сменить пароль")).click();
            $(AppiumBy.id("club.innopolis.customer:id/otp")).sendKeys("1234");
        });
        step("Ensure that password was changed", () -> {
            $$(AppiumBy.id("club.innopolis.customer:id/page_list_item_title"))
                    .shouldHave(CollectionCondition.sizeGreaterThan(0));
        });
    }

    public static String randomPhoneNumberGenerator(){
        long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
        return Long.toString(number);
    }

    public static String randomPasswordGenerator(){
        String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";

        String alphaNumeric = "Aa1" + upperAlphabet + lowerAlphabet + numbers;
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int length = 6;
        for(int i = 0; i < length; i++) {
            int index = random.nextInt(alphaNumeric.length());
            char randomChar = alphaNumeric.charAt(index);
            sb.append(randomChar);
        }
        return sb.toString();
    }
}
