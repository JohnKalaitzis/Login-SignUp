package com.example.mysignupapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    EditText EditTextFullname, EditTextUsername, EditTextPassword, EditTextEmail;
    Button buttonSignUp;
    TextView TextViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditTextFullname = findViewById(R.id.fullname);
        EditTextUsername = findViewById(R.id.username);
        EditTextPassword = findViewById(R.id.password);
        EditTextEmail = findViewById(R.id.email);
        buttonSignUp = findViewById(R.id.signupbtn);
        TextViewLogin = findViewById(R.id.already);

        TextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class );
                startActivity(intent);
                finish();
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String fullname, username, password, email;
                        fullname = String.valueOf(EditTextFullname.getText());
                        username = String.valueOf(EditTextUsername.getText());
                        password = EditTextPassword.getText().toString();
                        email = String.valueOf(EditTextEmail.getText());

                        if (!fullname.equals("") && !username.equals("") && !password.equals("") && !email.equals("")) {
                            //Creating array for parameters
                            String[] field = new String[4];
                            field[0] = "fullname";
                            field[1] = "username";
                            field[2] = "password";
                            field[3] = "email";
                            //Creating array for data
                            String[] data = new String[4];
                            data[0] = fullname;
                            data[1] = username;
                            data[2] = password;
                            data[3] = email;
                            //localhost-->Ip
                            PutData putData = new PutData("http://localhost/LoginRegister/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if(result.equals("Sign Up Success")){
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(getApplicationContext(), Login.class );
                                        startActivity(intent);
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
    });
}
}