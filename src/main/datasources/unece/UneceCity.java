package main.datasources.unece;

/**
 * This class is used to define UneceCity objects
 */
public class UneceCity {

    private final String code_a2;
    private final String city_code;
    private final String name;
    private final String latitude;
    private final String longitude;
    
    public UneceCity(String code_a2, String city_code, String name, String latitude, String longitude) {
        this.code_a2 = code_a2;
        this.city_code = city_code;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCode_a2() {
        return code_a2;
    }

    public String getCity_code() {
        return city_code;
    }

    public String getName() {
        return name;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

}