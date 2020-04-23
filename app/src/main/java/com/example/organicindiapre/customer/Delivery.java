package com.example.organicindiapre.customer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.organicindiapre.R;
import java.util.Calendar;
import java.util.TimeZone;

public class Delivery extends AppCompatActivity {

    private Button fromDate;
    private Button toDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        Intent intent = getIntent();
       String ProductName = intent.getStringExtra("PN");
       String ProductPrice = intent.getStringExtra("PP");
       String ProductQuantity = intent.getStringExtra("PQ");
       String Type = intent.getStringExtra("Type");

        TextView productname = findViewById(R.id.product_name);
        TextView productprice = findViewById(R.id.product_price);
        TextView productquantity = findViewById(R.id.product_quantity);
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


        productname.setText(ProductName);
        productprice.setText(ProductPrice);
        productquantity.setText(ProductQuantity);

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
