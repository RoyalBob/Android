package com.royalbob.listviewhighlight;

/**
 * Created by RoyalBob on 2016/7/21.
 */
public class Data {
    private String name;
    private String en;
    private String sen;

    public Data(String name, String en, String sen) {
        this.name = name;
        this.en = en;
        this.sen = sen;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEn() {
        return en;
    }
    public void setEn(String en) {
        this.en = en;
    }
    public String getSen() {
        return sen;
    }
    public void setSen(String sen) {
        this.sen = sen;
    }

}