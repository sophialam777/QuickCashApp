package com.example.iteration1;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import com.example.iteration1.validator.LoginValidator;
import com.google.firebase.database.*;
import java.util.Iterator;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class UserSessionUnitTest {
    @Test
    public void logoutTest(){
        UserSession testSession = new UserSession("testName","testEmail@dal.ca","0123","employee","DAL123");
        testSession.logout();
        assertFalse(testSession.loggedIn);
    }

    @Test
    public void usedDataTest(){
        UserSession testSession = new UserSession("testName","testEmail@dal.ca","0123","employee","DAL123");
        testSession.logout();
        assertEquals("",testSession.name);
        assertEquals("",testSession.email);
        assertEquals("",testSession.contact);
        assertEquals("",testSession.role);
        assertEquals("",testSession.userID);
    }



}
