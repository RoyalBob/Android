package com.royalbob.httpurlconnectiontest;

/**
 * Created by RoyalBob on 2016/7/22.
 */
public class ContactInfo {
    private String city, province, supplier ;

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }

    public String getSupplier() {
        return supplier;
    }
    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    @Override
    public String toString() {
        return "City:" + city + "\nProvince:" + province + "\nSupplier:" + supplier ;
    }
}
