package samsung.sec.com.isthere;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Network.NetworkTask;
import VO.Shop;
import common.Scan;

import static common.Scan.BR_ItemList;
import static common.Scan.BR_ShopList;
import static common.Scan.HTTP_ACTION_ITEMLIST;
import static common.Scan.HTTP_ACTION_SHOPLIST;
import static common.Scan.KEY_ItemList;
import static common.Scan.KEY_ShopList;
import static common.Scan.itemLimit;
import static common.Scan.lat;
import static common.Scan.lng;
import static common.Scan.scanDist;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Context context;
    private EditText editText;
    private SlidingUpPanelLayout sl;
    private RecyclerView mRecyclerView;
    private ArrayList<String> mArrayList;
    private DataAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FrameLayout rlayout;

    private RecyclerView mRecyclerViewListItem_current;
    private DataCurrentItem_Adapter mAdapterList_current;
    private RecyclerView.LayoutManager layoutManager_list_current;

    private ArrayList<String> mArrayListitemSoldTop;
    private DataListAdapter mAdapterListitemSoldTop;
    private RecyclerView mRecyclerViewListItemSoldTop;

    private ArrayList<Shop> shops;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main_sliding);
        SlidingUpPanelLayout mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mLayout.setCoveredFadeColor(Color.TRANSPARENT);
        shops = new ArrayList<Shop>();
        /*TextView btn3= (TextView)findViewById(R.id.textViewReserve1);

        btn3.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReserveActivity.class);
                startActivity(intent);
            }
        });*/
        rlayout = (FrameLayout) findViewById(R.id.contentLayout);
       /* ImageView img1= (ImageView)findViewById(R.id.imgGs25);
        img1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MapsActivity.class);
                //지도 액티비티로 이동
                startActivity(intent);
            }
        });*/
        // search view
        initViews();
        getListItem_frequent();
        getListitem_popular();
        getListitem_current();
        sl = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        sl.requestFocus();
        sl.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                TextView product = (TextView) findViewById(R.id.textIstheretemplate);
                //ImageView imageView2= (ImageView)findViewById(R.id.imageView2);

             //   LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(imageView2.getLayoutParams().width, imageView2.getLayoutParams().height);
              //  Log.d("donggeon",""+imageView2.getLayoutParams().width);
              //  Log.d("donggeon",""+imageView2.getLayoutParams().height);
              //  lp.setMargins(0, 250, 0, 0);
                if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    product.setVisibility(View.VISIBLE);
                 //   imageView2.setPadding(0,250,0,0);
                   // imageView2.setLayoutParams(lp);
                } else
                    product.setVisibility(View.INVISIBLE);
                 //   imageView2.setPadding(0,0,0,0);
            }
        });
        SearchView sr = (SearchView) findViewById(R.id.searchview1);
        View searchplate = (View)sr.findViewById(android.support.v7.appcompat.R.id.search_plate);
        searchplate.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        sr.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    sl.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    rlayout.setVisibility(View.INVISIBLE);
                } else {
                    mRecyclerView.setVisibility(View.INVISIBLE);
                    rlayout.setVisibility(View.VISIBLE);
                }
            }
        });
        sr.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("donggeon", query);
                mArrayList.add(query);
                Intent intent = new Intent(context, MapsActivity.class);
                intent.putExtra("itemname", query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                return true;
            }
        });
        ImageView imageView2= (ImageView)findViewById(R.id.imageView2);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                View drawerView =(View)findViewById(R.id.nav_view);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(drawerView);
                }
            }
        });
    }

    private void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.notice_search_recycler);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mArrayList = new ArrayList<>();
        mArrayList.add("하이네켄");
        mAdapter = new DataAdapter(mArrayList, context);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setVisibility(View.INVISIBLE);

    }

    private void getListItem_frequent() {

        RecyclerView mRecyclerViewListItem;
        ArrayList<String> mArrayListitem;
        DataListAdapter mAdapterList;
        RecyclerView.LayoutManager layoutManager_list;

        mRecyclerViewListItem = (RecyclerView) findViewById(R.id.recycle_frequent);
        mRecyclerViewListItem.setHasFixedSize(true);
        layoutManager_list = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewListItem.setLayoutManager(layoutManager_list);
        mArrayListitem = new ArrayList<>();
        mArrayListitem.add("하이네켄");
        mArrayListitem.add("트레비");
        mArrayListitem.add("버드와이저");
        mArrayListitem.add("필스너");
        mArrayListitem.add("토레타");
        mAdapterList = new DataListAdapter(mArrayListitem, context);
        mRecyclerViewListItem.setAdapter(mAdapterList);
    }
    //실시간 인기 급상승 품목목
   private void getListitem_popular() {
        RecyclerView.LayoutManager layoutManager_list_popular;
        mRecyclerViewListItemSoldTop = (RecyclerView) findViewById(R.id.recycle_popular);
        mRecyclerViewListItemSoldTop .setHasFixedSize(true);
        layoutManager_list_popular = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewListItemSoldTop.setLayoutManager(layoutManager_list_popular);
       mArrayListitemSoldTop = new ArrayList<>();
    }

    private void getListitem_current() {
        mRecyclerViewListItem_current = (RecyclerView) findViewById(R.id.recycle_current);
        mRecyclerViewListItem_current.setHasFixedSize(true);
        layoutManager_list_current = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewListItem_current.setLayoutManager(layoutManager_list_current);
        //public DataCurrentItem(String itemid,String itemcount,String imgcurrent,String martname,String martposition,String martdistance){
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //서버에서 Shop 리스트 목록을 리시버로 받음 (NetworkTask)
    @Override
    public void onResume() {
        super.onResume();
        IntentFilter shopfilter = new IntentFilter();
        shopfilter.addAction(BR_ShopList);
        registerReceiver(mShopListBR, shopfilter);
        getShopList();

        IntentFilter itemfilter = new IntentFilter();
        itemfilter.addAction(BR_ItemList);
        registerReceiver(mItemListBR, itemfilter);
        getSoldTopList();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(mShopListBR);
        unregisterReceiver(mItemListBR);
    }
    //리시버는 항상 해제 해줘야함

    BroadcastReceiver mShopListBR = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            try {

                JSONArray jArray = new JSONArray(intent.getStringExtra(KEY_ShopList));
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject oneObject = jArray.getJSONObject(i); // Pulling items from the array
                    String shop_id = oneObject.getString("shop_id");
                    String shop_name = oneObject.getString("shop_name");
                    Double marker_lat = oneObject.getDouble("shop_lat");
                    Double marker_lng = oneObject.getDouble("shop_lng");
                    int shop_distance = (int) (oneObject.getDouble("distance") * 1000.0d);
                    String item_soldtop = oneObject.getString("shop_soldtop");
                    Shop shop = new Shop(shop_id, shop_name, marker_lat, marker_lng, oneObject.getString("shop_type"), oneObject.getString("shop_info"), oneObject.getString("shop_vendor"), shop_distance, 0, item_soldtop);
                    shops.add(shop);
                    if (mAdapterList_current == null) {
                        mAdapterList_current = new DataCurrentItem_Adapter(shops, context);
                        mRecyclerViewListItem_current.setAdapter(mAdapterList_current);
                    }
                }
            } catch (JSONException e) {
                Log.e("JSON Parsing error", e.toString());
            }
        }
    };

    private void getShopList() {
        NetworkTask networkTask = new NetworkTask(context, HTTP_ACTION_SHOPLIST, Scan.shopScan);
        Map<String, String> params = new HashMap<String, String>();
        params.put("lat", lat);
        params.put("lng", lng);
        params.put("dist", scanDist);
        networkTask.execute(params);
    }
    BroadcastReceiver mItemListBR = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            try {
                JSONArray jArray = new JSONArray(intent.getStringExtra(KEY_ItemList));
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject oneObject = jArray.getJSONObject(i); // Pulling items from the array
                    String item_name = oneObject.getString("item_name");
                    mArrayListitemSoldTop.add(item_name);
                    if (mAdapterListitemSoldTop == null) {
                        mAdapterListitemSoldTop = new DataListAdapter(mArrayListitemSoldTop, context);
                        mRecyclerViewListItemSoldTop.setAdapter(mAdapterListitemSoldTop);
                    }
                }
            } catch (JSONException e) {
                Log.e("JSON Parsing error", e.toString());
            }
        }
    };

    private void getSoldTopList() {
        NetworkTask networkTask = new NetworkTask(context, HTTP_ACTION_ITEMLIST, Scan.itemSoldTop);
        Map<String, String> params = new HashMap<String, String>();
        params.put("item_limit", itemLimit);
        networkTask.execute(params);
    }
}
