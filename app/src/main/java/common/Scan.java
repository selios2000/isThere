package common;

/**
 * Created by Administrator on 2017-04-12.
 */

public class Scan {

    public static String localUrl = "http://192.168.0.5:8080/isthere";
    public static String awsUrl = "http://52.78.243.10:8080/isthere";
    public static String selectedUrl = awsUrl;

    public static String shopAdd = "/shop/add";
    public static String shopScan = "/shop/scan";
    public static String shopDelete = "/shop/delete";
    public static String shopScanByItem = "/shop/scanByItem";

    public static String stockUpdate = "/stock/update";
    public static String itemList = "/item/shopItemList";
    public static String itemImage = "/file?item_code="; //item 이미지를 받아옴
    public static String itemSoldTop = "/item/itemSoldTop"; //Main - 실시간 인기 급상승
    public static String itemResv = "/item/itemResv";

    public static String lat= "37.491",lng="127.020"; //해커톤 위,경도
    public static String scanDist = "1";  //기본 스캔반경
    public static String itemLimit = "5";  //기본 스캔반경

    public static String testItemName = "하이네켄";  //기본 스캔반경

    public static final String BR_ShopList = "samsung.sec.com.isthere.SHOP_LIST";
    public static final String BR_ItemList = "samsung.sec.com.isthere.ITEM_LIST";
    public static final String KEY_ShopList = "SHOP_LIST";
    public static final String KEY_ItemList = "ITEM_LIST";

    public static final String HTTP_RESPONSE_OK = "OK";
    public static final String HTTP_RESPONSE_FAIL = "FAIL";

    public static final int HTTP_ACTION_CMD = 0;
    public static final int HTTP_ACTION_SHOPLIST = 1;
    public static final int HTTP_ACTION_ITEMLIST = 2;
}
