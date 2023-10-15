package com.IOVIS.datavault;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.IOVIS.datavault.R;

public class UpdateActivity extends AppCompatActivity {
    EditText edit_name,edit_number,edit_dob,edit_email;
    Button update_button,delete_button;
    String id,name,phone,dob,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setting the page
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        edit_name = findViewById(R.id.edit_name2);
        edit_number = findViewById(R.id.edit_number2);
        edit_dob = findViewById(R.id.edit_dob2);
        edit_email = findViewById(R.id.edit_email2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);
        getAndSetIntentData();

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Go to the database and updates data
                Mydatabase myDB = new Mydatabase(UpdateActivity.this);
                name = edit_name.getText().toString().trim();
                phone = edit_number.getText().toString().trim();
                dob = edit_dob.getText().toString().trim();
                email = edit_email.getText().toString().trim();
                myDB.updateData(id,name,phone,dob,email);
            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mydatabase myDB = new Mydatabase(UpdateActivity.this);
                confirmDialog();
            }
        });

    }
//    getting data from Main_activity to Update_activity
    void getAndSetIntentData() {
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name") &&
                getIntent().hasExtra("phone") && getIntent().hasExtra("dob")
                && getIntent().hasExtra("email")){
            //Getting Data from Intent
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            phone = getIntent().getStringExtra("phone");
            dob = getIntent().getStringExtra("dob");
            email = getIntent().getStringExtra("email");

            //Setting Intent Data
            edit_name.setText(name);
            edit_number.setText(phone);
            edit_dob.setText(dob);
            edit_email.setText(email);
        } else {
            Toast.makeText(this, "No Data!", Toast.LENGTH_SHORT).show();
        }
    }
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name + " ?");
        builder.setMessage("Are you sure you want to delete?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Mydatabase myDB = new Mydatabase(UpdateActivity.this);
                myDB.deleteOneRow(id);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}