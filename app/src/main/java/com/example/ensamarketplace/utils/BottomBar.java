package com.example.ensamarketplace.utils;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ensamarketplace.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomBar {
    
    public static void setupEvents(BottomNavigationView nav, AppCompatActivity context){

        nav.setOnItemSelectedListener(
                item -> {
                    switch (item.getItemId()){

                        case R.id.home:Navigator.navigateToHome(context);break;
                        case R.id.ajouter_annonce:Navigator.navigateToAddAnnoucement(context);break;
                        case R.id.profile:Navigator.navigateToEditProfile(context);break;
                        default:

                    }
                    return false;
                }
        );

    }
}
