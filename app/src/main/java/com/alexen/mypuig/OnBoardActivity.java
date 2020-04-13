package com.alexen.mypuig;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.codemybrainsout.onboarder.AhoyOnboarderActivity;
import com.codemybrainsout.onboarder.AhoyOnboarderCard;

import java.util.ArrayList;
import java.util.List;


public class OnBoardActivity extends AhoyOnboarderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AhoyOnboarderCard ahoyOnboarderCard1 = new AhoyOnboarderCard("Title", "Description", R.drawable.fav_checkbox);
        ahoyOnboarderCard1.setBackgroundColor(R.color.black_transparent);
        ahoyOnboarderCard1.setTitleColor(R.color.black);
        ahoyOnboarderCard1.setDescriptionColor(R.color.black);
        ahoyOnboarderCard1.setTitleTextSize(dpToPixels(10, this));
        ahoyOnboarderCard1.setDescriptionTextSize(dpToPixels(8, this));
//        setColorBackground(R.color.white);
        setGradientBackground();

        AhoyOnboarderCard ahoyOnboarderCard2 = new AhoyOnboarderCard("Title", "Description", R.drawable.fav_checkbox);
        ahoyOnboarderCard2.setBackgroundColor(R.color.black_transparent);
        ahoyOnboarderCard2.setTitleColor(R.color.black);
        ahoyOnboarderCard2.setDescriptionColor(R.color.black);
        ahoyOnboarderCard2.setTitleTextSize(dpToPixels(10, this));
        ahoyOnboarderCard2.setDescriptionTextSize(dpToPixels(8, this));


        List<AhoyOnboarderCard> pages = new ArrayList<>();
        pages.add(ahoyOnboarderCard1);
        pages.add(ahoyOnboarderCard2);
//        pages.add(ahoyOnboarderCard3);

        setOnboardPages(pages);

        setFinishButtonTitle("Get Started");

//Set the finish button style
        setFinishButtonDrawableStyle(ContextCompat.getDrawable(this, R.drawable.rounded_button));
    }

    @Override
    public void onFinishButtonPressed() {

        startActivity(new Intent(this, MainActivity.class));

    }

}