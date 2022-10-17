package tests.local.negativeScenariosTests;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import io.appium.java_client.AppiumBy;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static io.qameta.allure.Allure.step;

@Tag("Negative Scenarios")
public class PurchaseTests extends TestBase {

    @Test
    @Disabled("Tinkof api is not working in dev")
    void buyingClubCardTest(){
        step("Open Club Card menu", () -> {
            SwipeLeftToRight();
            $(AppiumBy.id("club.innopolis.customer:id/clubCard")).click();
            $(AppiumBy.id("club.innopolis.customer:id/button")).shouldHave(Condition.exactText("Купить")).click();
        });
        step("Set amount of money", () -> {
            $(AppiumBy.id("club.innopolis.customer:id/setSum")).click();
            $(AppiumBy.id("club.innopolis.customer:id/setSum")).sendKeys("5000");
            $(AppiumBy.id("club.innopolis.customer:id/checkBox")).click();
            $(AppiumBy.id("club.innopolis.customer:id/bankCard/button")).click();
        });
        step("Set bank card data", () -> {
            $(AppiumBy.id("club.innopolis.customer:id/cardNumber")).sendKeys("4300 0000 0000 0777");
            $(AppiumBy.id("club.innopolis.customer:id/cardDate")).sendKeys("0123");
            $(AppiumBy.id("club.innopolis.customer:id/cardCSV")).sendKeys("111");
            $(AppiumBy.id("club.innopolis.customer:id/userEmail")).sendKeys("test@innopolis.club");
        });
        step("Finish purchase", () -> {
            $(AppiumBy.id("club.innopolis.customer:id/button"))
                    .shouldHave(Condition.exactText("Оплатить")).click();
        });
        step("Ensure correct money purchased", () -> {
            $(AppiumBy.id("club.innopolis.customer:id/clubCard")).click();
            $$(AppiumBy.id("club.innopolis.customer:id/moneyAmount"))
                    .shouldHave(CollectionCondition.exactTexts("5000"));
        });
    }

    @Test
    @Disabled("Tinkof api is not working in dev")
    void replenishmentCardTest(){
        step("Open Club Card menu", () -> {
            SwipeLeftToRight();
            $(AppiumBy.id("club.innopolis.customer:id/clubCard")).click();
            $(AppiumBy.id("club.innopolis.customer:id/button"))
                    .shouldHave(Condition.exactText("Пополнить")).click();
        });
        step("Set amount of points", () -> {
            $(AppiumBy.id("club.innopolis.customer:id/pointsAmount")).sendKeys("10000");
            $(AppiumBy.id("club.innopolis.customer:id/checkBox")).click();
            $(AppiumBy.id("club.innopolis.customer:id/bankCard/button")).click();
        });
        step("Finish purchase", () -> {
            $(AppiumBy.id("club.innopolis.customer:id/cardCSV")).sendKeys("111");
            $(AppiumBy.id("club.innopolis.customer:id/button"))
                    .shouldHave(Condition.exactText("Оплатить")).click();
        });
        step("Ensure correct replenishment", () -> {
            $(AppiumBy.id("club.innopolis.customer:id/clubCard")).click();
            $$(AppiumBy.id("club.innopolis.customer:id/moneyAmount"))
                    .shouldHave(CollectionCondition.exactTexts("15000"));
        });
    }

    @Test
    void withdrawalToNewCardTest(){
        step("Open Club Card menu", () -> {
            SwipeLeftToRight();
            $(AppiumBy.id("club.innopolis.customer:id/clubCard")).click();
            $(AppiumBy.id("club.innopolis.customer:id/button"))
                    .shouldHave(Condition.exactText("Вернуть")).click();
        });
        step("Set the sum to withdraw", () -> {
            $(AppiumBy.id("club.innopolis.customer:id/withdrawAmount")).sendKeys("999");
            $(AppiumBy.id("club.innopolis.customer:id/Next/button"))
                    .shouldHave(Condition.text("Дальше")).click();
        });
        step("Create new invoice to withdraw", () -> {
            $(AppiumBy.id("club.innopolis.customer:id/button"))
                    .shouldHave(Condition.text("Добавить счёт вывода")).click();
            $(AppiumBy.id("club.innopolis.customer:id/bankName")).sendKeys("Tinkof");
            $(AppiumBy.xpath("*//banksList/item[1]")).click();
            $(AppiumBy.id("club.innopolis.customer:id/settlementAccount"))
                    .sendKeys("40817810000006025035");
            $(AppiumBy.id("club.innopolis.customer:id/button"))
                    .shouldHave(Condition.text("Подтвердить возврат")).click();
        });
        step("Sign OTP and finish withdrawal", () -> {
            $(AppiumBy.id("club.innopolis.customer:id/otp")).sendKeys("1234");
            wait(10);
            $(AppiumBy.id("club.innopolis.customer:id/button"))
                    .shouldHave(Condition.text("Вернуться на главный экран")).click();
        });
        step("Ensure correct amount of money withdrawn", () -> {
            $(AppiumBy.id("club.innopolis.customer:id/operationsList"))
                    .shouldHave(Condition.exactText("Возврат")).shouldHave(Condition.text("999")).click();
        });
    }

    @Test
    void withdrawalToExistingCardTest(){
        step("Open Club Card menu", () -> {
            SwipeLeftToRight();
            $(AppiumBy.id("club.innopolis.customer:id/clubCard")).click();
            $(AppiumBy.id("club.innopolis.customer:id/button"))
                    .shouldHave(Condition.exactText("Вернуть")).click();
        });
        step("Set the sum to withdraw", () -> {
            $(AppiumBy.id("club.innopolis.customer:id/withdrawAmount")).sendKeys("888");
            $(AppiumBy.id("club.innopolis.customer:id/Next/button"))
                    .shouldHave(Condition.text("Дальше")).click();
        });
        step("Choose card for withdrawal", () -> {
            $(AppiumBy.id("club.innopolis.customer:id/cardsList"))
                    .shouldHave(Condition.text("Тинькофф")).click();
            $(AppiumBy.id("club.innopolis.customer:id/button"))
                    .shouldHave(Condition.text("Подтвердить возврат")).click();
        });
        step("Sign OTP and finish withdrawal", () -> {
            $(AppiumBy.id("club.innopolis.customer:id/otp")).sendKeys("1234");
            wait(10);
            $(AppiumBy.id("club.innopolis.customer:id/button"))
                    .shouldHave(Condition.text("Вернуться на главный экран")).click();
        });
        step("Ensure correct amount of money withdrawn", () -> {
            $(AppiumBy.id("club.innopolis.customer:id/operationsList"))
                    .shouldHave(Condition.exactText("Возврат")).shouldHave(Condition.text("888")).click();
        });
    }

    public void SwipeLeftToRight() throws InterruptedException {
        WebDriver driver = null;
        assert false;
        Dimension size = driver.manage().window().getSize();
        int star_x = (int) (size.width * 0.90);
        int end_x = (int) (size.width * 0.09);
        int star_y = size.height / 2;
        System.out.println("star_x = " + star_x + " ,end_x = " + end_x + " , star_y = " + star_y);
    }
}
