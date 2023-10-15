package com.IOVIS.datavault;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.IOVIS.datavault.R;

public class AddActivity extends AppCompatActivity {
    EditText edit_name,edit_number,edit_dob,edit_email;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        edit_name = findViewById(R.id.edit_name);
        edit_number = findViewById(R.id.edit_number);
        edit_dob = findViewById(R.id.edit_dob);
        edit_email = findViewById(R.id.edit_email);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mydatabase myDb = new Mydatabase(AddActivity.this);
                myDb.add_details(edit_name.getText().toString(),
                        edit_number.getText().toString()
                        ,edit_dob.getText().toString(),edit_email.getText().toString());
            }
        });
    }
}