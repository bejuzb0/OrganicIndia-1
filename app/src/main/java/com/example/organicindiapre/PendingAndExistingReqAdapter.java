package com.example.organicindiapre;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class PendingAndExistingReqAdapter extends RecyclerView.Adapter<PendingAndExistingReqAdapter.viewholder>{

    private CustomerDetails[] customerDetails;
    private String fragmentName;
    private ArrayList SelectedList = new ArrayList();
    private Context context;
    ArrayList getSelectedList() {
        return SelectedList;
    }


    PendingAndExistingReqAdapter(CustomerDetails[] customerDetails, String  fragmentName,Context context)
     {
        this.customerDetails = customerDetails;
        this.fragmentName = fragmentName;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_subscription,parent,false);
        return new viewholder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull viewholder holder, final int position)
    {
        holder.Cname.setText(customerDetails[position].getCustomerName());
        holder.Cadd.setText(customerDetails[position].getCustomerAddress()+" : "+customerDetails[position].getCustomerPhoneNumber());
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // ProductDetails productDetails = new ProductDetails();
                if (isChecked)
                {
                    SelectedList.add(customerDetails[position].getCustomerName());
                }
                else {
                    SelectedList.remove(customerDetails[position].getCustomerName());
                }
                // productDetails.setOrderList(SelectedList);
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ArrayList editcoustomer = new ArrayList<>();
             //   editcoustomer.add(new CustomerDetails(customerDetails[position].getCustomerName(),customerDetails[position].getCustomerAddress(),customerDetails[position].getCustomerPhoneNumber()));
                editcoustomer.add(customerDetails[position].getCustomerName());
                // Start customer edit activity
                Toast.makeText(context, "Start edit intent "+customerDetails[position].getCustomerName(), Toast.LENGTH_SHORT).show();
            }
        });

        ProductDetails[] productDetails;
        if (fragmentName.equals("Existing"))
        {
            //data for supplies
            // ProductDetails products =  new ProductDetails("milk","hbhk","jhbgre");
            // lis.add(products);
            productDetails = new ProductDetails[]
                    {
                            new ProductDetails("Milk"+position,100,52),
                            new ProductDetails("Curd"+position,400,92),
                            new ProductDetails("Gee"+position,500,54),
                    };
        }
        else {
            //data for products
            productDetails = new ProductDetails[]{
                    new ProductDetails("Milk"+position,100,98),
                    new ProductDetails("Gee"+position,400,92),
                    new ProductDetails("Curd"+position,500,54),
            };
        }
        final ProductAdapter ProductAdapter = new ProductAdapter(productDetails);
        holder.ProductsRecyclerView.setHasFixedSize(true);
        holder.ProductsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.ProductsRecyclerView.setAdapter(ProductAdapter);


    }

    @Override
    public int getItemCount() {
        return customerDetails.length;
    }

    class viewholder extends RecyclerView.ViewHolder{

        TextView Cname,Cadd;
        ImageButton edit;
        RecyclerView ProductsRecyclerView;
        CheckBox checkbox;

        viewholder(@NonNull View itemView)
        {
            super(itemView);
            Cname = itemView.findViewById(R.id.customer_name);
            Cadd = itemView.findViewById(R.id.customer_adr_pho);
            edit = itemView.findViewById(R.id.edit_button);
            checkbox = itemView.findViewById(R.id.checkbox);
            ProductsRecyclerView = itemView.findViewById(R.id.Products_recyclerView);
            if (fragmentName.equals("Pending"))
            {
                edit.setVisibility(View.GONE);
            }
            else {
                checkbox.setVisibility(View.GONE);
            }
        }
    }


    public static class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.viewHolder>{

        private ProductDetails[] productDetails;

        // private ArrayList<ProductDetails> productDetails;

        ProductAdapter(ProductDetails[] productDetails) {
            this.productDetails = productDetails;
        }

        @NonNull
        @Override
        public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_product_ist,parent,false);
            return new viewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull final viewHolder holder, final int position)
        {
            final String Name = productDetails[position].getName();
            final int Price = productDetails[position].getProductPrice();
            final int Quantity = productDetails[position].getProductQuantity();
            holder.Name.setText(Name);
            holder.Quantity.setText(Quantity+"");
            holder.Price.setText(Price+"");

        }


        @Override
        public int getItemCount() {
            return productDetails.length;
        }

        static class viewHolder extends RecyclerView.ViewHolder{
            TextView Name,Quantity,Price;

            viewHolder(@NonNull View itemView) {
                super(itemView);
                Name = itemView.findViewById(R.id.product_name);
                Quantity = itemView.findViewById(R.id.product_quantity);
                Price = itemView.findViewById(R.id.product_amount);

            }
        }



    }


}
