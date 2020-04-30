package com.example.organicindiapre;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Sai Gopal Adapter for products
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.viewHolder>{


    private ArrayList<ProductDetails> productDetailsArrayList;

    public ProductAdapter(ArrayList<ProductDetails> productDetailsArrayList) {
        this.productDetailsArrayList = productDetailsArrayList;
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
        final String Name = productDetailsArrayList.get(position).getName();
        final String Price = productDetailsArrayList.get(position).getProductPrice();
        final String Quantity = productDetailsArrayList.get(position).getProductQuantity();
        holder.Name.setText(Name);
        holder.Quantity.setText(Quantity);
        holder.Price.setText(Price);

    }


    @Override
    public int getItemCount() {
        return productDetailsArrayList.size();
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
