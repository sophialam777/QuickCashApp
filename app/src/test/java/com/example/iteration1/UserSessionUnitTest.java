package com.example.iteration1;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class UserSessionUnitTest {
    @Test
    public void logoutTest(){
        UserSession.login("testName","testEmail@dal.ca","0123","employee","DAL123", "token");
        UserSession.logout();
        assertFalse(UserSession.loggedIn);
    }

    @Test
    public void usedDataTest(){
        UserSession.login("testName","testEmail@dal.ca","0123","employee","DAL123", "token");
        UserSession.logout();
        assertEquals("",UserSession.name);
        assertEquals("",UserSession.email);
        assertEquals("",UserSession.contact);
        assertEquals("",UserSession.role);
        assertEquals("",UserSession.userID);
    }



}
