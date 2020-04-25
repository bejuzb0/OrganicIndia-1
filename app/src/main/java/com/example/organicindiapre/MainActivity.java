package com.example.organicindiapre;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.organicindiapre.customer.Customer_Act;
import com.example.organicindiapre.vendor.Vendor_Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String TAG = "Phone Number Verification";
    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private boolean isCodeSent = false;
    private FirebaseUser user;
    public ProgressDialog progressDialog;
    public String phoneNoDetails;
    private DatabaseReference database;
    private LinearLayout statusLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_0);

        final EditText phoneNumber = findViewById(R.id.etName);
        final Button login = findViewById(R.id.button);
        final Button resendCode = findViewById(R.id.resend_button);
        final EditText verificationCode = findViewById(R.id.verification_code);
        final TextView Status = findViewById(R.id.status);


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        statusLayout = findViewById(R.id.status_layout);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        database = FirebaseDatabase.getInstance().getReference().child("User");

        resendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String number = phoneNumber.getText().toString();
                if (number.length() == 10)
                {
                    statusLayout.setVisibility(View.VISIBLE);
                    resendVerificationCode("+91"+number,mResendToken);
                }
                else Toast.makeText(MainActivity.this, "Enter valid number", Toast.LENGTH_SHORT).show();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setEnabled(false);
                String number = phoneNumber.getText().toString();
                phoneNoDetails = number;
                if (isCodeSent)
                {
                    String code = verificationCode.getText().toString();
                    if (code.length() >= 6) {
                        progressDialog.show();
                        progressDialog.setMessage("Checking... OTP");
                        verifyPhoneNumberWithCode(mVerificationId, code);
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Enter valid code", Toast.LENGTH_SHORT).show();
                    }
                }
                else if (number.length() == 10)
                {
                    statusLayout.setVisibility(View.VISIBLE);
                    startPhoneNumberVerification("+91"+number);
                }
                else{
                    Toast.makeText(MainActivity.this, "Enter valid number", Toast.LENGTH_SHORT).show();
                    login.setEnabled(true);
                }

            }
        });


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
        {

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential)
            {
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
                Toast.makeText(MainActivity.this, "Verification completed."+credential, Toast.LENGTH_SHORT).show();
                login.setEnabled(true);
                statusLayout.setVisibility(View.GONE);
                mVerificationInProgress = false;
                verificationCode.setText(credential.getSmsCode());
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException ex)
            {
                progressDialog.dismiss();
                login.setEnabled(true);
                mVerificationInProgress = false;
                Log.d("Firebase Exception", String.valueOf(ex));
                Toast.makeText(MainActivity.this, ""+ex, Toast.LENGTH_SHORT).show();
                if (ex instanceof FirebaseAuthInvalidCredentialsException)
                {
                    verificationCode.setError("Invalid phone number");
                }
                else if (ex instanceof FirebaseTooManyRequestsException)
                {
                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                            Snackbar.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, "Quota exceeded.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Enter valid code", Toast.LENGTH_SHORT).show();
                }

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token)
            {
                Toast.makeText(MainActivity.this, "OTP sent successfully", Toast.LENGTH_SHORT).show();
                login.setEnabled(true);
                verificationCode.setVisibility(View.VISIBLE);
                resendCode.setVisibility(View.VISIBLE);
                mVerificationId = verificationId;
                mResendToken = token;
                isCodeSent = true;
                login.setText("Verify otp");
                Status.setText("Waiting... for OTP");
            }
        };

    }


    public void openActivityVend(){
        Intent intent=new Intent(this, Vendor_Activity.class);
        startActivity(intent);
    }

    public void openActivityCust() {
        Intent intent = new Intent(this, Customer_Act.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (user != null)
        {
            GotActivity();
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            checkNewUser();
                        }
                        else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                            {
                                Toast.makeText(MainActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                                Log.d(TAG,"InValidDetails",task.getException());
                                statusLayout.setVisibility(View.GONE);
                                Toast.makeText(MainActivity.this, "Enter valid code", Toast.LENGTH_SHORT).show();
                            }
                        }

                        progressDialog.dismiss();
                    }
                });
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks);
        mVerificationInProgress = true;
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code)
    {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks,
                token);
    }

    private void getUserDetails()
    {

        final Dialog builder = new Dialog(this);
        builder.setCancelable(false);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.setContentView(R.layout.dialog_getuserdetails);

        Button vendor = builder.findViewById(R.id.Vendor);
        Button customer = builder.findViewById(R.id.Customer);
        vendor.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v)
            {
                setVendor(true);
                update("Vendor",builder, true);
            }
        });
        customer.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v)
            {
                setVendor(false);
                update("Customer",builder, false);
            }
        });
        builder.show();
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

    private void GotActivity()
    {
        if (getVendor()){
            openActivityVend();
        }
        else {
            openActivityCust();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void  checkNewUser()
    {
        try {
            database.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    progressDialog.dismiss();
                    try{
                        String userType =  dataSnapshot.child("UserType").getValue(String.class);
                        assert userType != null;
                        if (userType.equals("Vendor")) {
                                setVendor(true);
                            } else {
                                setVendor(false);
                            }
                            GotActivity();

                    }catch (Exception ex){
                        getUserDetails();

                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    progressDialog.dismiss();
                }
            });
        }
        catch (Exception ex)
        {
            Toast.makeText(this, ""+ex, Toast.LENGTH_SHORT).show();
            getUserDetails();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void update(String Type, final Dialog builder, boolean isVendor)
    {
        progressDialog.show();
        progressDialog.setMessage("Updating...");
        if(isVendor == false) {
            database.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("DeliveryAddress").setValue("Not Set");
            database.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("DeliveryAddressName").setValue("Not Set");
        }
        database.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("MobileNumber").setValue(phoneNoDetails);
        database.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("UserType").setValue(Type).addOnSuccessListener(new OnSuccessListener<Void>()
        {
            @Override
            public void onSuccess(Void aVoid)
            {
                progressDialog.dismiss();
                builder.dismiss();
                GotActivity();
            }
        });

    }

}
