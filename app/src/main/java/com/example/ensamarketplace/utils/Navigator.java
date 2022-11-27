package com.example.ensamarketplace.utils;

import android.content.Context;
import android.content.Intent;

import com.example.ensamarketplace.AddNewAnnouncementActivity;
import com.example.ensamarketplace.EditProfileActivity;
import com.example.ensamarketplace.ListAnnouncements;
import com.example.ensamarketplace.LoginActivity;
import com.example.ensamarketplace.RegisterActivity;

public class Navigator {

    public static void navigateToHome(Context context){
        Intent intent = new Intent(context, ListAnnouncements.class);
        context.startActivity(intent);
    }

    public static void navigateToLogin(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void navigateToRegistry(Context context){
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    public static void navigateToEditProfile(Context context){
        Intent intent = new Intent(context, EditProfileActivity.class);
        context.startActivity(intent);
    }

    public static void navigateToAddAnnoucement(Context context){
        Intent intent = new Intent(context, AddNewAnnouncementActivity.class);
        context.startActivity(intent);
    }
}
