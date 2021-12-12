package id.kharisma.studio.antimacet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.model.DocumentCollections;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {
    Button update;
    GoogleSignInClient mGoogleSignInClient;
    TextView name, email, phone01, phone02, verify, changepassword, latestnews;
    ImageView pic;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        name = findViewById(R.id.name);
        pic = findViewById(R.id.pic);
        email = findViewById(R.id.email);
        phone01 = findViewById(R.id.phone001);
        phone02 = findViewById(R.id.phone002);
        latestnews = findViewById(R.id.latestnews);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("382839449308-68r67vkbk60qjb19k0ijm0t6el6l1ft6.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        update = findViewById(R.id.update);
        verify = findViewById(R.id.verifylink);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference profileref = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(pic);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),EditProfileActivity.class);
                i.putExtra("fullname", name.getText().toString());
                i.putExtra("email", email.getText().toString());
                i.putExtra("phone",phone02.getText().toString());
                startActivity(i);

            }
        });

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null ) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            Uri personPhoto = acct.getPhotoUrl();
            name.setText(personName);
            email.setText(personEmail);
            Glide.with(this).load(String.valueOf(personPhoto)).into(pic);
        } else if (fAuth.getCurrentUser() != null) {
            phone01.setVisibility(View.VISIBLE);
            phone02.setVisibility(View.VISIBLE);
            update.setVisibility(View.VISIBLE);
            userID  = fAuth.getCurrentUser().getUid();
            DocumentReference documentReference = fStore.collection("users").document(userID);
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable  DocumentSnapshot value, @Nullable  FirebaseFirestoreException error) {
                    name.setText(value.getString("fname"));
                    email.setText(value.getString("email"));
                    phone02.setText(value.getString("phone"));
                }
            });
            if (fAuth.getCurrentUser().getUid() == "uiUSYyb0TvhI99JNrxHUgVGXA8r2") {
                latestnews.setVisibility(View.VISIBLE);
            };
        }


        latestnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),UpdateNewsActivity.class);
                startActivity(i);
                finish();
            }
        });

        FirebaseUser fUser = fAuth.getCurrentUser();
        if (!fUser.isEmailVerified()) {
            verify.setVisibility(View.VISIBLE);
            verify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fUser. sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(HomeActivity.this,"Verification E-mail Has Been Sent!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull  Exception e) {
                            Log.d("tag", "on failure: Email not sent" +e.getMessage());
                        }
                    });
                }
            });
        }

        changepassword = findViewById(R.id.changepass);
        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetpassword = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Change Password?");
                passwordResetDialog.setMessage("Enter New Password > 6 Characters Long");
                passwordResetDialog.setView(resetpassword);
                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newpassword = resetpassword.getText().toString();
                        FirebaseUser fUser = fAuth.getCurrentUser();
                        fUser.updatePassword(newpassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(HomeActivity.this, "Password Changed Successfully!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull  Exception e) {
                                Toast.makeText(HomeActivity.this,"Password Change Failed", Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                });
                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                passwordResetDialog.show();
            }
        });

        setUpToolbar();
        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:

                        Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.nav_drawer1:

                        Intent intent1 = new Intent(HomeActivity.this, MapsActivity.class);
                        startActivity(intent1);
                        finish();
                        break;
                    case R.id.nav_drawer2:

                        Intent intent2 = new Intent(HomeActivity.this, NewsActivity.class);
                        startActivity(intent2);
                        finish();
                        break;
                    case R.id.nav_drawer3:

                        Intent intent3 = new Intent(HomeActivity.this, ReportActivity.class);
                        startActivity(intent3);
                        finish();
                        break;
                    case R.id.nav_share: {

                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        String shareBody = "http://play.google.com/store/apps/detail?id=" + getPackageName();
                        String shareSub = "Try now";
                        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                        startActivity(Intent.createChooser(sharingIntent, "Share using"));

                    }
                    break;
                    case R.id.nav_drawer4:

                        Intent intent4 = new Intent(HomeActivity.this, AboutUsActivity.class);
                        startActivity(intent4);
                        finish();
                        break;
                    case R.id.nav_drawer5:

                        Intent intent5 = new Intent(HomeActivity.this, EmergencyActivity.class);
                        startActivity(intent5);
                        finish();
                        break;
                    case R.id.nav_drawer6:

                        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                        builder.setIcon(R.mipmap.ic_launcher_foreground);
                        builder.setTitle(R.string.app_name);
                        builder.setMessage("Apa anda yakin mau Logout?");
                        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                signOut();
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();
                        break;
                }
                return false;
            }
        });
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...

                        Toast.makeText(HomeActivity.this,"logout",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent (HomeActivity.this,LoginActivity.class));
                        finish();
                    }
                });
    }

    public void setUpToolbar() {
        drawerLayout = findViewById(R.id.drawerLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }


}