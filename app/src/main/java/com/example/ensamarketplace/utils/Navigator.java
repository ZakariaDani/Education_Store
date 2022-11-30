package com.example.ensamarketplace.utils;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ensamarketplace.AddNewAnnouncementActivity;
import com.example.ensamarketplace.EditProfileActivity;
import com.example.ensamarketplace.ListAnnouncements;
import com.example.ensamarketplace.LoginActivity;
import com.example.ensamarketplace.R;
import com.example.ensamarketplace.RegisterActivity;
import com.example.ensamarketplace.ShowAnnouncementDetailsActivity;

public class Navigator {

    public static void navigateTo(AppCompatActivity context ,Class destination){
        Intent intent = new Intent(context, destination);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void navigateToHome(AppCompatActivity context){
        navigateTo(context,ListAnnouncements.class);
    }

    public static void navigateToLogin(AppCompatActivity context){
        navigateTo(context,LoginActivity.class);
    }

    public static void navigateToRegistry(AppCompatActivity context){
        navigateTo(context,RegisterActivity.class);
    }

    public static void navigateToEditProfile(AppCompatActivity context){
        navigateTo(context,EditProfileActivity.class);
    }

    public static void navigateToAddAnnoucement(AppCompatActivity context) {
        navigateTo(context, AddNewAnnouncementActivity.class);
    }

    public static void navigateToAnnoucementDetails(AppCompatActivity context) {
        navigateTo(context, ShowAnnouncementDetailsActivity.class);
    }
}
