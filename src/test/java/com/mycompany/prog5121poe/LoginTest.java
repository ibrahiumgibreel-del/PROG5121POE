package com.mycompany.prog5121poe;

import org.junit.Test;
import static org.junit.Assert.*;

public class LoginTest {

    @Test
    public void testUsernameCorrectlyFormatted() {
        Login user = new Login(
                "Kyle",
                "Smith",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertTrue(user.checkUserName());
    }

    @Test
    public void testUsernameIncorrectlyFormatted() {
        Login user = new Login(
                "Kyle",
                "Smith",
                "kyle!!!!!!!",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertFalse(user.checkUserName());
    }

    @Test
    public void testPasswordMeetsComplexityRequirements() {
        Login user = new Login(
                "Kyle",
                "Smith",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertTrue(user.checkPasswordComplexity());
    }

    @Test
    public void testPasswordDoesNotMeetComplexityRequirements() {
        Login user = new Login(
                "Kyle",
                "Smith",
                "kyl_1",
                "password",
                "+27838968976"
        );

        assertFalse(user.checkPasswordComplexity());
    }

    @Test
    public void testCellPhoneCorrectlyFormatted() {
        Login user = new Login(
                "Kyle",
                "Smith",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertTrue(user.checkCellPhoneNumber());
    }

    @Test
    public void testCellPhoneIncorrectlyFormatted() {
        Login user = new Login(
                "Kyle",
                "Smith",
                "kyl_1",
                "Ch&&sec@ke99!",
                "08966553"
        );

        assertFalse(user.checkCellPhoneNumber());
    }

    @Test
    public void testLoginSuccessful() {
        Login user = new Login(
                "Kyle",
                "Smith",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertTrue(user.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginFailed() {
        Login user = new Login(
                "Kyle",
                "Smith",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertFalse(user.loginUser("kyle!!!!!!!", "password"));
    }

    @Test
    public void testSuccessfulLoginMessage() {
        Login user = new Login(
                "Kyle",
                "Smith",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertEquals(
                "Welcome Kyle, Smith it is great to see you again.",
                user.returnLoginStatus(true)
        );
    }

    @Test
    public void testFailedLoginMessage() {
        Login user = new Login(
                "Kyle",
                "Smith",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertEquals(
                "Username or password incorrect, please try again.",
                user.returnLoginStatus(false)
        );
    }
}