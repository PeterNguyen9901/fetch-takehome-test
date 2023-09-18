package com.fetchhiring;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class MainView extends AppCompatActivity {

    private RecyclerView recyclerView;

    // Adapter that will be used to populate the RecyclerView with items
    private ItemAdapter itemAdapter;
    private ItemViewModel itemViewModel;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the UI layout for this Activity
        setContentView(R.layout.activity_main);





        // Bind the RecyclerView from the layout to the member variable
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressBar = findViewById(R.id.progressBar);


        // Initializing the adapter and setting it to the RecyclerView
        itemAdapter = new ItemAdapter(this, new ArrayList<>());  // Initialized with an empty list
        recyclerView.setAdapter(itemAdapter);


        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        itemViewModel.itemsLiveData.observe(this, items -> {
            itemAdapter.updateItems(items);
        });
        //for loading screen, set the visibility of progress and view based off state
        itemViewModel.loadingState.observe(this, state -> {
            switch (state) {
                case LOADING:
                    progressBar.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    break;
                case LOADED:
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    break;
                case ERROR:
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    // Show error message or handle it as per your requirement
                    Toast.makeText(MainView.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                    break;
            }
        });

        itemViewModel.fetchItems();
    }



}
