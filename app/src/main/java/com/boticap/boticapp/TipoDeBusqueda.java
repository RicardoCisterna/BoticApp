package com.boticap.boticapp;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;

import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;

import android.widget.Toast;



public class TipoDeBusqueda extends ActionBarActivity implements  OnQueryTextListener, OnActionExpandListener {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        context = getApplicationContext();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("BoticApp");

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        /**CREAR TABS**/
        ActionBar.Tab tab = actionBar.newTab()
                .setText("Medicamento")
                .setTabListener(new TabsListener(
                        this, "medicamentos", FragmentMedicamento.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText("Farmacia")
                .setTabListener(new TabsListener(
                        this, "farmacia", FragmentFarmacia.class));
        actionBar.addTab(tab);

    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }

    public class TabsListener implements ActionBar.TabListener{

        private Fragment fragment;
        private final String tag;

        public TabsListener(Activity activity, String tag, Class cls){
            this.tag = tag;
            fragment = Fragment.instantiate(activity, cls.getName());
        }

        @Override
        public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
            ft.replace(android.R.id.content, fragment, tag);
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
            ft.remove(fragment);
        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tipo_de_busqueda, menu);
        setContentView(R.layout.activity_tipo_de_busqueda);
        getMenuInflater().inflate(R.menu.menu_busqueda_medicamento, menu);

        MenuItem searchItem = menu.findItem(R.id.menu3_buscar);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(searchItem, this);

        return super.onCreateOptionsMenu(menu);
    }
/* por ahora no tiene sentido
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */
}
