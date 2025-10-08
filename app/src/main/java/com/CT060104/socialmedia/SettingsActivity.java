package com.CT060104.socialmedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.LinearLayout;
import android.widget.TextView;

import Controller.ReadUser;
import Model.Database;
import Model.User;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        LinearLayout profile = findViewById(R.id.profile);
        LinearLayout posts = findViewById(R.id.posts);
        LinearLayout comments = findViewById(R.id.post_comments);
        LinearLayout likes = findViewById(R.id.post_likes);
        LinearLayout friends = findViewById(R.id.friends);
        LinearLayout password = findViewById(R.id.password);
        LinearLayout logout = findViewById(R.id.logout);

        TextView user_name = findViewById(R.id.user_name);

        Database database = new Database();
        SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        String savedEmail = sharedPreferences.getString("Email", "");
        String savedPassword = sharedPreferences.getString("Password", "");
        User user = new ReadUser(savedEmail, savedPassword, database).getUser();

        user_name.setText(user.getName());

        profile.setOnClickListener(v-> {
            Intent intent = new Intent(SettingsActivity.this, ModifyProfile.class);
            startActivity(intent);
        });

        posts.setOnClickListener(v-> {
            Intent intent = new Intent(SettingsActivity.this, UserPosts.class);
            startActivity(intent);
        });

        comments.setOnClickListener(v-> {
            Intent intent = new Intent(SettingsActivity.this, UserComments.class);
            startActivity(intent);
        });

        likes.setOnClickListener(v-> {
            Intent intent = new Intent(SettingsActivity.this, UserLikes.class);
            startActivity(intent);
        });

        friends.setOnClickListener(v-> {
            Intent intent = new Intent(SettingsActivity.this, FriendsActivity.class);
            startActivity(intent);
        });

        password.setOnClickListener(v-> {
            Intent intent = new Intent(SettingsActivity.this, ChangePassword.class);
            startActivity(intent);
        });

        logout.setOnClickListener(v-> {
            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (sharedPreferences.contains("Email")) editor.remove("Email");
            if (sharedPreferences.contains("Password")) editor.remove("Password");
            if (sharedPreferences.contains("ID")) editor.remove("ID");
            editor.apply();
            startActivity(intent);
            finish();
        });

    }
}