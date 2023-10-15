package com.IOVIS.datavault;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.IOVIS.datavault.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    Mydatabase myDb;
    ArrayList<String> id,name,phone,dob,email;
    CustomAdapter customAdapter;
    ImageView empty_img;
    TextView no_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        onCreate method in an Android Activity class, which is the entry point for setting up
//        the user interface and initializing various components when the activity is created.
        super.onCreate(savedInstanceState);
//        this performs necessary initialization
        setContentView(R.layout.activity_main);
//        Code in this method belongs to activity_mani.xml
        recyclerView = findViewById(R.id.recycleView);
//        R java file contains all resources & ids so that we can connect
        floatingActionButton = findViewById(R.id.floatingActionButton);
        empty_img = findViewById(R.id.empty_img);
        no_data = findViewById(R.id.no_data);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //After clicking on  floatingActionButton it will lead us to new page add details
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                //The Intent class in Android is a fundamental component used for communication between
                // different parts of an Android application, as well as between different applications.
                startActivity(intent);
            }
        });

        //Initializing arraylists to store data
        myDb = new Mydatabase(MainActivity.this);
        id = new ArrayList<>(); // increments automatically
        name = new ArrayList<>();
        phone = new ArrayList<>();
        dob = new ArrayList<>();
        email = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(MainActivity.this,this,id,name,phone,dob,email);
        recyclerView.setAdapter(customAdapter);
//        Storing the data in arrays
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//        viewing the same data in MainActivity
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }
    void storeDataInArrays() {
        Cursor cursor = myDb.readAllData();
        if (cursor.getCount() == 0){
//            checks data in cursor
            empty_img.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()) {
                id.add(cursor.getString(0));
                name.add(cursor.getString(1));
                phone.add(cursor.getString(2));
                dob.add(cursor.getString(3));
                email.add(cursor.getString(4));
            }
            empty_img.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //The onCreate method in AppCompatActivity is an important lifecycle method that gets called when the activity is first created.
        // You override this method to perform initialization and setup for your activity.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.delete_all){
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you want to delete all Data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Mydatabase myDB = new Mydatabase(MainActivity.this);
                myDB.deleteAllData();
                //Refresh Activity
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
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