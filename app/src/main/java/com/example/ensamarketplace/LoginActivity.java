package com.example.ensamarketplace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ensamarketplace.utils.Navigator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;

    String emailInput,passwordInput;

    ProgressBar loadingIcon;

    private TextView registerButton;
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.emaill);
        password = findViewById(R.id.password);
        loadingIcon = findViewById(R.id.loadingIcon);
        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(event->{Navigator.navigateToRegistry(this);});
    }

    public void login(View view){
        emailInput = email.getText().toString();
        passwordInput = password.getText().toString();
        boolean validForm = validateForm();
        if(validForm){
            enableLoadingAnimation();
            firebaseAuth.signInWithEmailAndPassword(emailInput,passwordInput).addOnSuccessListener(
                    authResult -> {
                        Navigator.navigateToHome(this);
                    }
            ).addOnFailureListener(
                    e -> showMessage("l'addresse email ou le mot de passe est incorrect")
            ).addOnCompleteListener(
                    task -> disableLoadingAnimation()
            );
        }
    }

    public void showMessage(String errorMessage){
        Toast.makeText(this,errorMessage,Toast.LENGTH_SHORT).show();
    }

    public void enableLoadingAnimation(){
        loadingIcon.setVisibility(View.VISIBLE);
    }

    public void disableLoadingAnimation(){
        loadingIcon.setVisibility(View.INVISIBLE);
    }

    private boolean validateForm(){
        boolean validForm = false;
        if (emailInput.isEmpty() || passwordInput.isEmpty())  {
            showMessage("Tous les champs doivent etre remplis");
        } else if (!emailInput.matches("^[a-z].*@[a-z]+\\.[a-z]+$")) {
            showMessage("cette addresse email est invalide");
        } else if (password.length() < 6) {
            showMessage("le mot de passe doit contenir au minimum six caract??res");
        }
        else{
            validForm = true;
        }
        return validForm;
    }



}