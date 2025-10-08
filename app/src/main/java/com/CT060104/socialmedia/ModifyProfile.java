package com.CT060104.socialmedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Controller.ReadUser;
import Controller.UpdateUser;
import Model.Database;
import Model.User;

public class ModifyProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_profile);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        EditText first_name = findViewById(R.id.first_name);
        EditText last_name = findViewById(R.id.last_name);
        EditText email = findViewById(R.id.email);
        Button submit = findViewById(R.id.submit);

        Database database = new Database();

        SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        String savedEmail = sharedPreferences.getString("Email", "");
        String savedPassword = sharedPreferences.getString("Password", "");
        User user = new ReadUser(savedEmail, savedPassword, database).getUser();

        first_name.setText(user.getFirstName());
        last_name.setText(user.getLastName());
        email.setText(user.getEmail());

        submit.setOnClickListener(v-> {
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

            user.setFirstName(first_name.getText().toString());
            user.setLastName(last_name.getText().toString());
            user.setEmail(email.getText().toString());
            UpdateUser updateUser = new UpdateUser(user, database);

            // First condition to check if it is another email (the initial email will return true
            // (for usedEmail) so here we check if the user changed it and the new one is used
            if (!email.getText().toString().equals(savedEmail) && updateUser.isEmailUsed()) {
                email.setError("This email has been used before");
                return;
            }

            if (updateUser.update()) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Email", email.getText().toString());
                editor.apply();
                Toast.makeText(this, "Profile updated successfully",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}