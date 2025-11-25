package edu.sjsu.android.cs175finalproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String PREFS_PREFIX = "user_";
    private FirebaseAuth mAuth;

    private EditText etName, etBenchPress, etDeadlift, etRDL, etBicepCurl, etLatPulldown, etRows, etShoulderPress,
                    etInclineBench;


    public EditProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditProfileFragment newInstance(String param1, String param2) {
        EditProfileFragment fragment = new EditProfileFragment();
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
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText etName = view.findViewById(R.id.etName);
        EditText etBenchPress = view.findViewById(R.id.etBenchPress);
        EditText etDeadlift = view.findViewById(R.id.etDeadlift);
        EditText etRDL = view.findViewById(R.id.etRDL);
        EditText etBicepCurl = view.findViewById(R.id.etBicepCurls);
        EditText etLatPulldown = view.findViewById(R.id.etLatPulldown);
        EditText etRows = view.findViewById(R.id.etRows);
        EditText etShoulderPress = view.findViewById(R.id.etShoulderPress);
        EditText etInclineBench = view.findViewById(R.id.etInclineBench);
        Button saveButton = view.findViewById(R.id.saveProfileButton);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreferences();
                // TODO: put nav action back to profile here
            }
        });


    }

    public void savePreferences() {
        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        String prefsName = PREFS_PREFIX + uid;
        SharedPreferences prefs = requireContext().getSharedPreferences(prefsName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();


        String name = etName.getText().toString();
        String benchPress = etBenchPress.getText().toString();
        String deadlift = etDeadlift.getText().toString();
        String rdl = etRDL.getText().toString();
        String bicepCurl = etBicepCurl.getText().toString();
        String latPulldown = etLatPulldown.getText().toString();
        String rows = etRows.getText().toString();
        String shoulderPress = etShoulderPress.getText().toString();
        String inclineBench = etInclineBench.getText().toString();

        editor.putString("displayName", name);
        editor.putString("benchPress", benchPress);
        editor.putString("deadlift", deadlift);
        editor.putString("rdl", rdl);
        editor.putString("bicepCurl", bicepCurl);
        editor.putString("latPulldown", latPulldown);
        editor.putString("rows", rows);
        editor.putString("shoulderPress", shoulderPress);
        editor.putString("inclineBench", inclineBench);

        editor.apply();
    }
}