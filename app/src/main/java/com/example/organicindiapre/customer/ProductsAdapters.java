package com.example.organicindiapre.customer;

import android.annotation.SuppressLint;
import android.content.Context;
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

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

    /**
       First adapter for both Supplier and Product . main items like Vendor names,logo or Product names
    */

public class ProductsAdapters extends RecyclerView.Adapter<ProductsAdapters.viewHolder>
{

    private ArrayList<List> list;
    private Context context;
    private String fragmentName;
    private ArrayList SelectedList = new ArrayList(10);

    ArrayList getSelectedList() {
        return SelectedList;
    }


    ProductsAdapters(ArrayList<List> list, Context context, String fragmentName) {
        this.list = list;
        this.context = context;
        this.fragmentName = fragmentName;
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

        //getting data for each product under vendor or product
        ProductDetails[] productDetails;
        if (fragmentName.equals("supplier"))
        {
            //data for supplies
            holder.Title.setText("Product");
            // ProductDetails products =  new ProductDetails("milk","hbhk","jhbgre");
            // lis.add(products);
            productDetails = new ProductDetails[]
                    {
                            new ProductDetails("milk"+position,1,45,1),
                            new ProductDetails("curd"+position,2,50,2),
                            new ProductDetails("Paneer"+position,300,55,50),
                    };
        }
        else {
            //data for products
            holder.Title.setText("Vendor");
            productDetails = new ProductDetails[]{
                    new ProductDetails("ravi"+position,100,98,50),
                    new ProductDetails("krishna"+position,400,92,100),
                    new ProductDetails("sai"+position,500,54,250),
            };
        }
        final VendorProductAdapter vendorProductAdapter = new VendorProductAdapter(productDetails);
        holder.vendorProductList.setHasFixedSize(true);
        holder.vendorProductList.setLayoutManager(new LinearLayoutManager(context));
        holder.vendorProductList.setAdapter(vendorProductAdapter);



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

        private ProductDetails[] productDetails;

       // private ArrayList<ProductDetails> productDetails;

        VendorProductAdapter(ProductDetails[] productDetails) {
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
            final String Name = productDetails[position].getName();
            final int Price = productDetails[position].getProductPrice();
            final int Quantity = productDetails[position].getProductQuantity();
            final int MinPackingQuantity = productDetails[position].getMinPackingQuantity();
            //final String QuantityType = productDetails[position].getQuantityType();
            holder.Name.setText(Name);
            holder.Price.setText(Price+"/"+MinPackingQuantity);
            holder.Quantity.setText(Quantity+"");


            holder.Add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    holder.Quantity.setText(Count(holder.Quantity.getText().toString(),MinPackingQuantity));
                }
            });
            holder.Remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.Quantity.setText(Count(holder.Quantity.getText().toString(),-MinPackingQuantity));
                }
            });
            holder.Checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                    {
                        SelectedList.add(0,Name);
                        SelectedList.add(1,holder.Quantity.getText().toString());
                        SelectedList.add(2,Price+" ");
                    }
                    else {
                        SelectedList.remove(1);
                       SelectedList.remove(Name);
                       SelectedList.remove(Price+" ");
                    }
                   // productDetails.setOrderList(SelectedList);
                }
            });

        }

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
            return productDetails.length;
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

