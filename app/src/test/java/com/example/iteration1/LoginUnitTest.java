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
public class LoginUnitTest {

    private LoginValidator validator;

    @Mock
    FirebaseDatabase mockDatabase;
    @Mock
    DatabaseReference mockDatabaseReference;
    @Mock
    Query mockQuery;
    @Mock
    DataSnapshot mockDataSnapshot;
    @Mock
    ValueEventListener mockListener;

    @Before
    public void setup() {
        validator = new LoginValidator();
        MockitoAnnotations.openMocks(this);

        //Set up the mock behavior for Firebase Database
        when(mockDatabase.getReference("users")).thenReturn(mockDatabaseReference);
        when(mockDatabaseReference.orderByChild("email")).thenReturn(mockQuery);
    }

    @Test
    public void checkIfUserExists(){
        String email = "test@example.com";
        when(mockQuery.equalTo(email)).thenReturn(mockQuery);

        doAnswer(invocation -> {
            ValueEventListener listener = invocation.getArgument(0);
            listener.onDataChange(mockDataSnapshot);
            return null;
        }).when(mockQuery).addListenerForSingleValueEvent(any(ValueEventListener.class));

        when(mockDataSnapshot.exists()).thenReturn(true);
        assertTrue(validator.doesUserExist(email));
    }

    @Test
    public void checkIfUserDoesNotExist() {
        String email = "nonexistent@dal.ca";
        when(mockQuery.equalTo(email)).thenReturn(mockQuery);

        doAnswer(invocation -> {
            ValueEventListener listener = invocation.getArgument(0);
            listener.onDataChange(mockDataSnapshot);
            return null;
        }).when(mockQuery).addListenerForSingleValueEvent(any(ValueEventListener.class));

        when(mockDataSnapshot.exists()).thenReturn(false);
        assertFalse(validator.doesUserExist(email));
    }

    @Test
    public void testRoleValidationForEmployee() {
        userAccount mockUser = createMockUser("test@example.com", "Pass123!@", "Employee");
        mockDatabaseQueryForUser("test@example.com", mockUser);
        assertFalse(validator.isLoginSuccessful("test@example.com", "Pass123!@"));
    }

    @Test
    public void testRoleValidationForEmployer() {
        userAccount mockUser = createMockUser("test@example.com", "Pass123!@", "Employer");
        mockDatabaseQueryForUser("test@example.com", mockUser);
        assertFalse(validator.isLoginSuccessful("test@example.com", "Pass123!@"));
    }
    @Test
    public void checkIfLoginIsSuccessful() {
        String email = "mt769340@dal.ca";
        String password = "passworD123!";

        userAccount mockUser = createMockUser(email, password, "Employee");
        mockDatabaseQueryForUser(email, mockUser);

        boolean isLoginSuccessful = validator.isLoginSuccessful(email, password);
        assertTrue(isLoginSuccessful);
    }

    @Test
    public void checkIfLoginFailsWithIncorrectRole() {
        String email = "mt769340@dal.ca";
        String password = "passworD123!";

        userAccount mockUser = createMockUser(email, password, "Employer");
        mockDatabaseQueryForUser(email, mockUser);

        assertFalse(validator.doesUserExist(email)); //Returns false due to role mismatch
    }



    private userAccount createMockUser(String email, String password, String role) {
        userAccount mockUser = mock(userAccount.class);
        when(mockUser.getEmail()).thenReturn(email);
        when(mockUser.getPassword()).thenReturn(password);
        when(mockUser.getRole()).thenReturn(role);
        return mockUser;
    }


    private void mockDatabaseQueryForUser(String email, userAccount mockUser) {
        when(mockQuery.equalTo(email)).thenReturn(mockQuery);
        when(mockDataSnapshot.exists()).thenReturn(true);

        Iterable<DataSnapshot> mockChildren = mock(Iterable.class);
        when(mockDataSnapshot.getChildren()).thenReturn(mockChildren);
        Iterator<DataSnapshot> mockIterator = mock(Iterator.class);
        when(mockChildren.iterator()).thenReturn(mockIterator);

        when(mockIterator.hasNext()).thenReturn(true);
        DataSnapshot mockUserSnapshot = mock(DataSnapshot.class);
        when(mockIterator.next()).thenReturn(mockUserSnapshot);
        when(mockUserSnapshot.getValue(userAccount.class)).thenReturn(mockUser);
    }

    @Test
    public void checkIfEmailIsValidForLogin() {
        assertTrue(validator.isValidEmail("testuser@dal.ca"));
    }

    @Test
    public void checkIfEmailIsInvalidForLogin() {
        assertFalse(validator.isValidEmail("invalidEmail.com"));
    }

    @Test
    public void checkIfPasswordIsValidForLogin() {
        String password = "Password1@";
        assertTrue(validator.isPasswordValid(password));
    }

    @Test
    public void checkIfPasswordIsInvalidForLogin() {
        assertFalse(validator.isPasswordValid("pass1@"));
    }

    @Test
    public void testPasswordWithOnlySymbols() {
        assertFalse(validator.isPasswordValid("!@#$%^&*"));
    }

    @Test
    public void testPasswordWithOnlyDigits() {
        assertFalse(validator.isPasswordValid("12345678"));
    }

    @Test
    public void testPasswordWithOnlyUppercase() {
        assertFalse(validator.isPasswordValid("PASSWORD"));
    }

    @Test
    public void checkIfLoginFailsWithInvalidCredentials() {
        assertFalse(validator.isLoginSuccessful("test@example.com", "WrongPassword1@"));
    }

}
