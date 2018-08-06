package com.example.dongwoo.produnt;

public class sellerData {
    private String seller_name;
    private String good_rate;
    private String bad_rate;
    private String data_volume;
    private String price;

    public sellerData(String seller_name, String good_rate, String bad_rate, String data_volume, String money) {
        this.seller_name = seller_name;
        this.good_rate = good_rate;
        this.bad_rate = bad_rate;
        this.data_volume = data_volume;
        this.price=money;
    }
    public String getSeller_name() {
        return seller_name;
    }
    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }
    public String getGood_rate() {
        return good_rate;
    }
    public void setGood_rate(String good_rate) {
        this.good_rate = good_rate;
    }
    public String getBad_rate() {
        return bad_rate;
    }
    public void setBad_rate(String bad_rate) {
        this.bad_rate = bad_rate;
    }
    public String getData_volume() {
        return data_volume;
    }
    public void setData_volume(String data_volume) {
        this.data_volume = data_volume;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String money) {
        this.price = money;
    }
}
