package com.alexen.mypuig;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                // Show an explanation to the userFavs *asynchronously* -- don't block
                // this thread waiting for the userFavs's response! After the userFavs
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        0);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
                // Show an explanation to the userFavs *asynchronously* -- don't block
                // this thread waiting for the userFavs's response! After the userFavs
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        0);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        //1
        AhoyOnboarderCard ahoyOnboarderCard1 = new AhoyOnboarderCard("SECURE", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae arcu rutrum, imperdiet tellus consequat, bibendum tellus.", R.drawable.secure);
        ahoyOnboarderCard1.setBackgroundColor(R.color.white);
        ahoyOnboarderCard1.setTitleColor(R.color.black);
        ahoyOnboarderCard1.setDescriptionColor(R.color.black);
        ahoyOnboarderCard1.setTitleTextSize(dpToPixels(8, this));
        ahoyOnboarderCard1.setDescriptionTextSize(dpToPixels(4, this));
        setColorBackground(R.color.white);

        //2
        AhoyOnboarderCard ahoyOnboarderCard2 = new AhoyOnboarderCard("EASY ACCESSIBLE", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae arcu rutrum, imperdiet tellus consequat, bibendum tellus.", R.drawable.easy);
        ahoyOnboarderCard2.setBackgroundColor(R.color.white);
        ahoyOnboarderCard2.setTitleColor(R.color.black);
        ahoyOnboarderCard2.setDescriptionColor(R.color.black);
        ahoyOnboarderCard2.setTitleTextSize(dpToPixels(5, this));
        ahoyOnboarderCard2.setDescriptionTextSize(dpToPixels(4, this));

        //3

        AhoyOnboarderCard ahoyOnboarderCard3 = new AhoyOnboarderCard("RELIABLE", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae arcu rutrum, imperdiet tellus consequat, bibendum tellus.", R.drawable.realiable);
        ahoyOnboarderCard3.setBackgroundColor(R.color.white);
        ahoyOnboarderCard3.setTitleColor(R.color.black);
        ahoyOnboarderCard3.setDescriptionColor(R.color.black);
        ahoyOnboarderCard3.setTitleTextSize(dpToPixels(8, this));
        ahoyOnboarderCard3.setDescriptionTextSize(dpToPixels(4, this));

        //4

        AhoyOnboarderCard ahoyOnboarderCard4 = new AhoyOnboarderCard("LOREM IPSUM", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae arcu rutrum, imperdiet tellus consequat, bibendum tellus.", R.drawable.fin);
        ahoyOnboarderCard4.setBackgroundColor(R.color.white);
        ahoyOnboarderCard4.setTitleColor(R.color.black);
        ahoyOnboarderCard4.setDescriptionColor(R.color.black);
        ahoyOnboarderCard4.setTitleTextSize(dpToPixels(6, this));
        ahoyOnboarderCard4.setDescriptionTextSize(dpToPixels(4, this));

        List<AhoyOnboarderCard> pages = new ArrayList<>();
        pages.add(ahoyOnboarderCard1);
        pages.add(ahoyOnboarderCard2);
        pages.add(ahoyOnboarderCard3);
        pages.add(ahoyOnboarderCard4);

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