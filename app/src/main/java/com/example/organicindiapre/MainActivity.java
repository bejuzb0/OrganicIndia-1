package com.example.organicindiapre;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.organicindiapre.customer.Customer_Act;
import com.example.organicindiapre.vendor.Vendor_Activity;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private Button Login;
    private TextView Sign;
   // private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_0);

        Sign=(TextView)findViewById(R.id.tvsign);
        Name=(EditText)findViewById(R.id.etName);
        Password=(EditText)findViewById(R.id.etPassword);
        Login=(Button)findViewById(R.id.button);

        Login.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View v) {openActivityVend();}
            @Override
            public void onClick(View v) {
                if (Name.getText().toString().equals("admin") && Password.getText().toString().equals("123")) {
                    openActivityVend();
                }else if(Name.getText().toString().equals("cust") && Password.getText().toString().equals("123")) {
                    openActivityCust();
                }
            }

        });


    }
    public void openActivityVend(){
        Intent intent=new Intent(this, Vendor_Activity.class);
        startActivity(intent);
    }

    public void openActivityCust() {
        Intent intent = new Intent(this, Customer_Act.class);
        startActivity(intent);
    }



}
