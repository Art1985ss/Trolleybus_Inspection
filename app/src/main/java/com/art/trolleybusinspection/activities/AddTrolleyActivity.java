package com.art.trolleybusinspection.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.art.trolleybusinspection.R;
import com.art.trolleybusinspection.database.TrolleyRepository;
import com.art.trolleybusinspection.entity.Trolley;
import com.art.trolleybusinspection.entity.enums.TrolleyModel;

import java.time.LocalDate;
import java.util.Calendar;

import static com.art.trolleybusinspection.config.ValueConstants.*;

public class AddTrolleyActivity extends AppCompatActivity {
    private TrolleyRepository trolleyRepository;
    private ArrayAdapter<TrolleyModel> arrayAdapter;
    private EditText editTextTrolleyId;
    private Spinner spinnerTrolleyModel;
    private EditText editTextDateR0;
    private EditText editTextDateR1;
    private EditText editTextTractionMotor;
    private EditText editTextAkb1;
    private EditText editTextAkb2;
    private EditText editTextDieselEngine;
    private EditText editTextDieselGenerator;
    private EditText editTextMillage;
    private EditText editTextNotes;



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

        DatePickerDialog.OnDateSetListener onDateSetListenerR0 = (view, year, month, dayOfMonth) -> {
            LocalDate date = LocalDate.of(year, month + 1, dayOfMonth);
            editTextDateR0.setText(date.format(DATE_FORMAT));
        };
        DatePickerDialog.OnDateSetListener onDateSetListenerR1 = (view, year, month, dayOfMonth) -> {
            LocalDate date = LocalDate.of(year, month + 1, dayOfMonth);
            editTextDateR1.setText(date.format(DATE_FORMAT));
        };
        editTextDateR0 = findViewById(R.id.edit_text_dateR0);
        editTextDateR0.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    onDateSetListenerR0,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show();
        });
        editTextDateR1 = findViewById(R.id.edit_text_dateR1);
        editTextDateR1.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    onDateSetListenerR1,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show();
        });

        editTextTractionMotor = findViewById(R.id.edit_text_traction_engine);
        editTextAkb1 = findViewById(R.id.edit_text_akb1);
        editTextAkb2 = findViewById(R.id.edit_text_akb2);
        editTextDieselEngine = findViewById(R.id.edit_text_diesel_engine);
        editTextDieselGenerator = findViewById(R.id.edit_text_diesel_generator);
        editTextMillage = findViewById(R.id.edit_text_nobraukums);
        editTextNotes = findViewById(R.id.edit_text_piezimes);

        Intent intent = getIntent();
        if (intent.hasExtra(TROLLEY_ID)) {
            setTitle("Edit trolley");
            editTextTrolleyId.setEnabled(false);
            populate(intent.getIntExtra(TROLLEY_ID, -1));
        } else {
            setTitle("Add new trolley");
        }

        Button addNoteDate = findViewById(R.id.add_date_btn);
        addNoteDate.setOnClickListener(v -> {
            String text = editTextNotes.getText().toString().trim();
            text += "\n";
            Calendar calendar = Calendar.getInstance();
            LocalDate date = LocalDate.of(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH) + 1,
                    calendar.get(Calendar.DAY_OF_MONTH));
            text += date.format(DATE_FORMAT);
            text += " ";
            editTextNotes.setText(text);
            editTextNotes.setSelection(text.length());
        });
    }

    private void populate(int id) {
        Trolley trolley = trolleyRepository.findById(id);
        if (trolley == null) {
            Toast.makeText(this, "There is no trolley with id " + id, Toast.LENGTH_SHORT).show();
            return;
        }
        editTextTrolleyId.setText(String.valueOf(trolley.getId()));
        spinnerTrolleyModel.setSelection(arrayAdapter.getPosition(trolley.getModel()));
        editTextDateR0.setText(trolley.getDateR0().format(DATE_FORMAT));
        editTextDateR1.setText(trolley.getDateR1().format(DATE_FORMAT));
        editTextTractionMotor.setText(String.valueOf(trolley.getTractionMotorNumber()));
        editTextAkb1.setText(String.valueOf(trolley.getAkb1Id()));
        editTextAkb2.setText(String.valueOf(trolley.getAkb2Id()));
        editTextDieselGenerator.setText(String.valueOf(trolley.getDieselGeneratorNumber()));
        editTextDieselEngine.setText(String.valueOf(trolley.getDieselEngineNumber()));
        editTextMillage.setText(String.valueOf(trolley.getMileage()));
        editTextNotes.setText(trolley.getNote());
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
                System.out.println("saving");
                saveTrolley();
                return true;
            case R.id.copy:
                copy();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void copy() {
        ClipboardManager clipboardManager =
                (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        Trolley trolley = getTrolley();
        if (trolley == null) {
            Toast.makeText(this, "Could not be copied to clipboard", Toast.LENGTH_LONG).show();
            return;
        }
        ClipData clipData = ClipData.newPlainText(
                "Trolley id " + trolley.getId(),
                trolley.toString());
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(this, "Copied to clipboard", Toast.LENGTH_LONG).show();
    }

    private void saveTrolley() {
        Trolley trolley = getTrolley();
        if (trolley == null) return;

        if (getIntent().getIntExtra(TROLLEY_ID, -1) == -1) {
            Trolley t = trolleyRepository.findById(trolley.getId());
            if (t != null) {
                new AlertDialog.Builder(this)
                        .setTitle("Confirm save")
                        .setMessage("Trolleybus with such id already exists,\ndo you want to save it with new data?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            trolleyRepository.insert(trolley);
                            Toast.makeText(this, "Trolley saved", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            finish();
                        }).setNegativeButton("No", null).show();
            } else {
                Toast.makeText(this, "Trolley saved", Toast.LENGTH_SHORT).show();
                trolleyRepository.insert(trolley);
                setResult(RESULT_OK);
                finish();
            }
        } else {
            trolleyRepository.update(trolley);
            Toast.makeText(this, "Trolley updated", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        }
    }

    @Nullable
    private Trolley getTrolley() {
        if (editTextTrolleyId.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please provide troll id", Toast.LENGTH_LONG).show();
            return null;
        }
        Trolley trolley = new Trolley(Integer.parseInt(editTextTrolleyId.getText().toString().trim()));
        trolley.setModel(TrolleyModel.valueOf(spinnerTrolleyModel.getSelectedItem().toString()));

        if (editTextDateR0.getText().toString().trim().isEmpty()) {
            trolley.setDateR0(LocalDate.now());
        } else {
            try {
                trolley.setDateR0(LocalDate.parse(editTextDateR0.getText().toString().trim(), DATE_FORMAT));
            } catch (Exception e) {
                Toast.makeText(this, "Please provide date in format :\ndd.MM.yyyy", Toast.LENGTH_LONG).show();
                return null;
            }
        }
        if (editTextDateR1.getText().toString().trim().isEmpty()) {
            trolley.setDateR1(LocalDate.now());
        } else {
            try {
                trolley.setDateR1(LocalDate.parse(editTextDateR1.getText().toString().trim(), DATE_FORMAT));
            } catch (Exception e) {
                Toast.makeText(this, "Please provide date in format :\ndd.MM.yyyy", Toast.LENGTH_LONG).show();
                return null;
            }
        }
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
        if (editTextNotes.getText().toString().trim().isEmpty()) {
            trolley.setNote("");
        } else {
            trolley.setNote(editTextNotes.getText().toString());
        }
        return trolley;
    }
}