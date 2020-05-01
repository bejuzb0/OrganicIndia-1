package com.example.organicindiapre.customer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.organicindiapre.ProductAdapter;
import com.example.organicindiapre.ProductDetails;
import com.example.organicindiapre.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Delivery extends AppCompatActivity {

    private Button fromDate;
    private Button toDate;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    String Name,Address,MobileNumber,CustomerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

       Intent intent = getIntent();
       String ProductName = intent.getStringExtra("ProductName");
       String ProductPrice = intent.getStringExtra("Price");

       String ProductQuantity = intent.getStringExtra("Quantity");
       String Type = intent.getStringExtra("Type");
       String VendorId=intent.getStringExtra("VendorId");
       String ProductId=intent.getStringExtra("ProductId");
       String Cname=intent.getStringExtra("Name");
        String Address = intent.getStringExtra("Address");
        String CustomerId = intent.getStringExtra("CustomerId");
        String MobileNumber = intent.getStringExtra("MobileNumber");

        Log.d(TAG,"productname= "+ProductName
                +"\n  ProductPrice "+ProductPrice
                +"\n ProductId "+ProductId
                +"\n ProductQuantity "+ProductQuantity
                +"\n Type "+Type
                +"\n Address "+Address
                +"\nVendorId "+VendorId
                +"\n CustomerId "+CustomerId
                +"\n MobileNumber "+MobileNumber
                +"\n Cname "+Cname);



//        Intent cust_intent=getIntent();
//        String Name = cust_intent.getStringExtra("Name");
//        String Address = cust_intent.getStringExtra("Address");
//        String CustomerId = cust_intent.getStringExtra("CustomerId");
//        String MobileNumber = cust_intent.getStringExtra("MobileNumber");

//        fAuth = FirebaseAuth.getInstance();
//        fStore = FirebaseFirestore.getInstance();
//        DocumentReference docRef =fStore.collection("Users").document(fAuth.getCurrentUser().getUid());
//        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                if(documentSnapshot.exists()){
//                    Name = documentSnapshot.getString("FirstName") + " " + documentSnapshot.getString("LastName");
//                    Address = documentSnapshot.getString("Location");
//                    //mDA = documentSnapshot.getString("DeliveryAddress");
//                    MobileNumber = fAuth.getCurrentUser().getPhoneNumber();
//        CustomerId=fAuth.getCurrentUser().getUid();
//
//
//                }else {
//                    Log.d(TAG, "Retrieving Data:  Data Not Found ");
//                }
//            }
//        });
//        Log.d(TAG,"customer name "+Name+Address+" "+CustomerId+" "+MobileNumber);


        //Replace this with intent array and Product Details etc
        ArrayList<ProductDetails> productDetails = new ArrayList<>();

        RecyclerView ProductsRecycler = findViewById(R.id.Products_recycler);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        ProductsRecycler.setLayoutManager(linearLayout);

        final ProductAdapter ProductAdapter = new ProductAdapter(productDetails);
        ProductsRecycler.setHasFixedSize(true);
        ProductsRecycler.setAdapter(ProductAdapter);

        TextView onetime = findViewById(R.id.oneTimeDeliveryNote);
        LinearLayout subscriptionLayout = findViewById(R.id.Subscription_layout);
        fromDate = findViewById(R.id.from_button);
        toDate = findViewById(R.id.to_button);
        Button aContinue = findViewById(R.id.done);

        fromDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v)
            {
                DatePicker(fromDate);
            }
        });

        toDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v)
            {
                DatePicker(toDate);
            }
        });

        aContinue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(Delivery.this, "Start delivery", Toast.LENGTH_SHORT).show();
            }
        });


        //productname.setText(ProductName);
        //productprice.setText(ProductPrice);
        //productquantity.setText(ProductQuantity);

        /*
        Getting type of order is one type or subscription
         */
        if (!Type.equals("One Time"))
        {
            onetime.setVisibility(View.GONE);
        }
        else {
            subscriptionLayout.setVisibility(View.GONE);
        }



    }




    //Date picker for selecting from date to to date
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void DatePicker(final Button button)
    {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                    {
                        button.setText(dayOfMonth+"/"+month+"/"+year);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}
