package com.sorinaidea.ghaichi.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.CustomInfoWindowAdapter;
import com.sorinaidea.ghaichi.model.SampleClusterItem;
import com.sorinaidea.ghaichi.util.GhaichiPrefrenceManager;
import com.sorinaidea.ghaichi.util.Util;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.BarbershopServices;

import net.sharewire.googlemapsclustering.Cluster;
import net.sharewire.googlemapsclustering.ClusterManager;
import net.sharewire.googlemapsclustering.DefaultIconGenerator;
import net.sharewire.googlemapsclustering.IconGenerator;
import net.sharewire.googlemapsclustering.IconStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MapsActivity
        extends FragmentActivity
        implements OnMapReadyCallback,
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private Marker mCurrLocationMarker;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient mFusedLocationProviderClient;

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private final LatLng mDefaultLocation = new LatLng(35.3219, 46.9862);
    private static final int DEFAULT_ZOOM = 20;
    private static final float MIN_ZOOM = 5.0f;
    private static final float MAX_ZOOM = 20.0f;

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private boolean mLocationPermissionGranted;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* Fragm  entActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        View locationButton = ((View) mapFragment.getView().findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();

        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        rlp.setMargins(16, 0, 0, 16);

        mapFragment.getMapAsync(this);


//        SearchView search = (SearchView) findViewById(R.id.txtSearch);
//        search.setLayoutParams(getSupportLoaderManager().LayoutParams(Gravity.RIGHT));

    }

    private int getTextHeight(Paint paint, String str) {
        Rect bounds = new Rect();
        paint.getTextBounds(str, 0, str.length(), bounds);
        return bounds.height();
    }

    private int getTextWidth(Paint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }

    private final static float ICON_SCALE = 1.256f;

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId, @Nullable String text) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(0, 0, (int) (vectorDrawable.getIntrinsicWidth() * ICON_SCALE), (int) (vectorDrawable.getIntrinsicHeight() * ICON_SCALE));
        Bitmap bitmap = Bitmap.createBitmap((int) (vectorDrawable.getIntrinsicWidth() * ICON_SCALE), (int) (vectorDrawable.getIntrinsicHeight() * ICON_SCALE), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        if (text != null) {
            Paint paint = new Paint();
            paint.setAlpha(90);
            paint.setTextSize(Util.dp(16, MapsActivity.this));
            paint.setColor(Color.WHITE);


            canvas.drawText(text
                    , (bitmap.getWidth() / 2) - (getTextWidth(paint, text) / 2)
                    , (bitmap.getHeight() / 2) + (getTextHeight(paint, text) / 2)
                    , paint);
        }
//        return BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * 1.5f), (int) (bitmap.getHeight() * 1.5f), false));
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void initMarkers(final GoogleMap googleMap) {


        final ClusterManager<SampleClusterItem> clusterManager = new ClusterManager<>(this, googleMap);
        googleMap.setOnCameraIdleListener(clusterManager);


//        DefaultIconGenerator<SampleClusterItem> iconGenerator = new DefaultIconGenerator<>(this);
//
//        iconGenerator.setIconStyle(
//                new IconStyle.Builder(this)
//                        .setClusterIconResId(R.drawable.ic_pin)
//                        .build()
//        );

        clusterManager.setIconGenerator(new IconGenerator<SampleClusterItem>() {
            @NonNull
            @Override
            public BitmapDescriptor getClusterIcon(@NonNull Cluster<SampleClusterItem> cluster) {
                int size = cluster.getItems().size();

                if (size > 10 && size <= 50) {
                    return bitmapDescriptorFromVector(MapsActivity.this, R.drawable.ic_cluster_norm, Integer.toString(cluster.getItems().size()));
                } else if (size > 50 && size <= 100) {
                    return bitmapDescriptorFromVector(MapsActivity.this, R.drawable.ic_cluster_much, Integer.toString(cluster.getItems().size()));
                } else if (size > 100 && size <= 500) {
                    return bitmapDescriptorFromVector(MapsActivity.this, R.drawable.ic_cluster_very_much, Integer.toString(cluster.getItems().size()));
                } else if (size > 500) {
                    return bitmapDescriptorFromVector(MapsActivity.this, R.drawable.ic_cluster_too_much, Integer.toString(cluster.getItems().size()));
                } else {
                    return bitmapDescriptorFromVector(MapsActivity.this, R.drawable.ic_cluster, Integer.toString(cluster.getItems().size()));
                }
            }

            @NonNull
            @Override
            public BitmapDescriptor getClusterItemIcon(@NonNull SampleClusterItem clusterItem) {
                return bitmapDescriptorFromVector(MapsActivity.this, R.drawable.ic_pin, null);
            }
        });


        clusterManager.setCallbacks(new ClusterManager.Callbacks<SampleClusterItem>() {
            @Override
            public boolean onClusterClick(@NonNull Cluster<SampleClusterItem> cluster) {
//                LatLngBounds.Builder builder = LatLngBounds.builder();
//                for (SampleClusterItem item : cluster.getItems()) {
//                    builder.include(item.getLocation());
//                }
//                // Get the LatLngBounds
//                final LatLngBounds bounds = builder.build();
//
//                // Animate camera to the bounds
//                try {
//                    mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                return true;
            }

            @Override
            public boolean onClusterItemClick(@NonNull SampleClusterItem clusterItem) {

                return false;
            }
        });


        getBarbershopsLocations(clusterManager);


    }

    public void addMarkerAtLocation(LatLng location) {
//        mMap.addMarker(new MarkerOptions().title("MARKER").position(location));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, DEFAULT_ZOOM));
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, DEFAULT_ZOOM));
//        Toast.makeText(this, getInfo(location), Toast.LENGTH_SHORT).show();

    }


    private String getInfo(LatLng location) {
        String info = "";
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());

            List<Address> addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1);
            info += addresses.get(0).getAddressLine(0);
            info+="  --  ";
            info += addresses.get(0).getAddressLine(1);
            info+="  --  ";
            info += addresses.get(0).getAddressLine(2);
        } catch (Exception ex) {
        }
        return info;
    }

    private void getBarbershopsLocations(ClusterManager<SampleClusterItem> clusterManager) {

        Retrofit retrofit = API.getRetrofit();


        BarbershopServices service = retrofit.create(BarbershopServices.class);

        String accessKey = Util.getAccessKey(getApplicationContext());

        Call<List<com.sorinaidea.ghaichi.fast.Location>> repos = service.locations(accessKey);

        repos.enqueue(new Callback<List<com.sorinaidea.ghaichi.fast.Location>>() {

            @Override
            public void onFailure(Call<List<com.sorinaidea.ghaichi.fast.Location>> call, Throwable t) {
                Log.d("ERROR", "ERROR IN GETTING DATA");
            }

            @Override
            public void onResponse(Call<List<com.sorinaidea.ghaichi.fast.Location>> call,
                                   Response<List<com.sorinaidea.ghaichi.fast.Location>> response) {

                if (response.body() != null && !response.body().isEmpty()) {

                    List<SampleClusterItem> clusterItems = new ArrayList<>();
                    for (com.sorinaidea.ghaichi.fast.Location marker :
                            response.body()) {
                        SampleClusterItem item = new SampleClusterItem(
                                marker.getId(),
                                new LatLng(Double.parseDouble(marker.getLatitude()),
                                        Double.parseDouble(marker.getLongitude()))
                                , marker.getName() + "@" + marker.getId()
                                , marker.getIcon()
                        );


                        clusterItems.add(item);
                    }
                    clusterManager.setItems(clusterItems);
                } else {
                    Log.d("ERROR", "response is null");

                }
            }

        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        initMarkers(mMap);

        getLocationPermission();

        updateLocationUI();

        getDeviceLocation();

        mMap.setOnInfoWindowClickListener(this);
        mMap.setPadding(0, 0, 16, 16);

        mMap.setMinZoomPreference(MIN_ZOOM);
        mMap.setMaxZoomPreference(MAX_ZOOM);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));

        CustomInfoWindowAdapter adapter = new CustomInfoWindowAdapter(MapsActivity.this);
        mMap.setInfoWindowAdapter(adapter);

        mMap.setOnMapClickListener(latLng -> {
            addMarkerAtLocation(latLng);


        });


    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent = new Intent(MapsActivity.this, BarberShopActivity.class);
        intent.putExtra("BARBERSHOP_ID", marker.getTitle().split("@")[1]);
        startActivity(intent);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("موقعیت فعلی شما");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = (Location) task.getResult();

                            if (mLastKnownLocation != null) {
                                LatLng lastKnownPosition = new LatLng(mLastKnownLocation.getLatitude(),
                                        mLastKnownLocation.getLongitude());

//                                mMap.addMarker(new MarkerOptions()
//                                        .icon(Util.getBitmapDescriptor(R.drawable.ic_hairdresser_64px, getApplicationContext()))
//                                        .title(getString(R.string.your_current_location))
//                                        .flat(true)
//                                        .anchor(0.5f, 1f)
//                                        .position(lastKnownPosition));

                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastKnownPosition, DEFAULT_ZOOM));
                            }
                        } else {
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

}
