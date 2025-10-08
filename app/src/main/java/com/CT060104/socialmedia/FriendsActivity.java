package com.CT060104.socialmedia;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import Controller.ReadAllUsers;
import Controller.ReadUser;
import Model.Database;
import Model.FriendsAdapter;
import Model.User;

public class FriendsActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_user_list);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        TextView title = findViewById(R.id.title);
        title.setText("Friends");
        ListView list = findViewById(R.id.posts_list);

        Database database = new Database();

        SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        String savedEmail = sharedPreferences.getString("Email", "");
        String savedPassword = sharedPreferences.getString("Password", "");
        User user = new ReadUser(savedEmail, savedPassword, database).getUser();

        ArrayList<User> users = new ReadAllUsers(database, user).getList();
        list.setAdapter(new FriendsAdapter(users, this, user, database));

    }
}