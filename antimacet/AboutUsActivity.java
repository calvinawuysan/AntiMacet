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
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AboutUsActivity extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    ImageView ig_im, fb_im, web_im;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("382839449308-68r67vkbk60qjb19k0ijm0t6el6l1ft6.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        ig_im = findViewById(R.id.imageView3);
        fb_im = findViewById(R.id.imageView4);
        web_im = findViewById(R.id.imageView6);

        TextView t2 = (TextView) findViewById(R.id.textView31);
        t2.setMovementMethod(LinkMovementMethod.getInstance());

        web_im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://antimacet.com"));
                startActivity(browserIntent);
            }
        });

        ig_im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://instagram.com/antimacet"));
                startActivity(browserIntent);
            }
        });
        fb_im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://facebook.com/people/Zaceti-AntiMacet/100076181011998"));
                startActivity(browserIntent);
            }
        });

        setUpToolbar();
        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:

                        Intent intent = new Intent(AboutUsActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.nav_drawer1:

                        Intent intent1 = new Intent(AboutUsActivity.this, MapsActivity.class);
                        startActivity(intent1);
                        finish();
                        break;
                    case R.id.nav_drawer2:

                        Intent intent2 = new Intent(AboutUsActivity.this, NewsActivity.class);
                        startActivity(intent2);
                        finish();
                        break;
                    case R.id.nav_drawer3:

                        Intent intent3 = new Intent(AboutUsActivity.this, ReportActivity.class);
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

                        Intent intent4 = new Intent(AboutUsActivity.this, AboutUsActivity.class);
                        startActivity(intent4);
                        finish();
                        break;
                    case R.id.nav_drawer5:

                        Intent intent5 = new Intent(AboutUsActivity.this, EmergencyActivity.class);
                        startActivity(intent5);
                        finish();
                        break;
                    case R.id.nav_drawer6:

                        AlertDialog.Builder builder = new AlertDialog.Builder(AboutUsActivity.this);
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

                        AlertDialog.Builder feed = new AlertDialog.Builder(AboutUsActivity.this);
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

                        Toast.makeText(AboutUsActivity.this,"logout",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent (AboutUsActivity.this,LoginActivity.class));
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