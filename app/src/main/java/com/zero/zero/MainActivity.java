package com.zero.zero;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.internal.firebase_auth.zzes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.zzv;
import com.google.firebase.auth.zzx;
import java.util.List;


public class MainActivity extends AppCompatActivity  {
    Toast toast;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    public FirebaseUser currentUser = new FirebaseUser() {
        @NonNull
        @Override
        public String getUid() {
            return null;
        }

        @NonNull
        @Override
        public String getProviderId() {
            return null;
        }

        @Override
        public boolean isAnonymous() {
            return false;
        }

        @Nullable
        @Override
        public List<String> zzcw() {
            return null;
        }

        @NonNull
        @Override
        public List<? extends UserInfo> getProviderData() {
            return null;
        }

        @NonNull
        @Override
        public FirebaseUser zza(@NonNull List<? extends UserInfo> list) {
            return null;
        }

        @Override
        public FirebaseUser zzcx() {
            return null;
        }

        @NonNull
        @Override
        public FirebaseApp zzcu() {
            return null;
        }

        @Nullable
        @Override
        public String getDisplayName() {
            return null;
        }

        @Nullable
        @Override
        public Uri getPhotoUrl() {
            return null;
        }

        @Nullable
        @Override
        public String getEmail() {
            return null;
        }

        @Nullable
        @Override
        public String getPhoneNumber() {
            return null;
        }

        @Nullable
        @Override
        public String zzba() {
            return null;
        }

        @NonNull
        @Override
        public zzes zzcy() {
            return null;
        }

        @Override
        public void zza(@NonNull zzes zzes) {

        }

        @NonNull
        @Override
        public String zzcz() {
            return null;
        }

        @NonNull
        @Override
        public String zzda() {
            return null;
        }

        @Nullable
        @Override
        public FirebaseUserMetadata getMetadata() {
            return null;
        }

        @Override
        public zzv zzdb() {
            return null;
        }

        @Override
        public void zzb(List<zzx> list) {

        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {

        }

        @Override
        public boolean isEmailVerified() {
            return false;
        }
    };
    private static final String TAG = "MyActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabLayout tabLayout = findViewById(R.id.tab_layout1);
        tabLayout.addTab(tabLayout.newTab().setText("Donor"));
        tabLayout.addTab(tabLayout.newTab().setText("NGO"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.donor_login_progress_bar);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

    }

    private void updateUI(FirebaseUser currentUser) {
        this.currentUser = currentUser;
        if(currentUser!=null) {
            Intent intent = new Intent(this, MainDonorHome.class);
            startActivity(intent);
        }
    }

    public void signIn(String email, String password) {
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                        progressBar.setVisibility(View.GONE);
                        // ...
                    }
                });
    }





    public void donorLogin(View view) {
        EditText email = findViewById(R.id.donorEmail);
        String email_val = email.getText().toString().trim();
        EditText password = findViewById(R.id.donorPassword);
        String password_val = password.getText().toString().trim();
        if(!email_val.isEmpty() || !password_val.isEmpty())
            signIn(email_val,password_val);
        else
            Toast.makeText(this,"Email or Password cannot be empty",Toast.LENGTH_LONG).show();
    }


    public void donorSignup(View view) {
        Intent intent = new Intent(this, donor_registration.class);
        startActivity(intent);
    }

}


