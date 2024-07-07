package com.example.fitsync;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SetReminder extends AppCompatActivity {

    private EditText etReminderTitle;
    private ImageView ivSelectDateTime;
    private Calendar calendar;
    private SimpleDateFormat dateFormat, timeFormat;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Button btnSave;
    private ReminderDatabaseHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_reminder);

        etReminderTitle = findViewById(R.id.etReminderTitle);
        ivSelectDateTime = findViewById(R.id.ivSelectDateTime);
        btnSave = findViewById(R.id.btnSave);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        timeFormat = new SimpleDateFormat("HH:mm");

        dbHelper = new ReminderDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        ivSelectDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker();
            }
        });

        btnSave.setOnClickListener(v -> {
            saveReminder();
            startActivity(new Intent(SetReminder.this,Home.class));
            finish();
        });
    }

    private void showDateTimePicker() {
        // Initialize Date Picker Dialog
        datePickerDialog = new DatePickerDialog(SetReminder.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        // Initialize Time Picker Dialog
                        timePickerDialog = new TimePickerDialog(SetReminder.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                        calendar.set(Calendar.MINUTE, minute);

                                        // Update UI with selected date and time
                                        String selectedDateTime = dateFormat.format(calendar.getTime()) + " " +
                                                timeFormat.format(calendar.getTime());
                                        Toast.makeText(SetReminder.this, "Selected Date and Time: " + selectedDateTime, Toast.LENGTH_SHORT).show();
                                    }
                                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                        timePickerDialog.show();
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void saveReminder() {
        String title = etReminderTitle.getText().toString();
        String dateTime = dateFormat.format(calendar.getTime()) + " " + timeFormat.format(calendar.getTime());

        ContentValues values = new ContentValues();
        values.put(ReminderDatabaseHelper.COLUMN_TITLE, title);
        values.put(ReminderDatabaseHelper.COLUMN_DATETIME, dateTime);

        long newRowId = db.insert(ReminderDatabaseHelper.TABLE_REMINDERS, null, values);

        if (newRowId != -1) {
            Toast.makeText(this, "Reminder Saved", Toast.LENGTH_SHORT).show();

            // Schedule notification using AlarmManager
            scheduleNotification(calendar.getTimeInMillis(), title);

            startActivity(new Intent(SetReminder.this, Home.class));
            finish();
        } else {
            Toast.makeText(this, "Error Saving Reminder", Toast.LENGTH_SHORT).show();
        }
    }

    private void scheduleNotification(long timeInMillis, String title) {
        Intent notificationIntent = new Intent(this, ReminderReceiver.class);
        notificationIntent.putExtra("title", title); // Pass the title to the receiver

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
        }
    }


}
