package com.art.trolleybusinspection.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.art.trolleybusinspection.R;
import com.art.trolleybusinspection.database.TrolleyRepository;
import com.art.trolleybusinspection.entity.Trolley;
import com.art.trolleybusinspection.entity.enums.TrolleyModel;

import java.time.LocalDate;

import static com.art.trolleybusinspection.config.ValueConstants.*;

public class AddTrolleyActivity extends AppCompatActivity {
    private TrolleyRepository trolleyRepository;
    private ArrayAdapter<TrolleyModel> arrayAdapter;
    private EditText editTextTrolleyId;
    private Spinner spinnerTrolleyModel;
    private EditText editTextTractionMotor;
    private EditText editTextAkb1;
    private EditText editTextAkb2;
    private EditText editTextDieselEngine;
    private EditText editTextDieselGenerator;
    private EditText editTextMillage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trolley);

        trolleyRepository = TrolleyRepository.getInstance(this.getApplication());

        editTextTrolleyId = findViewById(R.id.edit_text_trolley_id);
        spinnerTrolleyModel = findViewById(R.id.spinner_trolley_model);
        arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                TrolleyModel.values());
        spinnerTrolleyModel.setAdapter(arrayAdapter);
        editTextTractionMotor = findViewById(R.id.edit_text_traction_engine);
        editTextAkb1 = findViewById(R.id.edit_text_akb1);
        editTextAkb2 = findViewById(R.id.edit_text_akb2);
        editTextDieselEngine = findViewById(R.id.edit_text_diesel_engine);
        editTextDieselGenerator = findViewById(R.id.edit_text_diesel_generator);
        editTextMillage = findViewById(R.id.edit_text_nobraukums);

        Intent intent = getIntent();
        if (intent.hasExtra(TROLLEY_ID)) {
            setTitle("Edit trolley");
            editTextTrolleyId.setEnabled(false);
            populate(intent.getIntExtra(TROLLEY_ID, -1));
        } else {
            setTitle("Add new trolley");
        }
    }

    private void populate(int id) {
        Trolley trolley = trolleyRepository.findById(id);
        if (trolley == null) {
            Toast.makeText(this, "There is no trolley with id " + id, Toast.LENGTH_SHORT).show();
            return;
        }
        editTextTrolleyId.setText(String.valueOf(trolley.getId()));
        spinnerTrolleyModel.setSelection(arrayAdapter.getPosition(trolley.getModel()));
        editTextTractionMotor.setText(String.valueOf(trolley.getTractionMotorNumber()));
        editTextAkb1.setText(String.valueOf(trolley.getAkb1Id()));
        editTextAkb2.setText(String.valueOf(trolley.getAkb2Id()));
        editTextDieselGenerator.setText(String.valueOf(trolley.getDieselGeneratorNumber()));
        editTextDieselEngine.setText(String.valueOf(trolley.getDieselEngineNumber()));
        editTextMillage.setText(String.valueOf(trolley.getMileage()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_edit_trolley, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_button:
                saveTrolley();
                Toast.makeText(this, "Trolley saved", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveTrolley() {
        if (editTextTrolleyId.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please provide troll id", Toast.LENGTH_LONG).show();
            return;
        }
        Trolley trolley = new Trolley(Integer.parseInt(editTextTrolleyId.getText().toString().trim()));
        trolley.setDate(LocalDate.now());
        trolley.setModel(TrolleyModel.valueOf(spinnerTrolleyModel.getSelectedItem().toString()));
        if (editTextMillage.getText().toString().trim().isEmpty()) {
            trolley.setMileage(0);
        } else {
            trolley.setMileage(Integer.parseInt(editTextMillage.getText().toString().trim()));
        }
        if (editTextAkb1.getText().toString().trim().isEmpty()) {
            trolley.setAkb2Id(0);
        } else {
            trolley.setAkb1Id(Integer.parseInt(editTextAkb1.getText().toString().trim()));
        }
        if (editTextAkb2.getText().toString().trim().isEmpty()) {
            trolley.setAkb2Id(0);
        } else {
            trolley.setAkb2Id(Integer.parseInt(editTextAkb2.getText().toString().trim()));
        }
        if (editTextTractionMotor.getText().toString().trim().isEmpty()) {
            trolley.setTractionMotorNumber(0);
        } else {
            trolley.setTractionMotorNumber(Integer.parseInt(editTextTractionMotor.getText().toString().trim()));
        }
        if (editTextDieselEngine.getText().toString().trim().isEmpty()) {
            trolley.setDieselEngineNumber(0);
        } else {
            trolley.setDieselEngineNumber(Integer.parseInt(editTextDieselEngine.getText().toString().trim()));
        }
        if (editTextDieselGenerator.getText().toString().trim().isEmpty()) {
            trolley.setDieselGeneratorNumber(0);
        } else {
            trolley.setDieselGeneratorNumber(Integer.parseInt(editTextDieselGenerator.getText().toString().trim()));
        }
        if (getIntent().getIntExtra(TROLLEY_ID, -1) == -1) {
            trolleyRepository.insert(trolley);
        } else {
            trolleyRepository.update(trolley);
        }
        setResult(RESULT_OK);
        finish();
    }
}