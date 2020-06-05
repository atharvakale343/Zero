package com.zero.zero;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;
import java.io.File;
import java.io.IOException;


public class MainDonorHome extends AppCompatActivity {
    static final int REQUEST_TAKE_PHOTO = 1;
    private FirebaseStorage storage;
    Uri photoURI;
    Toast toast;
    String currentPhotoPath;
    File image;
    public File photoFile;
    String imageFileName;
    String downloadUrl;
    ProgressBar progressBar;
    FirebaseUser currentFirebaseUser;
    String uid;
    ViewPager viewPager;
    ImageView ivPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_home);
        TabLayout tabLayout = findViewById(R.id.tab_layout1);
        tabLayout.addTab(tabLayout.newTab().setText("Donate"));
        tabLayout.addTab(tabLayout.newTab().setText("My Donations"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        final ViewPager viewPager = findViewById(R.id.pager1);
        final PagerAdapterForDonorHome adapter = new PagerAdapterForDonorHome(getSupportFragmentManager(), tabLayout.getTabCount());
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

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

        }

        storage = FirebaseStorage.getInstance();
        progressBar = findViewById(R.id.donor_home_progress_bar);
        progressBar.setVisibility(View.GONE);
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = currentFirebaseUser.getUid();
    }

    public void uploadFileAndCreateDonation() {

        progressBar.setVisibility(View.VISIBLE);
        if (photoURI == null) {
            downloadUrl = "Image not taken";
            uploadDonation();
        } else {
            StorageReference storageRef = storage.getReference();
            StorageReference imagesRef = storageRef.child("images");
            final StorageReference imagesUserRef = imagesRef.child(uid + "/" + photoURI.getLastPathSegment());
            final UploadTask uploadTask = imagesUserRef.putFile(photoURI);
            // Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    toast.makeText(getApplicationContext(), "Failure: Try Again Later", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    imagesUserRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            downloadUrl = task.getResult().toString();
                            Log.i("URL", downloadUrl);
                            uploadDonation();
                            toast.makeText(getApplicationContext(), "Donation Successful", Toast.LENGTH_LONG).show();
                            donateClearFunc();
                        }
                    });

                }


            });
        }

    }

    private void uploadDonation() {
        EditText foodDescription = findViewById(R.id.food_description);
        EditText pickupDate = findViewById(R.id.date);
        EditText fromPickupTime = findViewById(R.id.fromTime);
        EditText toPickupTime = findViewById(R.id.toTime);
        Spinner expiration = findViewById(R.id.expiration_date);
        EditText special = findViewById(R.id.special_instructions);
        DatabaseReference database;
        database = FirebaseDatabase.getInstance().getReference("Donations/" + uid);
        String id = database.push().getKey();

        Donation donation = new Donation(uid,
                id,
                "Unclaimed",
                foodDescription.getText().toString(),
                downloadUrl,
                pickupDate.getText().toString(),
                fromPickupTime.getText().toString(),
                toPickupTime.getText().toString(),
                expiration.getSelectedItem().toString(),
                special.getText().toString());


        database.child(id).setValue(donation)
                .addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(MainDonorHome.this, "Donation Successful", Toast.LENGTH_SHORT).show();
                            donateClearFunc();
                            progressBar.setVisibility(View.GONE);
                            switchToMyDonations();
                        } else {
                            Toast.makeText(MainDonorHome.this, "Unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                findViewById(R.id.take_photo).setEnabled(true);
            }
        }
    }

    public void donateSubmit(View view) {

        EditText foodDescription = findViewById(R.id.food_description);
        EditText pickupDate = findViewById(R.id.date);
        EditText fromPickupTime = findViewById(R.id.fromTime);
        EditText toPickupTime = findViewById(R.id.toTime);
        Spinner expiration = findViewById(R.id.expiration_date);
        EditText special = findViewById(R.id.special_instructions);
        Button takePhoto = findViewById(R.id.take_photo);
        String foodDescription$ = foodDescription.getText().toString();
        String pickupDate$ = pickupDate.getText().toString();
        String fromPickupTime$ = fromPickupTime.getText().toString();
        String toPickupTime$ = toPickupTime.getText().toString();
        String expiration$ = expiration.getSelectedItem().toString();
        String special$ = special.getText().toString();

        if (foodDescription$.isEmpty()) {
            foodDescription.setError("Food Description required");
            foodDescription.requestFocus();
            return;
        }
        if (pickupDate$.isEmpty()) {
            pickupDate.setError("Pickup Date required");
            pickupDate.requestFocus();
            return;
        }
        if (fromPickupTime$.isEmpty()) {
            fromPickupTime.setError("Pickup Time required");
            fromPickupTime.requestFocus();
            return;
        }
        if (toPickupTime$.isEmpty()) {
            toPickupTime.setError("Pickup Time required");
            toPickupTime.requestFocus();
            return;
        }
        if (expiration$.isEmpty()) {
            ((TextView)expiration.getSelectedView()).setError("Expiration Date required");
            expiration.requestFocus();
            return;
        }
        if (special$.isEmpty()) {
            special.setText("None");
        }

        uploadFileAndCreateDonation();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //toast.makeText(this,"Called",Toast.LENGTH_LONG).show();
        Button takePhoto = findViewById(R.id.take_photo);

        if (requestCode == REQUEST_TAKE_PHOTO) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                ivPreview = findViewById(R.id.donateImageView);
                ivPreview.requestLayout();
                ivPreview.getLayoutParams().height = 300;
                ivPreview.getLayoutParams().width = 500;
                TextView textView = findViewById(R.id.textView7);
                ivPreview.setImageBitmap(takenImage);
                setPic();
                takePhoto.setText("CHANGE PHOTO");
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void donorLogout(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void donateClear(View view) {
        donateClearFunc();
    }
    public void donateClearFunc() {
        EditText foodDescription = findViewById(R.id.food_description);
        foodDescription.getText().clear();
        EditText pickupDate = findViewById(R.id.date);
        pickupDate.getText().clear();
        EditText fromPickupTime = findViewById(R.id.fromTime);
        EditText toPickupTime = findViewById(R.id.toTime);
        fromPickupTime.getText().clear();
        toPickupTime.getText().clear();
        Spinner expirationDate = findViewById(R.id.expiration_date);
        expirationDate.setSelection(0);
        EditText specialInstructions = findViewById(R.id.special_instructions);
        specialInstructions.getText().clear();
        Button takePhoto = findViewById(R.id.take_photo);

        takePhoto.setText("TAKE PHOTO");
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
    private void setPic() {
        // Get the dimensions of the View
        int targetW = ivPreview.getWidth();
        int targetH = ivPreview.getHeight();
        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(currentPhotoPath);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
        ivPreview.setImageBitmap(bitmap);
    }

    public void takePhoto(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            dispatchTakePictureIntent();
        }

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                //...
            }

            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.zero.zero.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                //toast.makeText(this,Long.toString(photoFile.length()),Toast.LENGTH_LONG).show();
            }
        }
    }
    public void myAccount(View view) {
        Intent intent = new Intent(this, MyAccount.class);
        startActivity(intent);
    }

    public void switchToMyDonations() {
        viewPager = findViewById(R.id.pager1);
        viewPager.setCurrentItem(1);
    }
}


