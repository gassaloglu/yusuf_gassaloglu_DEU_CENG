package pbl;

public class Address{
    private String street;
    private String number;
    private String district;
    private String province;
    private String country;

    public Address(String street, String number, String district, String province, String country) {
        this.street = street;
        this.number = number;
        this.district = district;
        this.province = province;
        this.country = country;
    }

    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public String getDistrict() {
        return district;
    }
    public void setDistrict(String district) {
        this.district = district;
    }
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
   

    

    
    


}
