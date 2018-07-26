public class Location {

    private short volumeIntensity;
    private VolumeSetting VolumeSetting;
    private float latitude;
    private float longitude;
    private String name;
    private StreetAddress address;


    public short getVolumeIntensity() {
        return volumeIntensity;
    }

    public void setVolumeIntensity(short volumeIntensity) {
        if (volumeIntensity >= 10){
            this.volumeIntensity = 10;
        }
        else if (volumeIntensity <= 0) {
            this.volumeIntensity = 0;
        }
        else {
            this.volumeIntensity = volumeIntensity;
        }
    }

    public VolumeSetting getVolumeSetting() {
        return VolumeSetting;
    }

    public void setVolumeSetting(VolumeSetting volumeSetting) {
        VolumeSetting = volumeSetting;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public StreetAddress getAddress() {
        return address;
    }

    public void setAddress(StreetAddress address) {
        this.address = address;
    }



}
