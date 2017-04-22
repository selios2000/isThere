package samsung.sec.com.isthere;

/**
 * Created by 빠빵 on 2017-04-23.
 */

public class DataCurrentItem {
    private String itemid_current;
    private String itemcount_current;
    private String martname_current;
    private String martposition_current;
    private String martdistance_current;
    private String item_img_current;

    public DataCurrentItem(String itemid,String itemcount,String imgcurrent,String martname,String martposition,String martdistance){
        itemid_current=itemid;
        itemcount_current=itemcount;
         martname_current=martname;
        martposition_current=martposition;
        martdistance_current=martdistance;
        item_img_current=imgcurrent;
    }
    public String getItemid_current() {
        return itemid_current;
    }

    public void setItemid_current(String itemid_current) {
        this.itemid_current = itemid_current;
    }

    public String getItemcount_current() {
        return itemcount_current;
    }

    public void setItemcount_current(String itemcount_current) {
        this.itemcount_current = itemcount_current;
    }

    public String getMartname_current() {
        return martname_current;
    }

    public void setMartname_current(String martname_current) {
        this.martname_current = martname_current;
    }

    public String getMartposition_current() {
        return martposition_current;
    }

    public void setMartposition_current(String martposition_current) {
        this.martposition_current = martposition_current;
    }

    public String getMartdistance_current() {
        return martdistance_current;
    }

    public void setMartdistance_current(String martdistance_current) {
        this.martdistance_current = martdistance_current;
    }

    public String getItem_img_current() {
        return item_img_current;
    }

    public void setItem_img_current(String item_img_current) {
        this.item_img_current = item_img_current;
    }







}
