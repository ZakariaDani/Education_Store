package com.example.ensamarketplace;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ensamarketplace.model.Announcement;
import com.example.ensamarketplace.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AddNewAnnouncementActivity extends AppCompatActivity {
    EditText titre;
    EditText phone;
    EditText description;
    EditText price;
    RadioGroup branch;
    ProgressBar loadingIcon;
    Switch serviceSwitch;
    User user;
    ImageView uploadImage;
    String titreInput, phoneInput, descriptionInput, priceInput, typeInput="produit", branchInput, imageInput;
    private final FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
    private final StorageReference fireStoreCloud = FirebaseStorage.getInstance().getReference();
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private final int GALLERY_REQ_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_new_announcement);
        titre = findViewById(R.id.titre);
        phone = findViewById(R.id.phone);
        branch = findViewById(R.id.branch);
        price = findViewById(R.id.price);
        description = findViewById(R.id.description);
        loadingIcon = findViewById(R.id.loadingIcon);
        serviceSwitch = findViewById(R.id.switchService);
        uploadImage = findViewById(R.id.uploadImage);
        serviceSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            typeInput = isChecked ? "service":"produit";
            System.out.println(isChecked);
        });
        user = getConnectedUser();
    }

    public void uploadImage(View view) {
        Intent iGallery = new Intent(Intent.ACTION_PICK);
        iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(iGallery, GALLERY_REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {
            if(requestCode == GALLERY_REQ_CODE) {
                uploadImage.setImageURI(data.getData());
                fireStoreCloud.putFile(data.getData()).addOnCompleteListener(listener -> {
                    System.out.println(listener.getResult().getUploadSessionUri().toString());
                    imageInput = listener.getResult().getUploadSessionUri().toString();
                });

            }
        }
    }


    public void addNewAnnouncement(View view) {
        titreInput = titre.getText().toString();
        phoneInput = phone.getText().toString();
        descriptionInput = description.getText().toString();
        priceInput = price.getText().toString();
        RadioButton radio = findViewById(branch.getCheckedRadioButtonId());
        branchInput = radio.getText().toString();
        boolean validateForm = validateForm();
        if(validateForm){
            enableLoadingAnimation();
            Announcement announcement = new Announcement(titreInput, typeInput, imageInput, branchInput, phoneInput, descriptionInput, priceInput, firebaseAuth.getCurrentUser().getUid());
            System.out.println(announcement);
            saveAnnouncement(announcement);
        }


    }

    public void saveAnnouncement(Announcement announcement){
        fireStore.collection("Announcement").add(announcement)
                .addOnSuccessListener(
                        (OnSuccessListener) o -> showMessage("Votre annonce a été creé avec succée")
                )
                .addOnFailureListener(
                        e -> showMessage("notre serveur est en service pour le moment")
                ).addOnCompleteListener(
                        (OnCompleteListener<Void>) task -> disableLoadingAnimation()
                );
    }

    public boolean validateForm(){
        boolean validForm = false;

        if (titreInput.isEmpty() ||
                typeInput.isEmpty() || branchInput.isEmpty() || phoneInput.isEmpty() || descriptionInput.isEmpty() ) {
            showMessage("Tous les champs doivent etre remplis");
        }else if (!phoneInput.matches("^0[5-8][0-9]+$")) {
            showMessage("Ce numéro de téléphone est invalide");
        }
        else{
            validForm = true;
        }
        return validForm;
    }
    public User getConnectedUser() {
        User user = new User();
        DocumentReference docRef = fireStore.collection("Users").document(firebaseAuth.getCurrentUser().getUid());
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                user.setName(document.get("name").toString());
                user.setEmail(document.get("email").toString());
                user.setPhone(document.get("phone").toString());
                user.setBranch(document.get("branch").toString());
                phone.setText(user.getPhone());
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
