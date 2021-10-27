package id.kharisma.studio.antimacet;

import androidx.appcompat.app.AppCompatActivity;
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
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ReportActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    Button kecelakaan, bencana, macet, masalahmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        setUpToolbar();
        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:

                        Intent intent = new Intent(ReportActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.nav_drawer1:

                        Intent intent1 = new Intent(ReportActivity.this, NewsActivity.class);
                        startActivity(intent1);
                        finish();
                        break;
                    case R.id.nav_drawer2:

                        Intent intent2 = new Intent(ReportActivity.this, NewsActivity.class);
                        startActivity(intent2);
                        finish();
                        break;
                    case R.id.nav_drawer3:

                        Intent intent3 = new Intent(ReportActivity.this, ReportActivity.class);
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

                        Intent intent4 = new Intent(ReportActivity.this, AboutUsActivity.class);
                        startActivity(intent4);
                        finish();
                        break;
                    case R.id.nav_drawer5:

                        Intent intent5 = new Intent(ReportActivity.this, EmergencyActivity.class);
                        startActivity(intent5);
                        finish();
                        break;
                }
                return false;
            }
        });

        kecelakaan = findViewById(R.id.Report1);
        bencana = findViewById(R.id.Report2);
        macet = findViewById(R.id.Report3);
        masalahmap = findViewById(R.id.Report4);
        kecelakaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent (ReportActivity.this,KecelakaanActivity.class));
                finish();
            }
        });
        bencana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent (ReportActivity.this,BencanaActivity.class));
                finish();
            }
        });
        macet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent (ReportActivity.this,MacetActivity.class));
                finish();
            }
        });
        masalahmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent (ReportActivity.this,MasalahMapActivity.class));
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