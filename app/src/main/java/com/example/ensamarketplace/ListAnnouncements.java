package com.example.ensamarketplace;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ensamarketplace.model.Announcement;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ListAnnouncements extends AppCompatActivity {
    private List<Announcement> announcements = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecycleViewAdapter recycleViewAdapter;
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().hide();

        setContentView(R.layout.activity_list_announcements);

        prepareAnnouncements();
        setupRecycleView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.actionSearch);
        SearchView searchView = (SearchView) searchItem.getActionView();
        if(searchView.hasFocus()){
            searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    recycleViewAdapter.getFilter().filter(newText);
                    return false;
                }
            });
        }

        return true;
    }

    private void setupRecycleView() {
        recyclerView = findViewById(R.id.recyclerView);

        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recycleViewAdapter = new RecycleViewAdapter(announcements);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recycleViewAdapter);
    }

    private void prepareAnnouncements(){
        /*firestore.collection("Announcement").
                get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            Announcement announcement = d.toObject(Announcement.class);
                            announcements.add(announcement);
                        }
                    } else {
                        showMessage("No data found in Database");
                    }
                }).addOnFailureListener(e -> {
                    showMessage("Fail to get the data.");
                });
                */

        Announcement announcement1 = new Announcement();
        announcement1.setTitre("Cours cp1");
        Announcement announcement2 = new Announcement();
        announcement2.setTitre("Cours cp1");
        Announcement announcement3 = new Announcement();
        announcement3.setTitre("Cours cp1");
        Announcement announcement4 = new Announcement();
        announcement4.setTitre("Cours cp1");
        Announcement announcement5 = new Announcement();
        announcement5.setTitre("Cours cp1");
        Announcement announcement6 = new Announcement();
        announcement6.setTitre("Cours cp1");
        Announcement announcement7 = new Announcement();
        announcement7.setTitre("Cours cp1");
        Announcement announcement8 = new Announcement();
        announcement8.setTitre("Cours cp1");
        Announcement announcement9 = new Announcement();
        announcement9.setTitre("Cours cp1");
        announcements.add(announcement1);
        announcements.add(announcement2);
        announcements.add(announcement3);
        announcements.add(announcement4);
        announcements.add(announcement5);
        announcements.add(announcement6);
        announcements.add(announcement7);
        announcements.add(announcement8);
        announcements.add(announcement9);
    }

    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
