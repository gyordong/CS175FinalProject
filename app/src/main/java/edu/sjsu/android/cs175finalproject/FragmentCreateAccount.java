package edu.sjsu.android.cs175finalproject;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class FragmentCreateAccount extends Fragment {
    private FirebaseAuth mAuth;
    private EditText etUsername, etName, etPassword;

    public FragmentCreateAccount() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        return inflater.inflate(R.layout.fragment_create_account, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etName = view.findViewById(R.id.name);
        etUsername = view.findViewById(R.id.username); // this is an email field
        etPassword = view.findViewById(R.id.password);
        Button btnCreateAccount = view.findViewById(R.id.createAccountBtn);
        Button backButton = view.findViewById(R.id.backButton);

        btnCreateAccount.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String email = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // Validate
            if (TextUtils.isEmpty(name)) {
                etName.setError("Required");
                return;
            }
            if (TextUtils.isEmpty(email)) {
                etUsername.setError("Required");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                etPassword.setError("Required");
                return;
            }

            createAccount(email, password);
        });
        backButton.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_createAccount_back_to_launch));
    }

    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        Toast.makeText(requireContext(), "Account created.", Toast.LENGTH_SHORT).show();
                        // Navigate to the main part of the app
                        try {
                            Navigation.findNavController(requireView())
                                    .navigate(R.id.action_createAccount_to_main);
                        } catch (Exception e) {
                            Log.e(TAG, "Navigation failed.", e);
                            Toast.makeText(getContext(), "Navigation error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(requireContext(), "Authentication failed: " + task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }
}
