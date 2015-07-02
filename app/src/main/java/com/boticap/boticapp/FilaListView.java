package com.boticap.boticapp;

import android.os.Parcel;
import android.os.Parcelable;

public class FilaListView implements Parcelable {

    private String itemName;
    private String precio;
    private String fecha;

    public FilaListView(String itemName, String precio, String fecha){
        this.itemName = itemName;
        this.precio = precio;
        this.fecha = fecha;
    }

    public String getItemName() {
        return itemName;
    }

    public String getPrecio() {
        return precio;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
