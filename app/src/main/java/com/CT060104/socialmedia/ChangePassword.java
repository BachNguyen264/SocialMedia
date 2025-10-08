package com.CT060104.socialmedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Model.Database;

public class ChangePassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        EditText old_password = findViewById(R.id.old_password);
        EditText password = findViewById(R.id.password);
        EditText confirm_password = findViewById(R.id.confirm_password);
        Button submit = findViewById(R.id.submit);

        Database database = new Database();

        SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        int userID = sharedPreferences.getInt("ID", -1);
        String savedPassword = sharedPreferences.getString("Password", "");

        submit.setOnClickListener(v-> {
            if (old_password.getText().toString().equals("")) {
                old_password.setError("Please enter your old password");
                return;
            }
            if (!old_password.getText().toString().equals(savedPassword)) {
                old_password.setError("Old Password doesn't match");
                return;
            }
            if (password.getText().toString().equals("")) {
                password.setError("Please enter your new password");
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

            Controller.ChangePassword changePassword = new Controller.ChangePassword(
                    password.getText().toString(), userID, database);
            if (changePassword.isChanged()) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Password", password.getText().toString());
                editor.apply();
                Toast.makeText(this, "Password changed successfully",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}