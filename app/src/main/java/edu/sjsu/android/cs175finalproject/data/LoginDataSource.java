package edu.sjsu.android.cs175finalproject.data;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

import edu.sjsu.android.cs175finalproject.data.model.LoggedInUser;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private final FirebaseAuth mAuth;

    public LoginDataSource() {
        mAuth = FirebaseAuth.getInstance();
    }

    public Task<AuthResult> login(String username, String password) {
        return mAuth.signInWithEmailAndPassword(username, password);
    }

    public void logout() {
        mAuth.signOut();
    }
}
