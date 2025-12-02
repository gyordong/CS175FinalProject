package edu.sjsu.android.cs175finalproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.api.Distribution;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button reactButton;
    PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);

        // Get the NavHostFragment
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_container);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();

        // Dynamically choose which graph to start
        if (userIsLoggedIn()) {
            navController.setGraph(R.navigation.nav);       // Main app flow
        } else {
            navController.setGraph(R.navigation.nav_auth);  // Auth flow
        }

        NavigationUI.setupWithNavController(bottomNav, navController);

        // Hide/show bottom nav based on destination
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            int id = destination.getId();

            // List of fragments where bottom nav should be hidden
            if (id == R.id.fragmentLaunch || id == R.id.loginFragment || id == R.id.fragmentCreateAccount){
                bottomNav.setVisibility(View.GONE);
            } else {
                bottomNav.setVisibility(View.VISIBLE);
            }
        });

        // Edge-to-edge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        reactButton = findViewById(R.id.reactBtn);
        reactButton.setOnLongClickListener(v -> {
            showReactionPopup(v);
            return true;
        });
    }

    /**
     * Replace with your actual user login check.
     * For example, check SharedPreferences or FirebaseAuth.
     */
    private boolean userIsLoggedIn() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            return true;
        }
        return false; // if not logged in
    }

    private void showReactionPopup(View anchorView){
        View popupView = getLayoutInflater().inflate(R.layout.reaction_popup, null);

        popupWindow = new PopupWindow(
                popupView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                true);

        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);

        popupWindow.showAsDropDown(anchorView, -100, -150);

        TextView laugh = popupView.findViewById(R.id.laugh_react);
        TextView skull = popupView.findViewById(R.id.skull_react);
        TextView shock = popupView.findViewById(R.id.shock_react);
        TextView fire = popupView.findViewById(R.id.fire_react);

        laugh.setOnClickListener(v -> {
            reactButton.setText("😂");
            popupWindow.dismiss();
        });
        skull.setOnClickListener(v -> {
            reactButton.setText("💀");
            popupWindow.dismiss();
        });
        shock.setOnClickListener(v -> {
            reactButton.setText("😧");
            popupWindow.dismiss();
        });
        fire.setOnClickListener(v -> {
            reactButton.setText("🔥");
            popupWindow.dismiss();
        });
    }
}