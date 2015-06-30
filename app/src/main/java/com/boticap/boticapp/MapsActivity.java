package com.boticap.boticapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import BD.helper.DatabaseHelper;
import BD.model.Farmacia;

public class MapsActivity extends ActionBarActivity implements LocationListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    Context contexto;
    DatabaseHelper db;
    Location location = null;
    Double latitude;
    Double longitude;
    LocationManager locationManager;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;
    Map haspMap = new HashMap();
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        mMap.setMyLocationEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        actionBar = getSupportActionBar();
        actionBar.setTitle("BoticApp");
        actionBar.setDisplayHomeAsUpEnabled(true);

        contexto = this;
        db = new DatabaseHelper(getApplicationContext());
        List<Farmacia> listaFarmacias;

        //evalua si está encendido el gps
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        String provider = lm.getBestProvider(new Criteria(), true);
        if(provider.equals("passive")){
            dialogGPS();
        }
        else{
            onProviderEnabled(provider);
        }

        final Intent intent = getIntent();

        //Si el intent viene desde el buscador de medicamento, se va a mostrar solamente las farmacias que tienen un X medicamento
        if (intent.getLongExtra("id_remedio_seleccionado", 0) != 0){
            long id = intent.getLongExtra("id_remedio_seleccionado", 0);
            listaFarmacias = db.getFarmaciasConXRemedio(id);
            for (int i = 0; i < listaFarmacias.size(); i++) {
                String[] pos = listaFarmacias.get(i).getPosicion().split(" ");
                LatLng latLong = new LatLng(Double.valueOf(pos[0]), Double.valueOf(pos[1]));
                //se agrega un marcador en el mapa
                Marker m = mMap.addMarker(new MarkerOptions()
                                    .title(listaFarmacias.get(i).getNombre())
                                    .snippet(listaFarmacias.get(i).getDireccion())
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                                    .position(latLong)

                );
                //se crea un map donde se relaciona un marker con el id de la farmacia
                haspMap.put(m.getId(), listaFarmacias.get(i).getId());
            }

            //marcador clickeable
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Intent miIntent = new Intent(contexto, MedicamentoEnFarmacia.class);
                    int id_farmacia_seleccionada = (Integer) haspMap.get(marker.getId());
                    //se envia el id de la farmacia seleccionada
                    miIntent.putExtra("id_farmacia_seleccionada", id_farmacia_seleccionada);
                    long id_remedio_seleccionado = intent.getLongExtra("id_remedio_seleccionado",0);
                    //se envia el id del remedio seleccionado
                    miIntent.putExtra("id_remedio", id_remedio_seleccionado);
                    startActivity(miIntent);
                }
            });
        }
        //si el intent viene del buscador de farmacias, se mostraran todas las farmacias
        else {

            listaFarmacias = db.getAllFarmacias();

            for (int i = 0; i < listaFarmacias.size(); i++) {
                String[] pos = listaFarmacias.get(i).getPosicion().split(" ");
                LatLng latLong = new LatLng(Double.valueOf(pos[0]), Double.valueOf(pos[1]));
                mMap.addMarker(new MarkerOptions()
                                .title(listaFarmacias.get(i).getNombre())
                                .snippet(listaFarmacias.get(i).getDireccion())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                                .position(latLong)
                );
            }
        }
        //this.deleteDatabase("boticapp.db");
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) this.getApplicationContext()
                    .getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                this.canGetLocation = false;
            } else {
                this.canGetLocation = true;
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            60000,
                            100, this);
                    Log.d("Network", "Network Enabled");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                               60000,
                                100, this);
                        Log.d("GPS", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }


    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    public void dialogGPS(){
        AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
        builder.setTitle("Gps desactivado");
        builder.setCancelable(false);
        builder.setPositiveButton("Activar GPS", new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialog, int which){
                Intent startGps = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(startGps);
            }
        });
        builder.setNegativeButton("Dejar el GPS apagado", new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialog, int which){
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void moverCamara(){
        Location ubicacion = getLocation();
        if (ubicacion != null) {
            CameraUpdate camera = CameraUpdateFactory.newLatLng(new LatLng(ubicacion.getLatitude(), ubicacion.getLongitude()));
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(14);
            mMap.moveCamera(camera);
            mMap.animateCamera(zoom);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        if (status == 2){//disponible
            moverCamara();
        }
        else{
            //esperar al cambio de status
        }
    }

    @Override
    public void onProviderEnabled(String provider) {
        moverCamara();
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_maps_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, TipoDeBusqueda.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
