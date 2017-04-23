package samsung.sec.com.isthere;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main_sliding);
        SlidingUpPanelLayout mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mLayout.setCoveredFadeColor(Color.TRANSPARENT);
        /*TextView btn3= (TextView)findViewById(R.id.textViewReserve1);

        btn3.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReserveActivity.class);
                startActivity(intent);
            }
        });*/
        rlayout = (FrameLayout)findViewById(R.id.contentLayout);
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
        sl =(SlidingUpPanelLayout)findViewById(R.id.sliding_layout);
        sl.requestFocus();

        SearchView sr= (SearchView)findViewById(R.id.searchview1);
        sr.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    sl.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    rlayout.setVisibility(View.INVISIBLE);
                }
                else{
                    mRecyclerView.setVisibility(View.INVISIBLE);
                    rlayout.setVisibility(View.VISIBLE);
                }
            }
        });
        sr.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("donggeon",query);
                mArrayList.add(query);
                Intent intent = new Intent(context, MapsActivity.class);
                intent.putExtra("itemname",query);
                startActivity(intent);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                return true;
            }
        });

    }
    private void initViews(){
        mRecyclerView = (RecyclerView)findViewById(R.id.notice_search_recycler);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mArrayList= new ArrayList<>();
        mArrayList.add("하이네켄");
        mAdapter = new DataAdapter(mArrayList,context);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setVisibility(View.INVISIBLE);

    }

    private void getListItem_frequent(){

        RecyclerView mRecyclerViewListItem;
        ArrayList<String> mArrayListitem;
        DataListAdapter mAdapterList;
        RecyclerView.LayoutManager layoutManager_list;

        mRecyclerViewListItem = (RecyclerView)findViewById(R.id.recycle_frequent);
        mRecyclerViewListItem.setHasFixedSize(true);
        layoutManager_list = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mRecyclerViewListItem.setLayoutManager(layoutManager_list);
        mArrayListitem= new ArrayList<>();
        mArrayListitem.add("하이네켄");
        mArrayListitem.add("하이네켄2");
        mArrayListitem.add("하이네켄3");
        mArrayListitem.add("조청 유과");
        mAdapterList = new DataListAdapter(mArrayListitem,context);
        mRecyclerViewListItem.setAdapter(mAdapterList);
    }

    private void getListitem_popular(){

        RecyclerView mRecyclerViewListItem_popular;
        ArrayList<String> mArrayListitem_popular;
        DataListAdapter mAdapterList_popular;
        RecyclerView.LayoutManager layoutManager_list_popular;

        mRecyclerViewListItem_popular = (RecyclerView)findViewById(R.id.recycle_popular);
        mRecyclerViewListItem_popular.setHasFixedSize(true);
        layoutManager_list_popular = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mRecyclerViewListItem_popular.setLayoutManager(layoutManager_list_popular);
        mArrayListitem_popular= new ArrayList<>();
        mArrayListitem_popular.add("하이네켄");
        mArrayListitem_popular.add("하이네켄2");
        mArrayListitem_popular.add("하이네켄3");
        mArrayListitem_popular.add("조청 유과");
        mAdapterList_popular = new DataListAdapter(mArrayListitem_popular,context);
        mRecyclerViewListItem_popular.setAdapter(mAdapterList_popular);
    }
    private void getListitem_current(){

        RecyclerView mRecyclerViewListItem_current;
        ArrayList<DataCurrentItem> mArrayListitem_current;
        DataCurrentItem_Adapter mAdapterList_current;
        RecyclerView.LayoutManager layoutManager_list_current;

        mRecyclerViewListItem_current = (RecyclerView)findViewById(R.id.recycle_current);
        mRecyclerViewListItem_current.setHasFixedSize(true);
        layoutManager_list_current = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mRecyclerViewListItem_current.setLayoutManager(layoutManager_list_current);
        mArrayListitem_current= new ArrayList<DataCurrentItem>();
        //public DataCurrentItem(String itemid,String itemcount,String imgcurrent,String martname,String martposition,String martdistance){
        mArrayListitem_current.add(new DataCurrentItem("하이네켄","12","gs25","GS25","서초타워점","200 M"));
        mAdapterList_current = new DataCurrentItem_Adapter(mArrayListitem_current,context);
        mRecyclerViewListItem_current.setAdapter(mAdapterList_current);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    protected void onResume() {
        super.onResume();
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
}
