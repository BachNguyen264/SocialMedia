package com.CT060104.socialmedia;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ListView;

import java.util.ArrayList;

import Controller.ReadUser;
import Controller.ReadUserComments;
import Model.Comment;
import Model.Database;
import Model.Post;
import Model.PostCommentAdapter;
import Model.User;

public class UserComments extends AppCompatActivity {

    private ListView list;
    private Database database;
    private User user;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_comments);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        list = findViewById(R.id.list);

        database = new Database();

        SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        String savedEmail = sharedPreferences.getString("Email", "");
        String savedPassword = sharedPreferences.getString("Password", "");
        user = new ReadUser(savedEmail, savedPassword, database).getUser();

        refresh();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void refresh() {
        ReadUserComments readUserComments = new ReadUserComments(user, database);
        ArrayList<Post> posts = readUserComments.getPosts();
        ArrayList<Comment> comments = readUserComments.getComments();
        list.setAdapter(new PostCommentAdapter(posts, comments, this, user, database));
    }

}