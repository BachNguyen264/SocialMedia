package com.CT060104.socialmedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Controller.ReadUser;
import Model.Database;
import Model.User;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        Button login = findViewById(R.id.login);
        TextView create_account = findViewById(R.id.create_account);

        Database database = new Database();

        login.setOnClickListener(v-> {
            if (email.getText().toString().equals("")) {
                email.setError("Email cannot be empty");
                return;
            }
            if (password.getText().toString().equals("")) {
                password.setError("Password cannot be empty");
                return;
            }
            ReadUser readUser = new ReadUser(email.getText().toString(),
                    password.getText().toString(), database);
            if (readUser.isLoggedIn()) {
                User user = readUser.getUser();
                //Update data in sharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Email", user.getEmail());
                editor.putString("Password", user.getPassword());
                editor.putInt("ID", user.getID());
                editor.apply();
                //Go to home
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Incorrect email or password",
                        Toast.LENGTH_SHORT).show();
            }
        });

        create_account.setOnClickListener(v-> {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

    }
}