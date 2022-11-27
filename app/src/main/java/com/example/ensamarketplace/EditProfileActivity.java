package com.example.ensamarketplace;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ensamarketplace.model.User;
import com.example.ensamarketplace.utils.BottomBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditProfileActivity extends AppCompatActivity {
    EditText email;
    EditText name;
    EditText phone;

    RadioGroup branch;
    String emailInput,nameInput,branchInput,phoneInput;
    ProgressBar loadingIcon;
    BottomNavigationView bottomNavigationView;

    User user;
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_edit_profile);

        email = findViewById(R.id.emaill);
        name = findViewById(R.id.name);
        branch = findViewById(R.id.type);
        phone = findViewById(R.id.phone);

        loadingIcon = findViewById(R.id.loadingIcon);
        bottomNavigationView = findViewById(R.id.bottom_bar);
        BottomBar.setupEvents(bottomNavigationView,getApplicationContext());
        getConnectedUser();
    }

    public void save(View view) {

        emailInput = email.getText().toString();
        nameInput = name.getText().toString();
        RadioButton radio = findViewById(branch.getCheckedRadioButtonId());
        branchInput = radio.getText().toString();
        phoneInput = phone.getText().toString();

        boolean validateForm = validateForm();

        if(validateForm){
            enableLoadingAnimation();
            User user = new User(nameInput, emailInput, branchInput, phoneInput);
            saveUser(user, firebaseAuth.getCurrentUser().getUid());
        }
    }
    public void saveUser(User user,String userID){
        firestore.collection("Users").document(userID)
                .set(user).addOnSuccessListener(
                        unused -> showMessage("Votre profil a été modifié avec succée")
                )
                .addOnFailureListener(
                        e -> showMessage("notre serveur est en service pour le moment")
                ).addOnCompleteListener(
                        task -> disableLoadingAnimation()
                );
    }

    public boolean validateForm(){
        boolean validForm = false;

        if (emailInput.isEmpty() || nameInput.isEmpty() ||
         branchInput.isEmpty() || phoneInput.isEmpty() ) {
            showMessage("Tous les champs doivent etre remplis");
        }else if (!emailInput.matches("^[a-z].*@[a-z]+\\.[a-z]+$")) {
            showMessage("Cette addresse email est invalide");
        }else if (!phoneInput.matches("^0[5-8][0-9]+$")) {
            showMessage("Ce numéro de téléphone est invalide");
        }else{
            validForm = true;
        }
        return validForm;
    }
    public User getConnectedUser() {
        User user = new User();
        DocumentReference docRef = firestore.collection("Users").document(firebaseAuth.getCurrentUser().getUid());
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                user.setName(document.get("name").toString());
                user.setEmail(document.get("email").toString());
                user.setPhone(document.get("phone").toString());
                user.setBranch(document.get("branch").toString());
                phone.setText(user.getPhone());
                email.setText(user.getEmail());
                name.setText(user.getName());
                setBranch(user);
                if (document.exists()) {
                    System.out.println("data: " + user);
                } else {
                    System.out.println("No such document");
                }
            } else {
                System.out.println("get failed with "+ task.getException());
            }
        });
        return user;
    }
    public void enableLoadingAnimation(){
        loadingIcon.setVisibility(View.VISIBLE);
    }

    public void disableLoadingAnimation(){
        loadingIcon.setVisibility(View.INVISIBLE);
    }
    public void showMessage(String errorMessage){
        Toast.makeText(this,errorMessage,Toast.LENGTH_SHORT).show();
    }
    public void setBranch(User user) {
        switch (user.getBranch()) {
            case "CP":
                branch.check(R.id.cp);
                break;
            case "GI":
                branch.check(R.id.gi);
                break;
            case "GRT":
                branch.check(R.id.grt);
                break;
            case "GIL":
                branch.check(R.id.gil);
                break;
            case "GR":
                branch.check(R.id.gr);
                break;
        }
    }
}
