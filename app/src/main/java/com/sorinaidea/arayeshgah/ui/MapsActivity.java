package com.sorinaidea.arayeshgah.ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.adapter.CustomInfoWindowAdapter;
import com.sorinaidea.arayeshgah.model.ClusterMarker;
import com.sorinaidea.arayeshgah.model.MapMarker;
import com.sorinaidea.arayeshgah.util.CustomClusterRenderer;
import com.sorinaidea.arayeshgah.util.Util;
import com.sorinaidea.arayeshgah.webservice.MapBuilderWebService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    private final LatLng mDefaultLocation = new LatLng(35.311308, 46.995735);
    private static final int DEFAULT_ZOOM = 20;

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


        mapFragment.getMapAsync(this);

    }


    private void initMarkers(final GoogleMap googleMap) {
        final ClusterManager<ClusterMarker> clusterManager = new ClusterManager<>(this, googleMap);


        final CustomClusterRenderer renderer = new CustomClusterRenderer(this, googleMap, clusterManager);
        clusterManager.setRenderer(renderer);


        googleMap.setOnCameraIdleListener(clusterManager);

        clusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<ClusterMarker>() {
            @Override
            public boolean onClusterClick(Cluster<ClusterMarker> cluster) {


                // Zoom in the cluster. Need to create LatLngBounds and including all the cluster items
                // inside of bounds, then animate to center of the bounds.

                // Create the builder to collect all essential cluster items for the bounds.
                LatLngBounds.Builder builder = LatLngBounds.builder();
                for (ClusterMarker item : cluster.getItems()) {
                    builder.include(item.getPosition());
                }
                // Get the LatLngBounds
                final LatLngBounds bounds = builder.build();

                // Animate camera to the bounds
                try {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return true;
            }
        });

        googleMap.setOnMarkerClickListener(clusterManager);

        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl(Util.CONSTANTS.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();


        MapBuilderWebService service = retrofit.create(MapBuilderWebService.class);
        Call<List<MapMarker>> repos = service.listMarkers();
        repos.enqueue(new Callback<List<MapMarker>>() {
            @Override
            public void onFailure(Call<List<MapMarker>> call, Throwable t) {
                Log.d("ERROR", "ERROR IN GETTING DATA");
            }

            @Override
            public void onResponse(Call<List<MapMarker>> call, Response<List<MapMarker>> response) {
                int counter = 0;
                Context context = getApplicationContext();
                for (MapMarker marker :
                        response.body()) {
                    counter++;
                    ClusterMarker cmarker = new ClusterMarker();
                    cmarker.setPosition(new LatLng(marker.getLatitude(), marker.getLongitude()));
                    cmarker.setTitle(marker.getTitle());
                    cmarker.setSnippet(marker.getSnippet());
                    cmarker.setIcon(Util.getBitmapDescriptor(R.drawable.ic_hairdresser, context));
                    clusterManager.addItem(cmarker);
                    if (counter % 10 == 0)
                        clusterManager.cluster();
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

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();

        mMap.setOnInfoWindowClickListener(this);
        mMap.setPadding(0, 0, 16, 16);
        mMap.setMinZoomPreference(5.0f);
        mMap.setMaxZoomPreference(20.0f);

        CustomInfoWindowAdapter adapter = new CustomInfoWindowAdapter(MapsActivity.this);
        mMap.setInfoWindowAdapter(adapter);

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(this, "Info window clicked",
                Toast.LENGTH_SHORT).show();
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
        markerOptions.title("Current Position");
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

                                mMap.addMarker(new MarkerOptions()
                                        .icon(Util.getBitmapDescriptor(R.drawable.ic_pin , getApplicationContext()))
                                        .title(getString(R.string.your_current_location))
                                        .flat(true)
                                        .anchor(0.5f, 1f)
                                        .position(lastKnownPosition));

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
