package com.art.trolleybusinspection.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.art.trolleybusinspection.R;
import com.art.trolleybusinspection.TrolleyViewModel;
import com.art.trolleybusinspection.adapter.TrolleyAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.art.trolleybusinspection.config.ValueConstants.*;

public class MainActivity extends AppCompatActivity {
    private TrolleyViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        TrolleyAdapter trolleyAdapter = new TrolleyAdapter();
        recyclerView.setAdapter(trolleyAdapter);

        viewModel = new ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(TrolleyViewModel.class);
        viewModel.getAll().observe(this, trolleyAdapter::setTrolleys);

        FloatingActionButton addTrolleyButton = findViewById(R.id.button_add_trolley);
        addTrolleyButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTrolleyActivity.class);
            startActivityForResult(intent, ADD_REQUEST);
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Delete trolley")
                .setMessage("Do you really want to delete this trolley from records?")
                .setIcon(R.drawable.ic__delete);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                builder
                        .setPositiveButton("Ok", (dialog, which) -> {
                            viewModel.delete(trolleyAdapter.getTrolleyAt(viewHolder.getAdapterPosition()));
                            Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("No", (dialog, which) -> {
                            trolleyAdapter.notifyDataSetChanged();
                        }).show();
            }
        }).attachToRecyclerView(recyclerView);

        trolleyAdapter.setOnItemClickListener(trolley -> {
            Intent intent = new Intent(MainActivity.this, AddTrolleyActivity.class);
            intent.putExtra(TROLLEY_ID, trolley.getId());
            startActivityForResult(intent, EDIT_REQUEST);
        });
    }

    public void recreateActivity() {
        recreate();
    }
}