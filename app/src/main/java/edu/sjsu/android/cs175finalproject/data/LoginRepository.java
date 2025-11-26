package edu.sjsu.android.cs175finalproject.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import edu.sjsu.android.cs175finalproject.data.model.LoggedInUser;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public LiveData<Result<LoggedInUser>> login(String username, String password) {
        MutableLiveData<Result<LoggedInUser>> resultLiveData = new MutableLiveData<>();
        dataSource.login(username, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                AuthResult authResult = task.getResult();
                FirebaseUser firebaseUser = authResult.getUser();
                LoggedInUser loggedInUser = new LoggedInUser(firebaseUser.getUid(), firebaseUser.getDisplayName());
                setLoggedInUser(loggedInUser);
                resultLiveData.setValue(new Result.Success<>(loggedInUser));
            } else {
                resultLiveData.setValue(new Result.Error(task.getException()));
            }
        });
        return resultLiveData;
    }
}
