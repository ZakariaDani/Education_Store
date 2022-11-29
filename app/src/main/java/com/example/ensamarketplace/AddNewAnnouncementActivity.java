package com.example.ensamarketplace;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ensamarketplace.model.Announcement;
import com.example.ensamarketplace.model.User;
import com.example.ensamarketplace.utils.BottomBar;
import com.example.ensamarketplace.utils.Branch;
import com.example.ensamarketplace.utils.BranchAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.List;
import java.util.UUID;

public class AddNewAnnouncementActivity extends AppCompatActivity {

    View stepOne,stepTwo;
    EditText title;
    EditText description;
    EditText price;
    Button submitButton,nextButton;
    ProgressBar loadingIcon;
    RadioGroup type;
    Spinner branchSpinner;
    ImageView uploadImage;
    BottomNavigationView bottomNavigationView;
    Uri imageUri;
    String titreInput, descriptionInput, priceInput, typeInput="produit", branchInput, imageInput;
    User user = new User();

    private final FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
    private final FirebaseStorage firebaseStorage= FirebaseStorage.getInstance();
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private final int GALLERY_REQ_CODE = 1000;

    private final List<String> branches = List.of("CP","GI","GIL","GRT","GE","ENSA");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_new_announcement);
        title = findViewById(R.id.title);
        branchSpinner = findViewById(R.id.branchSpinner);
        description = findViewById(R.id.description);
        price = findViewById(R.id.price);
        type = findViewById(R.id.type);
        uploadImage = findViewById(R.id.uploadImage);
        stepOne = findViewById(R.id.stepOne);
        stepTwo = findViewById(R.id.stepTwo);
        loadingIcon = findViewById(R.id.loadingIcon);
        submitButton = findViewById(R.id.submitButton);
        nextButton = findViewById(R.id.nextButton);
        BranchAdapter adapter = new BranchAdapter(getApplicationContext(), Branch.getAllBranches());
        branchSpinner.setAdapter(adapter);
        bottomNavigationView = findViewById(R.id.bottom_bar);
        stepTwo.setClickable(false);
        stepTwo.setVisibility(View.INVISIBLE);
        BottomBar.setupEvents(bottomNavigationView,this);
        getConnectedUser();

    }

    public void getImageFromGallery(View view) {
        Intent iGallery = new Intent(Intent.ACTION_PICK);
        iGallery.setType("image/*");
        iGallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(iGallery, GALLERY_REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK &&requestCode == GALLERY_REQ_CODE
                &&data!=null &&data.getData()!=null) {
            uploadImage.setImageURI(data.getData());
            imageUri = data.getData();
        }
    }

    public void addNewAnnouncement(View view) {
//        this.overridePendingTransition();
        titreInput = title.getText().toString();
        descriptionInput = description.getText().toString();
        priceInput = price.getText().toString();
        branchInput = branches.get((int) branchSpinner.getSelectedItem());
        RadioButton radio = findViewById(type.getCheckedRadioButtonId());
        typeInput = radio.getText().toString();
        System.out.println(new Announcement(titreInput, typeInput, imageInput,
                branchInput, user.getPhone(), descriptionInput,
                priceInput, firebaseAuth.getCurrentUser().getUid()));

        boolean validateForm = validateForm();
        if (validateForm) {
            enableLoadingAnimation();
            if (imageUri != null) {
                String randomUid = UUID.randomUUID().toString();
                StorageReference cloudStorage = firebaseStorage.getReference().child("images/" + randomUid);
                cloudStorage.putFile(imageUri).addOnSuccessListener(
                        taskSnapshot -> cloudStorage.getDownloadUrl().addOnSuccessListener(uri -> {
                            Uri downloadUrl = uri;
                            imageInput = downloadUrl.toString();
                            saveAnnouncement();
                        })
                ).addOnCompleteListener(
                        listener -> disableLoadingAnimation()
                );
            } else {
                saveAnnouncement();
            }

        }
    }
    public void saveAnnouncement(){
        Announcement announcement = new Announcement(titreInput, typeInput, imageInput,
                branchInput, user.getPhone(), descriptionInput,
                priceInput, firebaseAuth.getCurrentUser().getUid());

        fireStore.collection("Announcement").add(announcement)
                .addOnSuccessListener(
                        (OnSuccessListener) o -> showMessage("Votre annonce a été creé avec succée")
                )
                .addOnFailureListener(
                        e -> showMessage("notre serveur est en service pour le moment")
                ).addOnCompleteListener(
                        (OnCompleteListener<Void>) task -> {disableLoadingAnimation();}
                );
    }

    public boolean validateForm(){
        boolean validForm = false;

        if (titreInput.isEmpty() ||
                typeInput.isEmpty() || priceInput.isEmpty() || descriptionInput.isEmpty() ) {
            showMessage("Tous les champs doivent etre remplis");
        }
        else if(!priceInput.matches("^[0-9]+")){
            showMessage("Le prix doit etre un nombre positif");
        }
        else{
            validForm = true;
        }
        return validForm;
    }
    public void getConnectedUser() {
        DocumentReference docRef = fireStore.collection("Users").document(firebaseAuth.getCurrentUser().getUid());
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                user.setName(document.get("name").toString());
                user.setEmail(document.get("email").toString());
                user.setPhone(document.get("phone").toString());
                user.setBranch(document.get("branch").toString());
                if (document.exists()) {
                    System.out.println("data: " + user);
                } else {
                    System.out.println("No such document");
                }
            } else {
                System.out.println("get failed with "+ task.getException());
            }
        });
    }

    public void  navigateToStepTwo(View view){
        stepOne.animate().alpha(0f).setDuration(800);
        stepTwo.animate().alpha(1f).setDuration(800);
        stepOne.setVisibility(View.INVISIBLE);
        stepTwo.setVisibility(View.VISIBLE);
        stepOne.setClickable(false);
        stepTwo.setClickable(true);


    }
    public void  backToStepOne(View view){
        stepOne.animate().alpha(1f).setDuration(800);
        stepTwo.animate().alpha(0f).setDuration(800);
        stepTwo.setVisibility(View.INVISIBLE);
        stepOne.setVisibility(View.VISIBLE);
        stepTwo.setClickable(false);
        stepOne.setClickable(true);
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
}