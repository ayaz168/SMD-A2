package com.ayazafzal.i170014_i170161;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp_Two extends AppCompatActivity {
    EditText newEmailScreenTwo,newPasswordScreenTwo,newcPasswordScreenTwo;
    Button signUpScreenTwo;
    TextView logInScreenTwo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_two);
        newEmailScreenTwo=findViewById(R.id.newEmailScreenTwo);
        newPasswordScreenTwo=findViewById(R.id.newPasswordScreenTwo);
        newcPasswordScreenTwo=findViewById(R.id.newcPasswordScreenTwo);
        signUpScreenTwo=findViewById(R.id.signUpScreenTwo);
        logInScreenTwo=findViewById(R.id.logInScreenTwo);
        logInScreenTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inX=new Intent(SignUp_Two.this,LogIn_Three.class);
                startActivity(inX);
            }
        });
        signUpScreenTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!newEmailScreenTwo.getText().toString().isEmpty()){
                    if(newPasswordScreenTwo.getText().toString().equals(newPasswordScreenTwo.getText().toString())){
                        Intent inX=new Intent(SignUp_Two.this,ProfileScreen_Six.class);
                        inX.putExtra("Email",newEmailScreenTwo.getText().toString());
                        inX.putExtra("Password",newPasswordScreenTwo.getText().toString());
                        startActivity(inX);
                    }else{
                        Toast.makeText(SignUp_Two.this,
                                "Passwords don't match!",
                                Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(SignUp_Two.this,
                            "Please Enter Email !",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}