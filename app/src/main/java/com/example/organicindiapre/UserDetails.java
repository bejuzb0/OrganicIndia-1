package com.example.organicindiapre;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.organicindiapre.customer.Customer_Act;
import com.example.organicindiapre.vendor.Vendor_Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserDetails extends AppCompatActivity {

    EditText firstName,lastName,email,password,conformPassword,address;
    Button saveBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    RadioGroup userType,userAdd;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.emailAddress);
        saveBtn = findViewById(R.id.saveBtn);
        password = findViewById(R.id.password);
        conformPassword = findViewById(R.id.conform_password);
        userType = findViewById(R.id.category);
        address = findViewById(R.id.address);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();


        if(getSelected().equals("Vendor"))
        {

        }



        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!firstName.getText().toString().isEmpty()&& !lastName.getText().toString().isEmpty() &&
                        !email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()
                        && !conformPassword.getText().toString().isEmpty() && !address.getText().toString().isEmpty()) {
                    if (password.getText().toString().equals(conformPassword.getText().toString()) && password.length() > 6)
                    {
                        final ProgressDialog progressDialog = new ProgressDialog(UserDetails.this);
                        progressDialog.show();
                        progressDialog.setMessage("Updating...");

                        DocumentReference docRef = fStore.collection("Users").document(userID);

                        Map<String,Object> user = new HashMap<>();
                        user.put("FirstName",firstName.getText().toString());
                        user.put("LastName",lastName.getText().toString());
                        user.put("Email",email.getText().toString());
                        user.put("Password",password.getText().toString());
                        user.put("MobileNumber",fAuth.getCurrentUser().getPhoneNumber());
                        user.put("UserType",getSelected());
                        user.put("Address",address.getText().toString());

                        docRef.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                {
                                    if (getSelected().equals("Vendor")){
                                        setVendor(true);
                                    }else {
                                        setVendor(false);
                                    }
                                    GotoActivity();
                                }else{
                                    Toast.makeText(UserDetails.this, "Some thing went out wrong", Toast.LENGTH_SHORT).show();
                                }
                                progressDialog.dismiss();
                            }
                        });

                    }
                    else {
                        Toast.makeText(UserDetails.this, "Password not match or less then 6", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(UserDetails.this, "Fill the required Details", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private String  getSelected()
    {
        int radioButtonID = userType.getCheckedRadioButtonId();
        View radioButton = userType.findViewById(radioButtonID);
        int id = userType.indexOfChild(radioButton);
        RadioButton RB = (RadioButton) userType.getChildAt(id);

        return RB.getText().toString();
    }

    private void setVendor(boolean vendor)
    {
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isVendor", vendor).apply();
    }

    private boolean getVendor()
    {
        return  getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isVendor", false);
    }

    private void GotoActivity()
    {
        if (getVendor()) {
            startActivity(new Intent(this,Vendor_Activity.class));
        } else {
            startActivity(new Intent(this, Customer_Act.class));
        }
    }

}
