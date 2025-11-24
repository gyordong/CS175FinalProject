package edu.sjsu.android.cs175finalproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.sjsu.android.cs175finalproject.databinding.FragmentCreateAccountBinding;

public class FragmentCreateAccount extends Fragment {

    private EditText etUsername, etName, etPassword;
    private FragmentCreateAccountBinding binding;

    public FragmentCreateAccount() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_account, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etName = view.findViewById(R.id.name);
        etUsername    = view.findViewById(R.id.username);
        etPassword = view.findViewById(R.id.password);
        Button btnCreateAccount = view.findViewById(R.id.createAccountBtn);
        Button backButton = view.findViewById(R.id.backButton);

        btnCreateAccount.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String username    = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // Validate
            if (TextUtils.isEmpty(name)) {
                etName.setError("Required");
                return;
            }
            if (TextUtils.isEmpty(username)) {
                etUsername.setError("Required");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                etPassword.setError("Required");
                return;
            }

            // Simulate account creation
            Toast.makeText(requireContext(),
                    "Account created for " + username,
                    Toast.LENGTH_SHORT).show();

            try {
                Navigation.findNavController(requireView())
                        .navigate(R.id.action_global_nav_main,
                                null,
                                new NavOptions.Builder()
                                        .setPopUpTo(R.id.nav_auth, true)
                                        .build());
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Navigation error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        backButton.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_createAccount_back_to_launch));
    }
}