package com.example.organicindiapre.customer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.organicindiapre.List;
import com.example.organicindiapre.ProductDetails;
import com.example.organicindiapre.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
       First adapter for both Supplier and Product . main items like Vendor names,logo or Product names
    */

public class ProductsAdapters extends RecyclerView.Adapter<ProductsAdapters.viewHolder>
{

    private ArrayList<List> list;
    private Context context;
    private String fragmentName;
    private String vendorUID;
    private ArrayList SelectedList = new ArrayList<>();
    VendorProductAdapter vendorProductAdapter;

    ArrayList getSelectedList() {
        return SelectedList;
    }
    FirebaseFirestore db;

    ProductsAdapters(ArrayList<List> list, Context context, String fragmentName) {
        this.list = list;
        this.context = context;
        this.fragmentName = fragmentName;
        db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_vendor_list,parent,false);
        return new viewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, final int position) {


        final boolean[] isExpended = {false};
        holder.Name.setText(list.get(position).getVendorName());
        holder.vendorLogo.setImageResource(list.get(position).getVendorLogo());
        holder.vendorProductList.setHasFixedSize(true);
        holder.vendorProductList.setLayoutManager(new LinearLayoutManager(context));

        //getting data for each product under vendor or product
        final ArrayList<ProductDetails> productDetails = new ArrayList<ProductDetails>();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (fragmentName.equals("supplier"))
        {
            //data for supplies
            holder.Title.setText("Product");
            // ProductDetails products =  new ProductDetails("milk","hbhk","jhbgre");
            // lis.add(products);


            db.collection("Users").document(list.get(position).getVendorID()).collection("Products")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Map<String, Object> m = document.getData();
                                    Log.d(TAG, list.get(position).getVendorID());
                                    productDetails.add(new ProductDetails(m.get("ProductName").toString(),Integer.parseInt(m.get("Quantity").toString()), Integer.parseInt(m.get("Rate").toString()), 1,list.get(position).getVendorID()));
                                    Log.d(TAG, "DocumentSnapshot product: " + document.getData());
                                }
                                vendorProductAdapter = new VendorProductAdapter(productDetails);
                                holder.vendorProductList.setAdapter(vendorProductAdapter);
                                vendorProductAdapter.notifyDataSetChanged();
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });
        }
        else {
            //data for products
            holder.Title.setText("Vendor");
        }

        holder.itemLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (isExpended[0])
                {
                    holder.detailsLayout.setVisibility(View.GONE);
                    holder.vendorProductList.setVisibility(View.GONE);
                    isExpended[0]=false;
                }
                else
                    {
                    holder.vendorProductList.setVisibility(View.VISIBLE);
                    holder.detailsLayout.setVisibility(View.VISIBLE);
                    isExpended[0] = true;
                       /* ArrayList<ProductDetails> lis = new ArrayList<>();
                       for (int i = 0;i<10;i++) {
                            List list_cust = new List("vendor name "+i, R.drawable.ic_logout);
                            lis.add(list_cust);
                        }
                        */
                       // SelectedItem = position;
                      // notifyDataSetChanged();
                }

            }
        });

/*
        if (SelectedItem != position)
        {
            holder.vendorLogo.setAlpha(0.5f);
            holder.detailsLayout.setVisibility(View.GONE);
            holder.vendorProductList.setVisibility(View.GONE);
        }
        else
            {
            holder.vendorLogo.setAlpha(0.9f);
            holder.vendorProductList.setVisibility(View.VISIBLE);
            holder.detailsLayout.setVisibility(View.VISIBLE);
        }
 */

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class viewHolder extends RecyclerView.ViewHolder{

        TextView Name,Title;
        ImageView vendorLogo;
        RecyclerView vendorProductList;
        RelativeLayout itemLayout,detailsLayout;

        viewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.name);
            vendorLogo = itemView.findViewById(R.id.vendor_logo);
            itemLayout = itemView.findViewById(R.id.item_layout);
            vendorProductList = itemView.findViewById(R.id.vendor_product_list);
            detailsLayout = itemView.findViewById(R.id.details_layout);
            Title = itemView.findViewById(R.id.name_of_recycler_item);
        }
    }



    /**
        Second adapter for both one time and subscription for child items
     */

    public class VendorProductAdapter extends RecyclerView.Adapter<VendorProductAdapter.viewHolder>{

        private ArrayList<ProductDetails> productDetails;

       // private ArrayList<ProductDetails> productDetails;

        VendorProductAdapter(ArrayList<ProductDetails> productDetails) {
            this.productDetails = productDetails;
        }

        @NonNull
        @Override
        public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cust,parent,false);
            return new viewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull final viewHolder holder, final int position)
        {
            //if(productDetails.get(position) != null) {
                final String Name = productDetails.get(position).getName();
                final int Price = productDetails.get(position).getProductPrice();
                final int Quantity = productDetails.get(position).getProductQuantity();
                final int MinPackingQuantity = productDetails.get(position).getMinPackingQuantity();

                //final String QuantityType = productDetails[position].getQuantityType();
                holder.Name.setText(Name);
                holder.Price.setText(Price + "/" + MinPackingQuantity);
                holder.Quantity.setText(Quantity + "");


                holder.Add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.Quantity.setText(Count(holder.Quantity.getText().toString(), MinPackingQuantity));
                    }
                });
                holder.Remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.Quantity.setText(Count(holder.Quantity.getText().toString(), -MinPackingQuantity));

            holder.Checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                    {
                        //Replace all this with ProductUID to add list
                        SelectedList.add(0,Name);
                        SelectedList.add(1,holder.Quantity.getText().toString());
                        SelectedList.add(2,Price+" ");
                    }
                    else {
                        //Remove ProductUID from list
                        SelectedList.remove(1);
                       SelectedList.remove(Name);
                       SelectedList.remove(Price+" ");

                    }
                });
                holder.Checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            SelectedList.add(0, Name);
                            SelectedList.add(1, holder.Quantity.getText().toString());
                            SelectedList.add(2, Price + " ");
                        } else {
                            SelectedList.remove(1);
                            SelectedList.remove(Name);
                            SelectedList.remove(Price + " ");
                        }
                        // productDetails.setOrderList(SelectedList);
                    }
                });
            }

       // }

        private String Count(String  Count, int Value)
        {
            int count = Integer.parseInt(Count.replace("ml","").trim());
            count = count + Value;
            if (!(count <= Value || count <= 0))
            {
                return String.valueOf(count);
            }
            else {
                return String.valueOf(Value).replace("-","");
            }
        }

        @Override
        public int getItemCount() {
            return productDetails.size();
        }

        class viewHolder extends RecyclerView.ViewHolder{
            TextView Name,Quantity,Price;
            ImageButton Add,Remove;
            CheckBox Checkbox;
            viewHolder(@NonNull View itemView) {
                super(itemView);
                Name = itemView.findViewById(R.id.name);
                Quantity = itemView.findViewById(R.id.quantity_count);
                Price = itemView.findViewById(R.id.product_price);
                Add = itemView.findViewById(R.id.add);
                Remove = itemView.findViewById(R.id.remove);
                Checkbox = itemView.findViewById(R.id.checkbox);

            }
        }



    }
}

