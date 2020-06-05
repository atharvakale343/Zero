package com.zero.zero;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MyDonations extends Fragment {
    private FirebaseUser currentFirebaseUser;
    private long numberOfChildren;
    private List<MyDonationsInfo> donationsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MyDonationsAdapter mAdapter;
    ProgressBar progressBar;
    RelativeLayout relativeLayout;
    Toast toast;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_donor_mydonations, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler_view);
        progressBar = view.findViewById(R.id.progressBarMyDonations);
        relativeLayout = view.findViewById(R.id.donationTileRelativeLayout);
        OnItemClickListener listener = new OnItemClickListener() {
            @Override
            public void onItemClick(MyDonationsInfo item) {
                    Intent intent = new Intent(view.getContext(),ViewAndEditDonation.class);
                    intent.putExtra("DonationKey", item.getKey());
                    startActivity(intent);
            }
        };
        mAdapter = new MyDonationsAdapter(donationsList,listener);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        prepareDonationsInfo();


    }

    private void prepareDonationsInfo() {
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = currentFirebaseUser.getUid();
        final DatabaseReference donations = FirebaseDatabase.getInstance().getReference("Donations/" + uid);
        numberOfChildren = 0;

        //toast.makeText(getContext(),donationsList.size(),Toast.LENGTH_LONG).show();
        donations.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.VISIBLE);
                donationsList.clear();
                numberOfChildren = dataSnapshot.getChildrenCount();
                for (DataSnapshot uniqueKeySnapshot : dataSnapshot.getChildren()) {
                    Donation donation = uniqueKeySnapshot.getValue(Donation.class);
                    MyDonationsInfo myDonationsInfo = new MyDonationsInfo(donation.foodDescription,
                            "Status: " + donation.status, donation.key, "Pickup Date: " + donation.pickupDate);
                    donationsList.add(myDonationsInfo);
                    progressBar.setVisibility(View.GONE);
                }
                Collections.reverse(donationsList);
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}