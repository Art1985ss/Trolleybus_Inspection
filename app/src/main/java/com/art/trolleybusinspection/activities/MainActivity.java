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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.art.trolleybusinspection.R;
import com.art.trolleybusinspection.TrolleyViewModel;
import com.art.trolleybusinspection.adapter.TrolleyAdapter;
import com.art.trolleybusinspection.entity.Trolley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.art.trolleybusinspection.config.ValueConstants.*;

public class MainActivity extends AppCompatActivity {
    private TrolleyViewModel viewModel;
    private TrolleyAdapter trolleyAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prepareView(false);
    }

    private void prepareView(boolean sortByDate) {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        trolleyAdapter = new TrolleyAdapter(sortByDate);
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
                            Toast.makeText(MainActivity.this, "Trolleybus deleted", Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_sort, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.sort_by_date) {
            if (item.isChecked()) {
                item.setChecked(false);
                prepareView(false);
            } else {
                item.setChecked(true);
                prepareView(true);
            }
        } else if (item.getItemId() == R.id.show_statistics) {
            Intent intent = new Intent(MainActivity.this, Statistics.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.export_to_csv) {
            String text = "id;model;date;traction engine;akb1;akb2;generator;diesel engine;millage;notes;\n";
            text += Objects.requireNonNull(viewModel.getAll().getValue()).stream().map(Trolley::toCSV).collect(Collectors.joining("\n"));
            try(FileOutputStream fos = openFileOutput(CSV_NAME, MODE_PRIVATE)) {
                fos.write(text.getBytes());
                Toast.makeText(this, "File saved : \n" + getDataDir() + CSV_NAME, Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return super.onOptionsItemSelected(item);

    }
}