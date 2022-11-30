package com.example.ensamarketplace;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ensamarketplace.model.Announcement;
import com.example.ensamarketplace.utils.Navigator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


public class ShowAnnouncementDetailsActivity extends AppCompatActivity {
    private ImageView image;
    private TextView price;
    private TextView title;
    private TextView description;
    private TextView phone;
    private Announcement announcement;
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_show_announcement_details);
        String documentId = getIntent().getStringExtra("documentId");
        getProductFromDB(documentId);
        image = findViewById(R.id.ann_image);
        price = findViewById(R.id.ann_price);
        title = findViewById(R.id.ann_title);
        description = findViewById(R.id.ann_desc);
        phone = findViewById(R.id.ann_phone);
    }

    public void getProductFromDB(String documentId) {
        DocumentReference docRef = firestore.collection("Announcement").document(documentId);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    announcement = new Announcement(document.get("titre").toString(),
                            document.get("type").toString(),
                            document.get("image") != null ? document.get("image").toString() : "", document.get("branch").toString(),
                            document.get("phone").toString(), document.get("description").toString(),
                            document.get("price").toString(), document.get("userOwner").toString(), documentId);

                    setImageFromUri(announcement);
                    title.setText(announcement.getTitre());
                    price.setText(announcement.getPrice()+" DH");
                    description.setText(announcement.getDescription());
                    phone.setText(announcement.getPhone());
                } else {
                    showMessage("pas de document");
                    Navigator.navigateToHome(this);
                }
            } else {
                showMessage("pas de document");
                Navigator.navigateToHome(this);

            }
        });
    }

    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    private void setImageFromUri(Announcement announcement) {
        try {
            int SDK_INT = android.os.Build.VERSION.SDK_INT;
            if (SDK_INT > 8) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
                InputStream in = new URL(announcement.getImage()).openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                image.setImageBitmap(bitmap);
            } else {
                image.setBackgroundResource(R.drawable.no_image);
            }
        } catch (IOException e) {
            e.printStackTrace();
            image.setBackgroundResource(R.drawable.no_image);
        }
    }
}
