package com.CT060104.socialmedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import Controller.CreateUser;
import Controller.ReadUser;
import Model.Database;
import Model.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        EditText first_name = findViewById(R.id.first_name);
        EditText last_name = findViewById(R.id.last_name);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        EditText confirm_password = findViewById(R.id.confirm_password);
        Button create_account = findViewById(R.id.create_account);
        TextView login = findViewById(R.id.login);

        Database database = new Database();

        SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        if (sharedPreferences.contains("Email") && sharedPreferences.contains("Password")) {
            String savedEmail = sharedPreferences.getString("Email", "");
            String savedPassword = sharedPreferences.getString("Password", "");
            ReadUser readUser = new ReadUser(savedEmail, savedPassword, database);
            if (readUser.isLoggedIn()) {
                //Update Data (ID)
                User currentUser = readUser.getUser();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("ID", currentUser.getID());
                editor.apply();
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }

        create_account.setOnClickListener(v-> {
            if (first_name.getText().toString().equals("")) {
                first_name.setError("First Name cannot be empty");
                return;
            }
            if (last_name.getText().toString().equals("")) {
                last_name.setError("Last Name cannot be empty");
                return;
            }
            if (email.getText().toString().equals("")) {
                email.setError("Email cannot be empty");
                return;
            }
            if (password.getText().toString().equals("")) {
                password.setError("Password cannot be empty");
                return;
            }
            if (password.getText().toString().length()<6) {
                password.setError("Password must contains at least 6 characters");
                return;
            }
            if (confirm_password.getText().toString().equals("")) {
                confirm_password.setError("Please confirm your password");
                return;
            }
            if (!password.getText().toString().equals(confirm_password.getText().toString())) {
                confirm_password.setError("Password doesn't match");
                return;
            }
            User u = new User();
            u.setFirstName(first_name.getText().toString());
            u.setLastName(last_name.getText().toString());
            u.setEmail(email.getText().toString());
            u.setPassword(password.getText().toString());
            CreateUser createUser = new CreateUser(u, database);
            if (!createUser.isEmailUsed()) {
                createUser.create();
                User user = createUser.getUser();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Email", user.getEmail());
                editor.putString("Password", user.getPassword());
                editor.putInt("ID", user.getID());
                editor.apply();
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                email.setError("This email has been used before");
            }
        });

        login.setOnClickListener(v-> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

    }
}