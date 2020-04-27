package com.endpoint.giveme.models;

import android.location.Location;

import com.google.android.libraries.places.api.internal.impl.net.pablo.PlaceResult;

import java.io.Serializable;

public class LocationModel implements Serializable {

   static Location location;

    public static Location getLocation() {
        return location;
    }

    public static void setLocation(Location location) {
        LocationModel.location = location;
    }
}
