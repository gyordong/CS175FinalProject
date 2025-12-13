package edu.sjsu.android.cs175finalproject;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfileFragment extends Fragment {


    private static final String PREFS_PREFIX = "user_";
    private FirebaseAuth mAuth;

    private EditText etName, etBenchPress, etDeadlift, etRDL, etBicepCurl, etLatPulldown, etRows, etShoulderPress,
                    etInclineBench;
    private ImageView profileImage;
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private ActivityResultLauncher<String> permissionLauncher;
    private Button logoutButton;

    private ImageView backArrow;

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
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mAuth = FirebaseAuth.getInstance();
        Button saveButton = view.findViewById(R.id.saveProfileButton);
        profileImage = view.findViewById(R.id.editProfileImage);
        backArrow = view.findViewById(R.id.back_arrow);
        etName = view.findViewById(R.id.etName);
        etBenchPress = view.findViewById(R.id.etBenchPress);
        etDeadlift = view.findViewById(R.id.etDeadlift);
        etRDL = view.findViewById(R.id.etRDL);
        etBicepCurl = view.findViewById(R.id.etBicepCurls);
        etLatPulldown = view.findViewById(R.id.etLatPulldown);
        etRows = view.findViewById(R.id.etRows);
        etShoulderPress = view.findViewById(R.id.etShoulderPress);
        etInclineBench = view.findViewById(R.id.etInclineBench);
        logoutButton = view.findViewById(R.id.logout);


        loadProfilePicture();

        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        if (imageUri != null) {
                            try {
                                // Display the image
                                profileImage.setImageURI(imageUri);

                                // Save the image to internal storage
                                saveProfilePicture(imageUri);

                                Toast.makeText(getContext(), "Profile picture updated!", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Toast.makeText(getContext(), "Error loading image", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );

        // Initialize permission launcher
        permissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        imagePickerLauncher.launch(intent);
                    } else {
                        Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra("android.content.extra.SHOW_ADVANCED", true);
                intent.putExtra("android.content.extra.FANCY", true);
                intent.putExtra("android.content.extra.SHOW_FILESIZE", true);
                imagePickerLauncher.launch(intent);
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreferences();
                Navigation.findNavController(v).navigate(R.id.action_editProfileFragment_to_profileFragment);
            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_editProfileFragment_to_profileFragment);
            }
        });
    }

    public void savePreferences() {
        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        String prefsName = PREFS_PREFIX + uid;
        SharedPreferences prefs = requireContext().getSharedPreferences(prefsName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();


        String name = etName.getText().toString().trim();
        String benchPress = etBenchPress.getText().toString().trim();
        String deadlift = etDeadlift.getText().toString().trim();
        String rdl = etRDL.getText().toString().trim();
        String bicepCurl = etBicepCurl.getText().toString().trim();
        String latPulldown = etLatPulldown.getText().toString().trim();
        String rows = etRows.getText().toString().trim();
        String shoulderPress = etShoulderPress.getText().toString().trim();
        String inclineBench = etInclineBench.getText().toString().trim();

        if (!name.isEmpty()) {
            editor.putString("displayName", name);
        }
        if (!benchPress.isEmpty()) {
            editor.putString("benchPress", benchPress);
        }
        if (!deadlift.isEmpty()) {
            editor.putString("deadlift", deadlift);
        }
        if (!rdl.isEmpty()) {
            editor.putString("rdl", rdl);
        }
        if (!bicepCurl.isEmpty()) {
            editor.putString("bicepCurl", bicepCurl);
        }
        if (!latPulldown.isEmpty()) {
            editor.putString("latPulldown", latPulldown);
        }
        if (!rows.isEmpty()) {
            editor.putString("rows", rows);
        }
        if (!shoulderPress.isEmpty()) {
            editor.putString("shoulderPress", shoulderPress);
        }
        if (!inclineBench.isEmpty()) {
            editor.putString("inclineBench", inclineBench);
        }

        editor.apply();
    }

    private void saveProfilePicture(Uri imageUri) {
        try {
            InputStream inputStream = getContext().getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();

            // Resize bitmap to reduce storage space
            Bitmap resizedBitmap = resizeBitmap(bitmap, 500, 500);

            // Save to internal storage
            File file = new File(getContext().getFilesDir(), "profile_picture.jpg");
            FileOutputStream fos = new FileOutputStream(file);
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.close();

            bitmap.recycle();
            resizedBitmap.recycle();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadProfilePicture() {
        File file = new File(getContext().getFilesDir(), "profile_picture.jpg");
        if (file.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            profileImage.setImageBitmap(bitmap);
        }
    }

    private Bitmap resizeBitmap(Bitmap bitmap, int maxWidth, int maxHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float ratio = Math.min((float) maxWidth / width, (float) maxHeight / height);
        int newWidth = Math.round(width * ratio);
        int newHeight = Math.round(height * ratio);

        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
    }
}
