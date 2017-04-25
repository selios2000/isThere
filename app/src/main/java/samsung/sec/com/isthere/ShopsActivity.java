package samsung.sec.com.isthere;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import Network.NetworkTask;
import VO.Item;
import adapter.ListViewAdapter;
import common.RoundedAvatarDrawable;
import common.Scan;

import static common.Scan.BR_ItemList;
import static common.Scan.HTTP_ACTION_ITEMLIST;
import static common.Scan.KEY_ItemList;
import static common.Scan.itemImage;
import static common.Scan.selectedUrl;

public class ShopsActivity extends AppCompatActivity {

    private String shop_id, martname_current, martposition_current;
    private TextView textView_itemName, textView_itemPrice, textView_shopName;
    private ImageView imageView_itemImage;
    private Context context;

    private ArrayList<Item> itemList;
    private ListView listview ;
    private ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        context = this;
        textView_itemName = (TextView)findViewById(R.id.textView_itemName);
        textView_itemPrice = (TextView)findViewById(R.id.textView_itemPrice);
        textView_shopName=  (TextView)findViewById(R.id.shopname);

        imageView_itemImage = (ImageView) findViewById(R.id.imageView_itemImage);

        //set current martname and position
        martname_current = getIntent().getStringExtra("martname_current");
        martposition_current = getIntent().getStringExtra("martposition_current");
        String titleText=martname_current+ " "+ martposition_current;
        textView_shopName.setText(titleText);

        shop_id = getIntent().getStringExtra("shop_id");
        itemList = new ArrayList<Item>();
        adapter = new ListViewAdapter() ;
        listview = (ListView) findViewById(R.id.listview_item);
        listview.setAdapter(adapter);
        listview.setEmptyView((TextView)findViewById(R.id.textview_empty));

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(titleText);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NetworkTask networkTask = new NetworkTask(context, HTTP_ACTION_ITEMLIST, Scan.itemList);
        Map<String, String> params = new HashMap<String, String>();
        params.put("shop_id", shop_id);
        networkTask.execute(params);
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

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter itemfilter = new IntentFilter();
        itemfilter.addAction(BR_ItemList);
        registerReceiver(mItemListBR, itemfilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(mItemListBR);
    }

    BroadcastReceiver mItemListBR = new BroadcastReceiver(){
        public void onReceive(Context context, Intent intent){
            try {

                JSONArray jArray = new JSONArray(intent.getStringExtra(KEY_ItemList));
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject oneObject = jArray.getJSONObject(i); // Pulling items from the array
                    String item_code = oneObject.getString("item_code");
                    String item_name = oneObject.getString("item_name");
                    String item_price = oneObject.getString("item_price");
                    String item_type = oneObject.getString("item_type");
                    String item_info = oneObject.getString("item_info");
                    String stock_stock = oneObject.getString("stock_stock");
                    Item item = new Item(item_code, item_name, item_price, item_type, item_info, stock_stock);
                    item.setStock_sold(oneObject.getInt("stock_sold"));
                    new LoadImage(item_code).execute();
                    itemList.add(item);
                    adapter.addItem(item);
                }
                getItemListfromShop();
            }catch(JSONException e){
                Log.e("JSON Parsing error", e.toString());
            }
        }
    };

    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        private Bitmap item_image;
        private String item_code, url;
        public LoadImage(String item_code){
            this.item_code = item_code;
            url = selectedUrl+itemImage+item_code;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Bitmap doInBackground(String... args) {
            Log.i("Image download", "Request URL : "+url);
            try {
                item_image = BitmapFactory
                        .decodeStream((InputStream) new URL(url)
                                .getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return item_image;
        }

        protected void onPostExecute(Bitmap image) {
            if (image != null) {
                for (Item i : itemList){
                    if(i.getItem_code().equals(item_code)) {
                        RoundedAvatarDrawable tmpRoundedAvatarDrawable = new RoundedAvatarDrawable(image);
                        i.setItem_image(tmpRoundedAvatarDrawable);
                        adapter.notifyDataSetChanged();
                    }
                }
                Log.i("Image download", "Download complete!!");
            } else {
                Toast.makeText(context, "이미지가 존재하지 않습니다.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getItemListfromShop() {

        RecyclerView mRecyclerViewListItem;
        ArrayList<String> mArrayListitem;
        DataListAdapter mAdapterList;
        RecyclerView.LayoutManager layoutManager_list;

        mRecyclerViewListItem = (RecyclerView) findViewById(R.id.recycle_shopPopular);
        mRecyclerViewListItem.setHasFixedSize(true);
        layoutManager_list = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewListItem.setLayoutManager(layoutManager_list);

        Collections.sort(itemList, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return (o1.getStock_sold() < o2.getStock_sold()) ? -1: (o1.getStock_sold() > o2.getStock_sold()) ? 1:0 ;
            }
        });

        mArrayListitem = new ArrayList<>();
        for (int i=0; (i<itemList.size()) && i <5; i++ ){
            mArrayListitem.add(itemList.get(i).getItem_name());
        }
        mAdapterList = new DataListAdapter(mArrayListitem, context);
        mRecyclerViewListItem.setAdapter(mAdapterList);
    }

}
