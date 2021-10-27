package id.kharisma.studio.antimacet;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import id.kharisma.studio.antimacet.databinding.ActivityMapsBinding;
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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        setUpToolbar();
        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:

                        Intent intent = new Intent(MapsActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.nav_drawer1:

                        Intent intent1 = new Intent(MapsActivity.this, NewsActivity.class);
                        startActivity(intent1);
                        finish();
                        break;
                    case R.id.nav_drawer2:

                        Intent intent2 = new Intent(MapsActivity.this, NewsActivity.class);
                        startActivity(intent2);
                        finish();
                        break;
                    case R.id.nav_drawer3:

                        Intent intent3 = new Intent(MapsActivity.this, ReportActivity.class);
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

                        Intent intent4 = new Intent(MapsActivity.this, AboutUsActivity.class);
                        startActivity(intent4);
                        finish();
                        break;
                    case R.id.nav_drawer5:

                        Intent intent5 = new Intent(MapsActivity.this, EmergencyActivity.class);
                        startActivity(intent5);
                        finish();
                        break;
                }
                return false;
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


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}