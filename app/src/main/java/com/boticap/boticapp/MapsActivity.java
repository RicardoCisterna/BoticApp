package com.boticap.boticapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements LocationListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private EditText txtNombre;
    private DataSource data;
    Context contexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        mMap.setMyLocationEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        contexto = this.getApplicationContext();

        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        String provider = lm.getBestProvider(new Criteria(), true);
        //System.out.println("MAIN: " + provider + " proveeeedor");
        if(provider.equals("passive")){
            onProviderDisabled(provider);
        }

        data = new DataSource(contexto);
        try {
            data.abrir();
        }
        catch (Exception e){
            Log.i("hola", "hola");
        }

        //data.agregarMarcadores(new Marcador("Farmacia Ahumada", "Farmacia La Florida", "-33.522645 -70.579160"));

        List<Marcador> m = data.obtenerMarcadores();
        for (int i=0; i < m.size(); i++){
            String[] pos = m.get(i).getCoordenadas().split(" ");
            LatLng lat = new LatLng(Double.valueOf(pos[0]), Double.valueOf(pos[1]));
            mMap.addMarker(new MarkerOptions()
                            .title(m.get(i).getTitulo())
                            .snippet(m.get(i).getFragmento())
                            .position(lat)
            );
        }

        data.cerrar();
        txtNombre = (EditText)findViewById(R.id.TxtNombre);
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

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
}
