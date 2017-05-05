package uk.tonymerritt.bootcamplocator.model;

/**
 * Created by Klavar on 04/05/2017.
 */

public class Klavar {

    final String DRAWABLE = "drawable/";

    public  String getImageUrl() {
        return DRAWABLE + locationImageUrl;
    }


    public float getLongitude() {
        return longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public String getLocationTitle() {
        return locationTitle;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public String getLocationImageUrl() {
        return locationImageUrl;
    }

    private float longitude;
    private float latitude;
    private String locationTitle;
    private String locationAddress;
    private String locationImageUrl;

    public Klavar(float latitude, float longitude, String locationTitle, String locationAddress, String locationImageUrl) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.locationTitle = locationTitle;
        this.locationAddress = locationAddress;
        this.locationImageUrl = locationImageUrl;
    }


}
