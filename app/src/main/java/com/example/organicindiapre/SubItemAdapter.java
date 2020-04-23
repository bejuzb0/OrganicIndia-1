package com.example.organicindiapre;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.organicindiapre.customer.CustProduct_Subclass;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SubItemAdapter extends RecyclerView.Adapter<SubItemAdapter.SubItemViewHolder> {
    List<CustProduct_Subclass> subItemList;

    SubItemAdapter(List<CustProduct_Subclass> subItemList) {
        this.subItemList = subItemList;
    }

    @NonNull
    @Override
    public SubItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cust_product_row_0, parent, false);
        return new SubItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubItemViewHolder holder, int position) {
        CustProduct_Subclass subItem = subItemList.get(position);
        holder.prod_name.setText(subItem.getProduct_name());
        holder.prod_qty.setText(subItem.getQuantity());
        holder.prod_amnt.setText(subItem.getAmount());
    }

    @Override
    public int getItemCount() {
        return subItemList.size();
    }

    public class SubItemViewHolder extends RecyclerView.ViewHolder {
        TextView prod_name;
        TextView prod_qty;
        TextView prod_amnt;
        public SubItemViewHolder(@NonNull View itemView) {
            super(itemView);
            prod_name = itemView.findViewById(R.id.product_name);
            prod_amnt = itemView.findViewById(R.id.amount_order);
            prod_qty = itemView.findViewById(R.id.quantity_order);
        }
    }
}
