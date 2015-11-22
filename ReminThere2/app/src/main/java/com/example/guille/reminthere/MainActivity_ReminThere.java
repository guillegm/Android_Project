package com.example.guille.reminthere;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

public class MainActivity_ReminThere extends AppCompatActivity {

    private ListView list;
    private EditText new_reminder;

    private ArrayList<Reminder> reminders;
    private ItemAdapter adapter;

    private void dataChanged() {
        adapter.notifyDataSetChanged();
        saveToFile();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity__remin_there);

        Button add = (Button) findViewById(R.id.button_add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), Activity_Details.class);
                startActivityForResult(myIntent, 0);

            }
        });

        list = (ListView) findViewById(R.id.list);
        new_reminder = (EditText) findViewById(R.id.new_reminder);
        new_reminder.setText("");

        try {
            restoreFromFile();
        } catch (IOException e) {
            reminders = new ArrayList<Reminder>();
        }

        adapter = new ItemAdapter(this, R.layout.activity_new_reminder, reminders);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                reminders.get(pos).toggleChecked();
                dataChanged();
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View v, int pos, long id) {
                removeItem(pos);
                return true;
            }
        });



    }

    private void removeItem(final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.confirm_erase);
        builder.setMessage(R.string.confirm_message);
        builder.setPositiveButton(R.string.erase, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                reminders.remove(pos);
                dataChanged();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.create().show();
    }

    public void onAddItem(View view) {
        String new_reminder_name = new_reminder.getText().toString();
        if (new_reminder_name.isEmpty()) {
            return;
        }
        reminders.add(new Reminder(new_reminder_name));
        new_reminder.setText("");
        dataChanged();
    }
    private static final String DATA_FILE = "reminderlist.obj";

    private void saveToFile() {
        try {
            FileOutputStream fos = openFileOutput(DATA_FILE, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(reminders);
        } catch (FileNotFoundException e) {
            Log.e("ReminThere", "saveToFile: FileNotFoundException");
        } catch (IOException e) {
            Log.e("ReminThere", "saveToFile: IOException");
        }
    }

    private void restoreFromFile() throws IOException {
        try {
            FileInputStream fis = openFileInput(DATA_FILE);
            ObjectInputStream ois = new ObjectInputStream(fis);
            reminders = (ArrayList<Reminder>)ois.readObject();
        } catch (ClassNotFoundException e) {
            Log.e("ReminThere", "restoreFromFile: ClassNotFoundException");
        } catch (OptionalDataException e) {
            Log.e("ReminThere", "restoreFromFile: OptionalDataException");
        } catch (StreamCorruptedException e) {
            Log.e("ReminThere", "restoreFromFile: StreamCorruptedException");
        }
    }
}


/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity__remin_there, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

*/