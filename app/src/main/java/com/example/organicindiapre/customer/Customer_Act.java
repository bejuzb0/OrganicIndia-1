package com.example.organicindiapre.customer;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.organicindiapre.MainActivity;
import com.example.organicindiapre.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class Customer_Act extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();


        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState== null) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_customer,
                    new CustomerFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home_customer);
        }



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {

            case R.id.nav_profile_customer:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_customer,
                        new CustomerProfileFragment()).commit();
                break;

            case R.id.nav_home_customer:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_customer,
                        new CustomerFragment()).commit();
                break;

            case R.id.nav_order_customer:
                Toast.makeText(this, "Order Clicked", Toast.LENGTH_SHORT).show();

                break;
            case R.id.nav_subscription_customer:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_customer,
                        new CustomerSubscriptionFragment()).commit();
                break;

            case R.id.nav_logout_customer:
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(this, MainActivity.class);
                startActivity(intent);
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity();
            }
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mn = getMenuInflater();
        mn.inflate(R.menu.main_menu_0,menu);
        return (true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.location:
                Toast.makeText(this, "Location Clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.notification:
                Toast.makeText(this, "Notification Clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.search:

                return true;
        }
        return true;
    }
}
