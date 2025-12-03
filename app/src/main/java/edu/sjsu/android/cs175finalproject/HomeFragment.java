package edu.sjsu.android.cs175finalproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class HomeFragment extends Fragment {


    private TextView reactionDisplay;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    ImageButton likeBtn;
    ImageButton reactButton;
    boolean liked = false;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reactionDisplay = view.findViewById(R.id.reaction_display);

        reactButton = view.findViewById(R.id.react_button);
        reactButton.setOnLongClickListener(v -> {
            showReactionPopup(v);
            return true;
        });

        likeBtn = view.findViewById(R.id.likeBtn);

        likeBtn.setOnClickListener(v -> {
            liked = !liked;
            likeBtn.animate()
                    .scaleX(1.3f)
                    .scaleY(1.3f)
                    .setDuration(120)
                    .withEndAction(() -> {
                        likeBtn.animate().scaleX(1f).scaleY(1f).setDuration(120).start();
                        if (liked){
                            likeBtn.setImageResource(R.drawable.heart_filled);
                        }else{
                            likeBtn.setImageResource(R.drawable.heart_outline);
                        }
                    })
                    .start();
        });
    }

    private void showReactionPopup(View anchorView){
        View popupView = getLayoutInflater().inflate(R.layout.reaction_popup, null);

        PopupWindow popupWindow = new PopupWindow(
                popupView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                true
        );

        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);

        popupWindow.showAsDropDown(anchorView, -140, -260);

        Button laugh = popupView.findViewById(R.id.laugh_react);
        Button skull = popupView.findViewById(R.id.skull_react);
        Button shock = popupView.findViewById(R.id.shock_react);
        Button fire = popupView.findViewById(R.id.fire_react);

        View.OnClickListener reactionClick = v -> {
            String emoji = ((Button)v).getText().toString();
            reactionDisplay.setText(emoji);
            reactionDisplay.setVisibility(View.VISIBLE);
            popupWindow.dismiss();
        };

        laugh.setOnClickListener(reactionClick);
        skull.setOnClickListener(reactionClick);
        shock.setOnClickListener(reactionClick);
        fire.setOnClickListener(reactionClick);
    }
}