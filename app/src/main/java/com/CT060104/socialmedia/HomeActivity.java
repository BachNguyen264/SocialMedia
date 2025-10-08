package com.CT060104.socialmedia;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import Controller.CreatePost;
import Controller.GenerateTimeline;
import Controller.ReadUser;
import Model.Database;
import Model.Post;
import Model.PostsAdapter;
import Model.User;

public class HomeActivity extends AppCompatActivity {

    private User user;
    private Database database;
    private ListView posts_list;
    private String savedEmail;
    private String savedPassword;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ImageView modify = findViewById(R.id.modify);
        EditText post_edittext = findViewById(R.id.post_edittext);
        Button post = findViewById(R.id.post);
        posts_list = findViewById(R.id.posts_list);

        database = new Database();
        SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        savedEmail = sharedPreferences.getString("Email", "");
        savedPassword = sharedPreferences.getString("Password", "");

        modify.setOnClickListener(v-> {
            Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        post.setOnClickListener(v-> {
            String content = post_edittext.getText().toString();
            if (content.equals("") || content.equals(" ")) {
                Toast.makeText(this, "Cannot publish empty post", Toast.LENGTH_SHORT).show();
                return;
            }
            Post p = new Post(content, user);
            if (new CreatePost(p, database).isPosted()) {
                Toast.makeText(this, "Posted successfully", Toast.LENGTH_SHORT).show();
                post_edittext.setText("");
            }
        });

        refreshTimeline();

        //You can add a new button to refresh timeline behind settings btn for example but here
        // I will make it refresh onCreateand onResume (if you open settings and get back to home
        // or any other activity then you close it the posts will be refreshed

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume() {
        super.onResume();
        refreshTimeline();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void refreshTimeline() {
        user = new ReadUser(savedEmail, savedPassword, database).getUser();
        ArrayList<Post> posts = new GenerateTimeline(user, database).getPosts();
        posts_list.setAdapter(new PostsAdapter(posts, this, user, database));
    }

}