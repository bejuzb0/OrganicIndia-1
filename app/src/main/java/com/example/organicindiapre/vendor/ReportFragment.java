package com.example.organicindiapre.vendor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.organicindiapre.R;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ReportFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_report_0, container,false);

        RecyclerView noDelivery = view.findViewById(R.id.no_delivery_recycler);
        RecyclerView revenueAmount = view.findViewById(R.id.Revenue_Amount_recycler);

        ArrayList<ReportsHolder> noDeliveryArrayList = new ArrayList<>();
        ArrayList<ReportsHolder> revenueArrayList = new ArrayList<>();

        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext());
        LinearLayoutManager Layout = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        noDelivery.setLayoutManager(linearLayout);
        revenueAmount.setLayoutManager(Layout);

        for (int i = 0;i<=6;i++)
        {
            ReportsHolder reportsHolder = new ReportsHolder("from"+i,"to","cudid"+i);
            noDeliveryArrayList.add(reportsHolder);
        }

        NoDeliveryAdapter noDeliveryAdapter = new NoDeliveryAdapter(noDeliveryArrayList);
        noDelivery.setAdapter(noDeliveryAdapter);
        noDelivery.setHasFixedSize(true);

        for (int i = 0;i<=8;i++)
        {
            ReportsHolder reportsHolder = new ReportsHolder("amount"+i,"revenue");
            revenueArrayList.add(reportsHolder);
        }

         RevenueAdapter revenueAdapter = new RevenueAdapter(revenueArrayList);
         revenueAmount.setAdapter(revenueAdapter) ;
         revenueAmount.setHasFixedSize(true);

        return view;
    }


    public static class NoDeliveryAdapter extends RecyclerView.Adapter<NoDeliveryAdapter.NoDeliveryViewHolder>
    {
        ArrayList<ReportsHolder> reportsHolders;

        NoDeliveryAdapter(ArrayList<ReportsHolder> reportsHolders) {
            this.reportsHolders = reportsHolders;
        }

        @NonNull
        @Override
        public NoDeliveryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nodeliveryreport_items,parent,false);
            return new NoDeliveryViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull NoDeliveryViewHolder holder, int position) {

            holder.from.setText(reportsHolders.get(position).getFrom());
            holder.to.setText(reportsHolders.get(position).getTo());

        }

        @Override
        public int getItemCount() {
            return reportsHolders.size();
        }

        static class NoDeliveryViewHolder extends RecyclerView.ViewHolder {
            TextView from,to;
            NoDeliveryViewHolder(@NonNull View itemView) {
                super(itemView);
                from = itemView.findViewById(R.id.from);
                to = itemView.findViewById(R.id.to);
            }
        }
    }

    public static class RevenueAdapter extends RecyclerView.Adapter<RevenueAdapter.RevenueViewHolder>{

        ArrayList<ReportsHolder> holders ;

        RevenueAdapter(ArrayList<ReportsHolder> holders) {
            this.holders = holders;
        }

        @NonNull
        @Override
        public RevenueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reports_amount_revenue_items,parent,false);
            return new RevenueViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RevenueViewHolder holder, int position)
        {
            holder.amount.setText(holders.get(position).getAmount());
            holder.revenue.setText(holders.get(position).getRevenue());
        }

        @Override
        public int getItemCount() {
            return holders.size();
        }

        static class RevenueViewHolder extends RecyclerView.ViewHolder {
            TextView revenue,amount;
            RevenueViewHolder(@NonNull View itemView) {
                super(itemView);
                revenue = itemView.findViewById(R.id.revenue);
                amount = itemView.findViewById(R.id.amount);
            }
        }
    }

}
