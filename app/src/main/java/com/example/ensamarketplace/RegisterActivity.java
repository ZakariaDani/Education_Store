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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ensamarketplace.model.User;
import com.example.ensamarketplace.utils.Navigator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.FirebaseFirestore;


public class RegisterActivity extends AppCompatActivity {

    EditText email;
    EditText name;
    EditText password;
    EditText phone;

    RadioGroup branch;
    String emailInput,nameInput,passwordInput,branchInput,phoneInput;

    ProgressBar loadingIcon;
    TextView loginButton;

    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.emaill);
        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        branch = findViewById(R.id.branch);
        phone = findViewById(R.id.phone);

        loginButton  = findViewById(R.id.navigationButton);
        loadingIcon = findViewById(R.id.loadingIcon);

        loginButton.setOnClickListener(event->{
            Navigator.navigateToLogin(this);
        });
    }

    public void register(View view) {

        emailInput = email.getText().toString();
        nameInput = name.getText().toString();
        passwordInput = password.getText().toString();
        RadioButton radio = findViewById(branch.getCheckedRadioButtonId());
        branchInput = radio.getText().toString();
        phoneInput = phone.getText().toString();

        boolean validateForm = validateForm();

        if(validateForm){
            enableLoadingAnimation();
            User user = new User(nameInput, emailInput, branchInput, phoneInput);

            firebaseAuth.createUserWithEmailAndPassword(emailInput, passwordInput).addOnSuccessListener(
                    new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            saveUser(user,authResult.getUser().getUid());
                        }
                    }
            ).addOnFailureListener(
                    new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            disableLoadingAnimation();
                            showMessage(e.getMessage());
                        }
                    }
            );
        }
    }

    public void saveUser(User user,String userID){
        firestore.collection("Users").document(userID)
                .set(user).addOnSuccessListener(
                        result->{showMessage("Votre compte a été creé avec succée");
                            Navigator.navigateToHome(this);}
                )
               .addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showMessage("notre serveur est en service pour le moment");
                    }
                }
                ).addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                disableLoadingAnimation();
                            }
                        }
                );
    }

    public void showMessage(String errorMessage){
        Toast.makeText(this,errorMessage,Toast.LENGTH_SHORT).show();
    }

    public boolean validateForm(){
        boolean validForm = false;

        if (emailInput.isEmpty() || nameInput.isEmpty() ||
                passwordInput.isEmpty() || branchInput.isEmpty() || phoneInput.isEmpty() ) {
            showMessage("Tous les champs doivent etre remplis");
        }else if (!emailInput.matches("^[a-z].*@[a-z]+\\.[a-z]+$")) {
            showMessage("Cette addresse email est invalide");
        }else if (!phoneInput.matches("^0[5-8][0-9]+$")) {
            showMessage("Ce numéro de téléphone est invalide");
        }else if (passwordInput.length() < 6) {
            showMessage("Le mot de passe doit contenir au minimum six caractères");
        }
        else{
            validForm = true;
        }
        return validForm;
    }

    public void enableLoadingAnimation(){
        loadingIcon.setVisibility(View.VISIBLE);
    }

    public void disableLoadingAnimation(){
        loadingIcon.setVisibility(View.INVISIBLE);
    }
}