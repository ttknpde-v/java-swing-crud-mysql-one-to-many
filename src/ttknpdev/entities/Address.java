package ttknpdev.entities;

public class Address {
    private String aid;
    private String country;
    private String city;
    private String details;

    public Address(String aid, String country, String city, String details) {
        this.aid = aid;
        this.country = country;
        this.city = city;
        this.details = details;
    }

    public Address() {
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Address {" +
                "aid='" + aid + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
