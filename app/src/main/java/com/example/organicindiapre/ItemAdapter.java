package com.example.organicindiapre;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.organicindiapre.customer.CustProduct_Subclass;
import com.example.organicindiapre.customer.CustomerClass;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/*  Aditya Kumar
    Used for the Outermost card with Customer and their product details.
    Binds one customer to one Holder.
    For layout of the card, check customer_row_0.xmlml
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<CustomerClass> itemList;

    public ItemAdapter(List<CustomerClass> itemList) {
        this.itemList = itemList;
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_row_0, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            CustomerClass customerObj = itemList.get(position);
            //if(customerObj != null) {
                holder.cust_name.setText(customerObj.getCust_name());
                holder.cust_phone_no.setText(customerObj.getPhone_no());
                holder.cust_address.setText(customerObj.getAddress());
                LinearLayoutManager layoutManager = new LinearLayoutManager(
                        holder.productRecyclerView.getContext(),
                        LinearLayoutManager.VERTICAL,
                        false
                );
                layoutManager.setInitialPrefetchItemCount(customerObj.getProductList().size());
                SubItemAdapter subItemAdapter = new SubItemAdapter(customerObj.getProductList());
                holder.productRecyclerView.setLayoutManager(layoutManager);
                holder.productRecyclerView.setAdapter(subItemAdapter);
                holder.productRecyclerView.setRecycledViewPool(viewPool);
            //}


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView cust_name;
        private TextView cust_phone_no;
        private  TextView cust_address;
        private RecyclerView productRecyclerView;
        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            cust_name = itemView.findViewById(R.id.customer_name);
            cust_phone_no = itemView.findViewById(R.id.phone_no);
            cust_address = itemView.findViewById(R.id.address);
            productRecyclerView = itemView.findViewById(R.id.recycViewProductList);
        }
    }

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
            holder.prod_name.setText(subItem.getProductName());
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

}
