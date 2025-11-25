package edu.sjsu.android.cs175finalproject;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static final String PREFS_PREFIX = "user_";
    private FirebaseAuth mAuth;
    private Button checkInButton;
    private ImageView editProfileButton;
    private TextView displayName, benchPress, deadlift,
            rdl, bicepCurl, latPulldown, rows, shoulderPress, inclineBench,
            streakCount;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editProfileButton = view.findViewById(R.id.edit_profile_Btn);

        streakCount = view.findViewById(R.id.tvStreakCount);
        checkInButton = view.findViewById(R.id.checkIn_Btn);

        displayName = view.findViewById(R.id.profileNameText);
        benchPress = view.findViewById(R.id.benchPressWeight);
        deadlift = view.findViewById(R.id.deadliftWeight);
        rdl = view.findViewById(R.id.rdlWeight);
        bicepCurl = view.findViewById(R.id.bicepCurlWeight);
        latPulldown = view.findViewById(R.id.latPulldownWeight);
        rows = view.findViewById(R.id.rowWeight);
        shoulderPress = view.findViewById(R.id.shoulderPressWeight);
        inclineBench = view.findViewById(R.id.inclineBenchWeight);

        loadPreferences();

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_profileFragment_to_editProfileFragment);
            }
        });

        checkInButton.setOnClickListener(new View.OnClickListener() {
            // hard coded streak
            @Override
            public void onClick(View v) {
                streakCount.setText("1 day");
            }
        });

    }

    // can add placeholders if deemed better
    private void loadPreferences() {
        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        String prefsName = PREFS_PREFIX + uid;
        SharedPreferences prefs = requireContext().getSharedPreferences(prefsName, Context.MODE_PRIVATE);

        String displayNameData = prefs.getString("displayName", mAuth.getCurrentUser().getDisplayName());
        String benchPressData = prefs.getString("benchPress", "");
        String deadliftData = prefs.getString("deadlift", "");
        String rdlData = prefs.getString("rdl", "");
        String bicepCurlData = prefs.getString("bicepCurl", "");
        String latPulldownData = prefs.getString("latPulldown", "");
        String rowsData = prefs.getString("rows", "");
        String shoulderPressData = prefs.getString("shoulderPress", "");
        String inclineBenchData = prefs.getString("inclineBench", "");

        System.out.println(displayNameData);

        displayName.setText(displayNameData);
        benchPress.setText(benchPressData);
        deadlift.setText(deadliftData);
        rdl.setText(rdlData);
        bicepCurl.setText(bicepCurlData);
        latPulldown.setText(latPulldownData);
        rows.setText(rowsData);
        shoulderPress.setText(shoulderPressData);
        inclineBench.setText(inclineBenchData);
    }

}