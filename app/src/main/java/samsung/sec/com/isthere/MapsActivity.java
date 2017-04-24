package samsung.sec.com.isthere;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Network.NetworkTask;
import VO.Shop;
import common.Scan;

import static common.Scan.BR_ShopList;
import static common.Scan.HTTP_ACTION_SHOPLIST;
import static common.Scan.KEY_ShopList;
import static common.Scan.lat;
import static common.Scan.lng;
import static common.Scan.scanDist;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Context context;
    private String mapsearchtext;
    private ArrayList<Shop> shops;
    private ArrayList<MarkerOptions> markers;

    private RecyclerView mRecyclerViewListItem_current;
    private DataCurrentItem_Adapter mAdapterList_current;
    private RecyclerView.LayoutManager layoutManager_list_current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Intent intent = getIntent();
        if(intent != null)
            mapsearchtext=intent.getStringExtra("itemname");
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(mapsearchtext);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getListitem_current();
    }

    @Override
    public void onResume(){
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BR_ShopList);
        registerReceiver(mShopListBR, filter);

    }
    @Override
    public void onPause(){
        super.onPause();
        unregisterReceiver(mShopListBR);
    }

    private void getShopByItem() {
        NetworkTask networkTask = new NetworkTask(context, HTTP_ACTION_SHOPLIST ,Scan.shopScanByItem);
        Map<String, String> params = new HashMap<String, String>();
        params.put("item_name", mapsearchtext);
        params.put("shop_lat", lat);
        params.put("shop_lng", lng);
        params.put("distance", scanDist);
        networkTask.execute(params);
    }

    BroadcastReceiver mShopListBR = new BroadcastReceiver(){
        public void onReceive(Context context, Intent intent){
            try {
                shops = new ArrayList<Shop>();
                markers = new ArrayList<MarkerOptions>();
                JSONArray jArray = new JSONArray(intent.getStringExtra(KEY_ShopList));
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject oneObject = jArray.getJSONObject(i); // Pulling items from the array
                    String shop_id = oneObject.getString("shop_id");
                    String shop_name = oneObject.getString("shop_name");
                    Double marker_lat = oneObject.getDouble("shop_lat");
                    Double marker_lng = oneObject.getDouble("shop_lng");
                    String shop_info = oneObject.getString("shop_info");
                    int shop_distance = (int) (oneObject.getDouble("distance")*1000);
                    int stock_stock = oneObject.getInt("stock_stock");
                    Shop shop = new Shop(shop_id, shop_name, marker_lat, marker_lng, oneObject.getString("shop_type"), oneObject.getString("shop_info"), oneObject.getString("shop_vendor"),shop_distance,stock_stock);
                    shops.add(shop);

                    mAdapterList_current = new DataCurrentItem_Adapter(shops,context, mapsearchtext);
                    mRecyclerViewListItem_current.setAdapter(mAdapterList_current);

                    MarkerOptions marker = new MarkerOptions();
                    marker.position(new LatLng(marker_lat, marker_lng)).title(shop_info).snippet(shop_name);
                    int height = 150;
                    int width = 150;
                    BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.stock_color);
                    Bitmap b=bitmapdraw.getBitmap();
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                    marker.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                    markers.add(marker);
                    Marker location = mMap.addMarker(marker);
                }
                if(shops.isEmpty())
                    Toast.makeText(context, mapsearchtext + " : 현재 사용자 주변에 재고가 없습니다.", Toast.LENGTH_LONG).show();
            }catch(JSONException e){
                Log.e("JSON Parsing error", e.toString());
            }
        }
    };


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getShopByItem();
        //37.491, 127.020
        LatLng currentLocation = new LatLng(37.491, 127.020);
        //마커를 원하는 이미지로 변경해줘야함
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLocation);
        markerOptions.title("BlueHack Hackathon");
        markerOptions.draggable(true);
        //markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        int height = 150;
        int width = 150;
        BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.stock_black);
        Bitmap b=bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
        //currentMarker = mGoogleMap.addMarker(markerOptions);
        //37.2512358
        //127.0187548
        mMap.addMarker(markerOptions);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));


        //상점 상품 상세 화면으로 이동
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            public boolean onMarkerClick(Marker marker) {

                return false;
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
     /*   MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                System.out.println(s);
                return false;
            }
        });*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getListitem_current(){
        mRecyclerViewListItem_current = (RecyclerView)findViewById(R.id.recycle_maps);
        mRecyclerViewListItem_current.setHasFixedSize(true);
        layoutManager_list_current = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mRecyclerViewListItem_current.setLayoutManager(layoutManager_list_current);
        //public DataCurrentItem(String itemid,String itemcount,String imgcurrent,String martname,String martposition,String martdistance){
        //mArrayListitem_current.add(new DataCurrentItem("하이네켄","12","gs25","GS25","서초타워점","200 M"));
    }
}