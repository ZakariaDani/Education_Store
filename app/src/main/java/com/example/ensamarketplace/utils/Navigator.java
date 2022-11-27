package com.example.ensamarketplace.utils;

import android.content.Context;
import android.content.Intent;

import com.example.ensamarketplace.AddNewAnnouncementActivity;
import com.example.ensamarketplace.EditProfileActivity;
import com.example.ensamarketplace.ListAnnouncements;
import com.example.ensamarketplace.LoginActivity;
import com.example.ensamarketplace.RegisterActivity;

public class Navigator {

    public static void navigateTo(Context context ,Class destination){
        Intent intent = new Intent(context, destination);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void navigateToHome(Context context){
        navigateTo(context,ListAnnouncements.class);
    }

    public static void navigateToLogin(Context context){
        navigateTo(context,LoginActivity.class);
    }

    public static void navigateToRegistry(Context context){
        navigateTo(context,RegisterActivity.class);
    }

    public static void navigateToEditProfile(Context context){
        navigateTo(context,EditProfileActivity.class);
    }

    public static void navigateToAddAnnoucement(Context context){
        navigateTo(context,AddNewAnnouncementActivity.class);
    }


}
