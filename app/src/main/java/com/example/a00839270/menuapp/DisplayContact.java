package com.example.a00839270.menuapp;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import android.support.v7.app.ActionBarActivity;

import com.example.sairamkrishna.myapplication.R;

public class DisplayContact extends ActionBarActivity {
    private DBHelper mydb ;

    TextView name ;
    TextView serial;
    TextView data;
    TextView place;
    TextView time;
    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);
        name = (TextView) findViewById(R.id.editTextName);
        serial = (TextView) findViewById(R.id.editTextSerial);
        data = (TextView) findViewById(R.id.editTextPlace);
        place = (TextView) findViewById(R.id.editTextData);
        time = (TextView) findViewById(R.id.editTextTime);

        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            int Value = extras.getInt("id");

            if(Value>0){
                //means this is the view part not the add contact part.
                Cursor rs = mydb.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();

                String nameA = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_NAME));
                String serialA = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_SERIAL));
                String dataA = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_DATA));
                String placeA = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_PLACE));
                String timeA = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_TIME));

                if (!rs.isClosed())  {
                    rs.close();
                }
                Button b = (Button)findViewById(R.id.button1);
                b.setVisibility(View.INVISIBLE);

                name.setText((CharSequence)nameA);
                name.setFocusable(false);
                name.setClickable(false);

                serial.setText((CharSequence)serialA);
                serial.setFocusable(false);
                serial.setClickable(false);

                data.setText((CharSequence)dataA);
                data.setFocusable(false);
                data.setClickable(false);

                place.setText((CharSequence)placeA);
                place.setFocusable(false);
                place.setClickable(false);

                time.setText((CharSequence)timeA);
                time.setFocusable(false);
                time.setClickable(false);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Bundle extras = getIntent().getExtras();

        if(extras !=null) {
            int Value = extras.getInt("id");
            if(Value>0){
                getMenuInflater().inflate(R.menu.display_contact, menu);
            } else{
                getMenuInflater().inflate(R.menu.main_menu, menu);
            }
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()) {
            case R.id.Edit_Contact:
                Button b = (Button)findViewById(R.id.button1);
                b.setVisibility(View.VISIBLE);
                name.setEnabled(true);
                name.setFocusableInTouchMode(true);
                name.setClickable(true);

                serial.setEnabled(true);
                serial.setFocusableInTouchMode(true);
                serial.setClickable(true);

                data.setEnabled(true);
                data.setFocusableInTouchMode(true);
                data.setClickable(true);

                place.setEnabled(true);
                place.setFocusableInTouchMode(true);
                place.setClickable(true);

                time.setEnabled(true);
                time.setFocusableInTouchMode(true);
                time.setClickable(true);

                return true;
            case R.id.Delete_Contact:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.deleteContact)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mydb.deleteContact(id_To_Update);
                                Toast.makeText(getApplicationContext(), "Deleted Successfully",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });

                AlertDialog d = builder.create();
                d.setTitle("Are you sure");
                d.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void run(View view) {
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            int Value = extras.getInt("id");
            if(Value>0){
                if(mydb.updateContact(id_To_Update,name.getText().toString(),
                        serial.getText().toString(), data.getText().toString(),
                        place.getText().toString(), time.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                }
            } else{
                if(mydb.insertContact(name.getText().toString(), serial.getText().toString(),
                        data.getText().toString(), place.getText().toString(),
                        time.getText().toString())){
                    Toast.makeText(getApplicationContext(), "done",
                            Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getApplicationContext(), "not done",
                            Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        }
    }
}