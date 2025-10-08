package com.CT060104.socialmedia;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import Controller.ReadUser;
import Controller.ReadUserLikes;
import Model.Database;
import Model.Post;
import Model.PostsAdapter;
import Model.User;

public class UserLikes extends AppCompatActivity {

    private ListView posts_list;
    private Database database;
    private User user;

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_user_list);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        TextView title = findViewById(R.id.title);
        title.setText("Likes");
        posts_list = findViewById(R.id.posts_list);

        database = new Database();

        SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        String savedEmail = sharedPreferences.getString("Email", "");
        String savedPassword = sharedPreferences.getString("Password", "");
        user = new ReadUser(savedEmail, savedPassword, database).getUser();

        refresh();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void refresh() {
        ArrayList<Post> posts = new ReadUserLikes(user, database).getPosts();
        posts_list.setAdapter(new PostsAdapter(posts, this, user, database));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }
}