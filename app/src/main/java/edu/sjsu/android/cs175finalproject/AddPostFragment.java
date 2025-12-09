package edu.sjsu.android.cs175finalproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class AddPostFragment extends Fragment {

    private ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_post, container, false);
    }

    private Bitmap handleImageRotation(String imagePath) throws IOException {
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

        ExifInterface exif = new ExifInterface(imagePath);
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.postRotate(90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.postRotate(180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.postRotate(270);
                break;
            default:
                break;
        }

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView = view.findViewById(R.id.selectedImage);
        ImageButton cancelBtn = view.findViewById(R.id.cancelButton);
        ImageButton confirmBtn = view.findViewById(R.id.confirmButton);

        // Get the image path
        String path = getArguments().getString("imagePath");

        try {
            Bitmap rotatedBitmap = handleImageRotation(path);
            imageView.setImageBitmap(rotatedBitmap);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Failed to load image", Toast.LENGTH_SHORT).show();
        }

        // Cancel: go back to CameraFragment
        cancelBtn.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.popBackStack(); // safely return to CameraFragment
        });

        confirmBtn.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Posted!", Toast.LENGTH_SHORT).show();
        });
    }
}
