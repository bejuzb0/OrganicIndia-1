package com.example.organicindiapre;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class Admin extends AppCompatActivity {

    private String VendorName,VendorNumber,VendorUID,CustomerUID,ProductID,CustomerName,CustomerNumber,ProductName,ProductPrice,ProductQuantity;
    private String Delivered;
    private FirebaseFirestore DB;
    private ArrayList<ReportAdminHolder> AdminOrderArrayList;
    private RecyclerView AdminOrderRecycler, AdminRevenue;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        final ArrayList<ReportAdminHolder> AdminReportArrayList = new ArrayList<>();
         AdminOrderArrayList = new ArrayList<>();


        AdminRevenue = findViewById(R.id.admin_revenue);
        LinearLayoutManager Layout = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        AdminRevenue.setLayoutManager(Layout);

        AdminOrderRecycler = findViewById(R.id.admin_orders_recycler);
        LinearLayoutManager orderLayout = new LinearLayoutManager(this);
        AdminOrderRecycler.setLayoutManager(orderLayout);


        DB = FirebaseFirestore.getInstance();
        final CollectionReference ReportAdminRef = DB.collection("Reports");
        final CollectionReference OrderAdminRef = DB.collection("Orders_OneTime");

        OrderAdminRef
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if (task.isSuccessful())
                        {
                            for (QueryDocumentSnapshot snapshot : Objects.requireNonNull(task.getResult()))
                            {
                                if (snapshot.exists())
                                {
                                    VendorUID = snapshot.get("VendorID").toString();
                                     CustomerUID = snapshot.get("CustomerID").toString();
                                    ProductID = snapshot.get("ProductID").toString();
                                    ProductQuantity = snapshot.get("Quantity").toString();
                                    //methods for getting data
                                    VendorDetails();
                                    CustomerDetails();;
                                    ProductDetails();
                                }
                            }
                        }
                    }
                });

        ReportAdminRef
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful())
                        {
                            for (final QueryDocumentSnapshot snapshot : Objects.requireNonNull(task.getResult()))
                            {
                                if (snapshot.exists())
                                {
                                    Toast.makeText(Admin.this, ""+snapshot.getId(), Toast.LENGTH_SHORT).show();
                                    String VendorUID = Objects.requireNonNull(snapshot.get("VendorUID")).toString();
                                    final String Revenue = Objects.requireNonNull(snapshot.get("Revenue")).toString();
                                    DB.collection("Users")
                                            .document(VendorUID)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task)
                                                {
                                                    DocumentSnapshot snapshot1 = task.getResult();
                                                    assert snapshot1 != null;
                                                    String VendorName = Objects.requireNonNull(snapshot1.get("FirstName")).toString();
                                                    String PhoneNumber = Objects.requireNonNull(snapshot1.get("MobileNumber")).toString();
                                                    ReportAdminHolder adminHolder = new ReportAdminHolder(VendorName,PhoneNumber,Revenue);
                                                    AdminReportArrayList.add(adminHolder);
                                                    AdminRevenueAdapter adminRevenueAdapter = new AdminRevenueAdapter(AdminReportArrayList);
                                                    AdminRevenue.setAdapter(adminRevenueAdapter) ;
                                                    AdminRevenue.setHasFixedSize(true);
                                                }
                                            });
                                }

                            }

                        }

                    }
                });




    }

    private void VendorDetails(){
        DB.collection("Users")
                .document(VendorUID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful())
                        {
                            DocumentSnapshot vendorSnap = task.getResult();
                            assert vendorSnap != null;
                            VendorName  = vendorSnap.get("FirstName").toString();
                            VendorNumber = vendorSnap.get("MobileNumber").toString();
                        }
                    }
                });

    }

    private void CustomerDetails()
    {
        DB.collection("Users")
                .document(CustomerUID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful())
                        {
                            DocumentSnapshot vendorSnap = task.getResult();
                            assert vendorSnap != null;
                            CustomerName  = vendorSnap.get("FirstName").toString();
                            CustomerNumber = vendorSnap.get("MobileNumber").toString();
                        }
                    }
                });

    }

    private void ProductDetails(){
        DB.collection("Products")
                .document(ProductID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful())
                        {

                            DocumentSnapshot productSnap = task.getResult();
                            ProductName = productSnap.get("ProductName").toString();
                            ProductPrice = productSnap.get("Rate").toString();
                            AdminOrderArrayList.add(new ReportAdminHolder(VendorName,CustomerName,ProductName,ProductPrice,ProductQuantity));
                        }
                        AdminOrdersAdapter adminOrdersAdapter = new AdminOrdersAdapter(AdminOrderArrayList);
                        AdminOrderRecycler.setAdapter(adminOrdersAdapter);
                        AdminOrderRecycler.setHasFixedSize(true);
                    }
                });
    }

    public static class AdminRevenueAdapter extends RecyclerView.Adapter<AdminRevenueAdapter.ViewHolder>{

        ArrayList<ReportAdminHolder> reportAdminHolders;

        AdminRevenueAdapter(ArrayList<ReportAdminHolder> reportAdminHolders) {
            this.reportAdminHolders = reportAdminHolders;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reports_amount_revenue_items,parent,false);
            return new ViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position)
        {
            holder.Name.setText("Name : "+reportAdminHolders.get(position).getVendorName());
            holder.Revenue.setText("Revenue : "+reportAdminHolders.get(position).getRevenue());
            holder.Phone.setText("Contact : "+reportAdminHolders.get(position).getVendorPhoneNumber());
        }

        @Override
        public int getItemCount() {
            return reportAdminHolders.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView Name ,Phone ,Revenue;
            ViewHolder(@NonNull View itemView) {
                super(itemView);
                Revenue = itemView.findViewById(R.id.customer_name);
                Phone = itemView.findViewById(R.id.revenue);
                Name = itemView.findViewById(R.id.amount);

            }
        }
    }


    public static class AdminOrdersAdapter extends RecyclerView.Adapter<AdminOrdersAdapter.ViewHolder>{

        ArrayList<ReportAdminHolder> adminHolders;

        AdminOrdersAdapter(ArrayList<ReportAdminHolder> adminHolders) {
            this.adminHolders = adminHolders;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_report_orders_items,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position)
        {
            holder.VendorName.setText("Vendor Name : "+adminHolders.get(position).getVendorName());
            holder.CustomerName.setText("Customer Name : "+adminHolders.get(position).getCustomerName());
            holder.Product.setText(adminHolders.get(position).getProductName());
            holder.Quantity.setText(adminHolders.get(position).getProductQuantity());
            holder.Price.setText(adminHolders.get(position).getProductPrice());
        }

        @Override
        public int getItemCount() {
            return adminHolders.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView CustomerName,VendorName,Product,Price,Quantity;
            ViewHolder(@NonNull View itemView)
            {
                super(itemView);
                CustomerName = itemView.findViewById(R.id.customer_name);
                VendorName = itemView.findViewById(R.id.vendor_name);
                Product = itemView.findViewById(R.id.product_name);
                Price = itemView.findViewById(R.id.product_price);
                Quantity = itemView.findViewById(R.id.product_quantity);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
