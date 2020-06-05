package com.zero.zero;

import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MyDonationsAdapter extends RecyclerView.Adapter<MyDonationsAdapter.MyViewHolder> {

    private List<MyDonationsInfo> myDonations;
    private OnItemClickListener listener;

    public MyDonationsAdapter(List<MyDonationsInfo> donationsList, OnItemClickListener listener) {
        this.myDonations = donationsList;
        this.listener = listener;
    }

    public MyDonationsAdapter(List<MyDonationsInfo> donationsList) {
        this.myDonations = donationsList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView foodDescription, status, date;
        Button viewDetails;
        Toast toast;
        View v;

        public MyViewHolder(View view) {
            super(view);
            this.v = view;
            foodDescription = view.findViewById(R.id.foodDescriptionView);
            status = view.findViewById(R.id.statusView);
            date = view.findViewById(R.id.dateView);
            viewDetails = view.findViewById(R.id.view_details);
//            viewDetails.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    toast.makeText(view.getContext(), "HI", Toast.LENGTH_LONG).show();
//                }
//            });
        }




        public void bind(final MyDonationsInfo item, final OnItemClickListener listener) {
            viewDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.donation_tile, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MyDonationsInfo myDonationsInfo = myDonations.get(position);
        holder.foodDescription.setText(myDonationsInfo.getFoodDescription());
        holder.status.setText(myDonationsInfo.getStatus());
        holder.date.setText(myDonationsInfo.getDate());
        holder.bind(myDonations.get(position), listener);
    }

    @Override
    public int getItemCount() {

        return myDonations.size();
    }

}




