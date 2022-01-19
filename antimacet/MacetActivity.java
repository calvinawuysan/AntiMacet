package id.kharisma.studio.antimacet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MacetActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    GoogleSignInClient mGoogleSignInClient;
    EditText reportername, wil, pen, tggl;
    Button submitbtn;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_macet);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("382839449308-68r67vkbk60qjb19k0ijm0t6el6l1ft6.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        reportername = findViewById(R.id.reporter2);
        wil = findViewById(R.id.wilayah2);
        pen = findViewById(R.id.penjelasan2);
        tggl = findViewById(R.id.tgl2);
        submitbtn = findViewById(R.id.submit2);


        databaseReference = FirebaseDatabase.getInstance("https://anti-macet-d1e23-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("Macet");
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertdata();
            }
        });

        setUpToolbar();
        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:

                        Intent intent = new Intent(MacetActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.nav_drawer1:

                        Intent intent1 = new Intent(MacetActivity.this, MapsActivity.class);
                        startActivity(intent1);
                        finish();
                        break;
                    case R.id.nav_drawer2:

                        Intent intent2 = new Intent(MacetActivity.this, NewsActivity.class);
                        startActivity(intent2);
                        finish();
                        break;
                    case R.id.nav_drawer3:

                        Intent intent3 = new Intent(MacetActivity.this, ReportActivity.class);
                        startActivity(intent3);
                        finish();
                        break;
                    case R.id.nav_share: {

                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        String shareBody = "http://play.google.com/store/apps/detail?id=" + getPackageName();
                        String shareSub = "Try now";
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                        startActivity(Intent.createChooser(sharingIntent, "Share using"));

                    }
                    break;
                    case R.id.nav_drawer4:

                        Intent intent4 = new Intent(MacetActivity.this, AboutUsActivity.class);
                        startActivity(intent4);
                        finish();
                        break;
                    case R.id.nav_drawer5:

                        Intent intent5 = new Intent(MacetActivity.this, EmergencyActivity.class);
                        startActivity(intent5);
                        finish();
                        break;
                    case R.id.nav_drawer6:

                        AlertDialog.Builder builder = new AlertDialog.Builder(MacetActivity.this);
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
                    case R.id.nav_feedback:

                        AlertDialog.Builder feed = new AlertDialog.Builder(MacetActivity.this);
                        feed.setIcon(R.mipmap.ic_launcher_foreground);
                        feed.setTitle(R.string.app_name);
                        feed.setMessage("Untuk membuat aplikasi ini menjadi lebih baik, kami perlu bantuan anda untuk memberikan feedback bagi kami. Feedback tidak akan memakan waktu yang banyak, kami harap pengguna dapat meluangkan waktu sejenak untuk mengisi form feedback. Terima kasih telah membantu!");
                        feed.setPositiveButton("Feedback", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/xurrQZMiyayNRYbp9"));
                                startActivity(browserIntent);
                            }
                        });
                        feed.setNegativeButton("Later", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        feed.show();
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

                        Toast.makeText(MacetActivity.this,"logout",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent (MacetActivity.this,LoginActivity.class));
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

    private void insertdata() {
        String reporter = reportername.getText().toString();
        String wilayah = wil.getText().toString();
        String penjelasan = pen.getText().toString();
        String tanggal = tggl.getText().toString();

        if (TextUtils.isEmpty(reporter)) {
            reportername.setError("Name is Required");
            return;
        }

        if (TextUtils.isEmpty(tanggal)) {
            tggl.setError("Tanggal is Required");
            return;
        }

        if (TextUtils.isEmpty(wilayah)) {
            wil.setError("Wilayah is Required");
            return;
        }

        if (TextUtils.isEmpty(penjelasan)) {
            pen.setError("Penjelasan is Required");
            return;
        }



        Macet macet = new Macet(reporter,wilayah,penjelasan,tanggal);
        databaseReference.push().setValue(macet).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(MacetActivity.this,"Data Submitted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MacetActivity.this, ReportActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}