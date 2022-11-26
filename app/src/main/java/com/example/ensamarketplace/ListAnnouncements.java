package com.example.ensamarketplace;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ensamarketplace.model.Announcement;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListAnnouncements extends AppCompatActivity {

    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    private List<Announcement> announcements = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecycleViewAdapter recycleViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        updateActionBarStyles();

        setContentView(R.layout.activity_list_announcements);

        prepareAnnouncements();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.actionSearch);
        SearchView searchView = (SearchView) searchItem.getActionView();
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
        return true;
    }

    private void updateActionBarStyles() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Liste des annonces");
        ColorDrawable colorDrawable = new ColorDrawable(getResources().getColor(R.color.main_color));
        actionBar.setBackgroundDrawable(colorDrawable);
    }

    private void setupRecycleView() {
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recycleViewAdapter = new RecycleViewAdapter(announcements);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recycleViewAdapter);
    }


    private void prepareAnnouncements() {
        firestore.collection("Announcement").
                get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    updateData(queryDocumentSnapshots);
                    setupRecycleView();
                })
                .addOnFailureListener(e -> showMessage("Echec"));
    }

    private void updateData(QuerySnapshot queryDocumentSnapshots) {
        if (!queryDocumentSnapshots.isEmpty()) {
            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
            announcements = list.stream()
                    .map(d -> d.toObject(Announcement.class))
                    .collect(Collectors.toList());
        } else {
            showMessage("Aucune donnée trouvée dans la base de données");
        }
    }

    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}