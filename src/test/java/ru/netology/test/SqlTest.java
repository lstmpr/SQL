package ru.netology.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DbInteraction;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class SqlTest {

    @Test
    void shouldBeValid() {
        open("http://localhost:9999/");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DbInteraction.getVerificationCode(authInfo.getLogin());
        var dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldBeBlocked() {
        open("http://localhost:9999/");
        var loginPage = new LoginPage();
        var otherAuthInfo = DataHelper.getOtherAuthInfo();
        loginPage.inValidLogin(otherAuthInfo);
        loginPage.cleanLoginFields();
        loginPage.inValidLogin(otherAuthInfo);
        loginPage.cleanLoginFields();
        loginPage.inValidLogin(otherAuthInfo);
        var statusQuery = DbInteraction.getStatus(otherAuthInfo.getLogin());
        assertEquals("blocked", statusQuery);
    }

    @AfterAll
    static void cleanDB() {
        DbInteraction.cleanDb();
    }

}
