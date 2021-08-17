package com.art.trolleybusinspection.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Checkable;
import android.widget.Toast;

import com.art.trolleybusinspection.R;
import com.art.trolleybusinspection.TrolleyViewModel;
import com.art.trolleybusinspection.adapter.TrolleyAdapter;
import com.art.trolleybusinspection.entity.Trolley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Comparator;
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
        prepareView(null);
    }

    private void prepareView(Comparator<Trolley> comparator) {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        trolleyAdapter = new TrolleyAdapter(comparator);
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
                        .setNegativeButton("No", (dialog, which) -> trolleyAdapter.notifyDataSetChanged()).show();
            }
        }).attachToRecyclerView(recyclerView);

        trolleyAdapter.setOnItemClickListener(trolley -> {
            Intent intent = new Intent(MainActivity.this, AddTrolleyActivity.class);
            intent.putExtra(TROLLEY_ID, trolley.getId());
            startActivityForResult(intent, EDIT_REQUEST);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_sort, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Comparator<Trolley> trolleyComparator = Comparator.comparing(Trolley::getId);
        switch (item.getItemId()) {
            case R.id.sort_by_id:
                item.setChecked(true);
                trolleyComparator = Comparator.comparing(Trolley::getId);
                break;
            case R.id.sort_by_dateR0:
                item.setChecked(true);
                trolleyComparator = Comparator.comparing(Trolley::getDateR0).reversed();
                break;
            case R.id.sort_by_dateR1:
                item.setChecked(true);
                trolleyComparator = Comparator.comparing(Trolley::getDateR1).reversed();
                break;
            case R.id.show_statistics:
                Intent intent = new Intent(MainActivity.this, Statistics.class);
                startActivity(intent);
                break;
            case R.id.export_to_csv:
                Toast.makeText(this, "Not implemented!", Toast.LENGTH_LONG).show();
            default:
                return super.onOptionsItemSelected(item);
        }
        prepareView(trolleyComparator);
        return super.onOptionsItemSelected(item);

    }

    private void exportToCSV() {
        String text = "id;model;date;traction engine;akb1;akb2;generator;diesel engine;millage;notes;\n";
        text += Objects.requireNonNull(viewModel.getAll().getValue()).stream().map(Trolley::toCSV).collect(Collectors.joining("\n"));
        String path = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + "/" + CSV_NAME;
        try (FileOutputStream fos =new FileOutputStream(new File(path))) {
            fos.write(text.getBytes());
            Toast.makeText(this, "File saved : \n" + path, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}