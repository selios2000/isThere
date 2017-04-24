package VO;

/**
 * Created by Administrator on 2017-04-15.
 */

public class Shop {
    private String shop_id;
    private String shop_name;
    private double shop_lat;
    private double shop_lng;
    private String shop_type;
    private String shop_info;
    private String shop_vendor;
    private double distance;
    private int stock_stock;


    public Shop(String shop_id, String shop_name, double shop_lat, double shop_lng, String shop_type , String shop_info, String shop_vendor, double shop_distance, int stock_stock) {
        this.shop_id = shop_id;
        this.shop_name = shop_name;
        this.shop_lat = shop_lat;
        this.shop_lng = shop_lng;
        this.shop_type = shop_type;
        this.shop_info = shop_info;
        this.shop_vendor = shop_vendor;
        this.stock_stock = stock_stock;
    }

    public String getShop_id() {
        return shop_id;
    }
    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }
    public String getShop_name() {
        return shop_name;
    }
    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }
    public double getShop_lat() {
        return shop_lat;
    }
    public void setShop_lat(double shop_lat) {
        this.shop_lat = shop_lat;
    }
    public double getShop_lng() {
        return shop_lng;
    }
    public void setShop_lng(double shop_lng) {
        this.shop_lng = shop_lng;
    }
    public String getShop_type() {
        return shop_type;
    }
    public void setShop_type(String shop_type) {
        this.shop_type = shop_type;
    }
    public String getShop_info() {
        return shop_info;
    }
    public void setShop_info(String shop_info) {
        this.shop_info = shop_info;
    }
    public String getShop_vendor() {
        return shop_vendor;
    }
    public void setShop_vendor(String shop_vendor) {
        this.shop_vendor = shop_vendor;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getStock_stock() {
        return stock_stock;
    }

    public void setStock_stock(int stock_stock) {
        this.stock_stock = stock_stock;
    }
}
