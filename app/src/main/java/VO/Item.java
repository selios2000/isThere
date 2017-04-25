package VO;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2017-04-20.
 */

public class Item {
    private String item_code;
    private String item_name;
    private String item_price;
    private String item_type;
    private String item_info;
    private Drawable item_image;
    private String stock_stock;
    private int stock_sold;

    public Item(String item_code, String item_name, String item_price, String item_type, String item_info, String stock_stock) {
        this.item_code = item_code;
        this.item_name = item_name;
        this.item_price = item_price;
        this.item_type = item_type;
        this.item_info = item_info;
        this.stock_stock = stock_stock;
    }

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_price() {
        return item_price;
    }

    public void setItem_price(String item_price) {
        this.item_price = item_price;
    }

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public String getItem_info() {
        return item_info;
    }

    public void setItem_info(String item_info) {
        this.item_info = item_info;
    }

    public Drawable getItem_image() {
        return item_image;
    }

    public void setItem_image(Drawable item_image) {
        this.item_image = item_image;
    }

    public String getStock_stock() {
        return stock_stock;
    }

    public void setStock_stock(String stock_stock) {
        this.stock_stock = stock_stock;
    }

    public int getStock_sold() {
        return stock_sold;
    }

    public void setStock_sold(int stock_sold) {
        this.stock_sold = stock_sold;
    }
}
