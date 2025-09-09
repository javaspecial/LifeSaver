package com.peoples.shield.main;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.peoples.shield.R;
import com.peoples.shield.risk.NetworkClient;
import com.peoples.shield.risk.RiskGeofenceReceiver;
import com.peoples.shield.risk.RiskZone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private GeofencingClient geofencingClient;
    private final List<PendingIntent> geofenceIntents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 33) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 100);
            }
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(com.peoples.shield.R.id.map);
        if (mapFragment != null) mapFragment.getMapAsync(this);

        geofencingClient = LocationServices.getGeofencingClient(this);

        findViewById(R.id.btn_disconnect).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LandingActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        ensureLocationPermission();

        LatLng center = new LatLng(23.7806, 90.4070);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 13f));

        // First attempt: fetch from demo server
        fetchZonesFromServer();
    }

    private void ensureLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            map.setMyLocationEnabled(true);
        }
    }

    private void fetchZonesFromServer() {
        NetworkClient.api().getRiskZones().enqueue(new Callback<List<RiskZone>>() {
            @Override
            public void onResponse(Call<List<RiskZone>> call, Response<List<RiskZone>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    applyZones(response.body());
                }
                else {
                    // Fallback to bundled asset
                    applyZones(loadZonesFromAssets());
                }
            }

            @Override
            public void onFailure(Call<List<RiskZone>> call, Throwable t) {
                applyZones(loadZonesFromAssets());
            }
        });
    }

    private List<RiskZone> loadZonesFromAssets() {
        List<RiskZone> zones = new ArrayList<>();
        try (InputStream is = getAssets().open("riskzones.json");
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) sb.append(line);
            String json = sb.toString();
            // Very small ad-hoc parser since we avoid extra deps; Retrofit will handle server case
            // Here we'll reuse Gson via Retrofit converter present on classpath
            com.google.gson.Gson gson = new com.google.gson.Gson();
            RiskZone[] arr = gson.fromJson(json, RiskZone[].class);
            for (RiskZone z : arr) zones.add(z);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return zones;
    }

    private void applyZones(List<RiskZone> zones) {
        clearGeofences();

        List<LatLng> points = new ArrayList<>();
        for (RiskZone zone : zones) {
            LatLng latLng = new LatLng(zone.lat, zone.lng);
            points.add(latLng);
            addRiskGeofence(latLng, zone.radius);
        }

        HeatmapTileProvider provider = new HeatmapTileProvider.Builder()
                .data(points)
                .radius(50)
                .build();
        map.addTileOverlay(new TileOverlayOptions().tileProvider(provider));

        if (!zones.isEmpty()) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(zones.get(0).getLat(), zones.get(0).getLng()), 14f));
        }
    }

    private void addRiskGeofence(LatLng center, float radius) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) return;

        Geofence geofence = new Geofence.Builder()
                .setRequestId("risk_" + center.latitude + "_" + center.longitude)
                .setCircularRegion(center.latitude, center.longitude, radius)
                .setExpirationDuration(6 * 60 * 60 * 1000L)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                .build();

        GeofencingRequest request = new GeofencingRequest.Builder()
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .addGeofence(geofence).build();

        Intent intent = new Intent(this, RiskGeofenceReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, (int) System.currentTimeMillis(), intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        geofencingClient.addGeofences(request, pi);
        geofenceIntents.add(pi);
    }

    private void clearGeofences() {
        for (PendingIntent pi : geofenceIntents) {
            geofencingClient.removeGeofences(pi);
        }
        geofenceIntents.clear();
        map.clear();
    }
}
