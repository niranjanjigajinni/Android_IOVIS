package com.IOVIS.datavault;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.IOVIS.datavault.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context; // Has all database keys and relatable datatype
    private Activity activity;
    private ArrayList id, name, phone, dob, email;
    Animation translate_anim;
    CustomAdapter(Activity activity,Context context, ArrayList id, ArrayList name, ArrayList phone,
                  ArrayList dob, ArrayList email){
        this.activity = activity;
        this.context = context;
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.dob = dob;
        this.email = email;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        method is responsible for inflating the layout for individual items in
//        the RecyclerView and creating a new ViewHolder to hold those views
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent ,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
//        Here we are getting data or string from arraylists
        holder.text_id.setText(String.valueOf(id.get(position)));
        holder.text_name.setText(String.valueOf(name.get(position)));
        holder.text_phone.setText(String.valueOf(phone.get(position)));
        holder.text_dob.setText(String.valueOf(dob.get(position)));
        holder.text_email.setText(String.valueOf(email.get(position)));

//        to update data we are setting OnClickListener
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//         When we click on OnClickListener then the Main_activity content is transferred to Update_activity
                Intent intent = new Intent(context,(UpdateActivity.class));
                intent.putExtra("id",String.valueOf(id.get(position)));
                intent.putExtra("name",String.valueOf(name.get(position)));
                intent.putExtra("phone",String.valueOf(phone.get(position)));
                intent.putExtra("dob",String.valueOf(dob.get(position)));
                intent.putExtra("email",String.valueOf(email.get(position)));

                activity.startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text_id,text_name,text_phone,text_dob,text_email;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
//            itemView is obj of class View so we are reading data from my_row.xml
//            hence we are establishing connection between my_row and recycle_view
//            as following widgets are present in my_row we need to use itemView
            text_id = itemView.findViewById(R.id.text_id);
            text_name = itemView.findViewById(R.id.text_name);
            text_phone = itemView.findViewById(R.id.text_phone);
            text_dob = itemView.findViewById(R.id.text_dob);
            text_email = itemView.findViewById(R.id.text_email);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            //Animate Recyclerview
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);

        }
    }
}
