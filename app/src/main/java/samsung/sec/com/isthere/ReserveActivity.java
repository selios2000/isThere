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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import Network.NetworkTask;
import common.RoundedAvatarDrawable;
import common.Scan;

import static common.Scan.BR_ItemList;
import static common.Scan.HTTP_ACTION_ITEMLIST;
import static common.Scan.KEY_ItemList;
import static common.Scan.itemImage;
import static common.Scan.selectedUrl;

public class ReserveActivity extends AppCompatActivity {

    private String shop_id, item_name, item_code;
    private TextView textView_itemName, textView_itemPrice;
    private ImageView imageView_itemImage;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        context = this;
        textView_itemName = (TextView)findViewById(R.id.textView_itemName);
        textView_itemPrice = (TextView)findViewById(R.id.textView_itemPrice);
        imageView_itemImage = (ImageView) findViewById(R.id.imageView_itemImage);

        item_name = getIntent().getStringExtra("item_name");
        shop_id = getIntent().getStringExtra("shop_id");

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("예약하기");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        getShopStock();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(mItemListBR);
    }

    private void getShopStock() {
        NetworkTask networkTask = new NetworkTask(context, HTTP_ACTION_ITEMLIST, Scan.itemResv);
        Map<String, String> params = new HashMap<String, String>();
        params.put("shop_id", shop_id);
        params.put("item_name", item_name);
        networkTask.execute(params);
    }

    BroadcastReceiver mItemListBR = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            try {
                JSONArray jArray = new JSONArray(intent.getStringExtra(KEY_ItemList));
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject oneObject = jArray.getJSONObject(i);
                    textView_itemName.setText(item_name + "   (재고 : " + oneObject.getString("stock_stock") + ")");
                    textView_itemPrice.setText(oneObject.getString("item_price")+ " 원");
                    item_code = oneObject.getString("item_code");
                    new LoadImage(item_code).execute();
                }
            } catch (JSONException e) {
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
               RoundedAvatarDrawable tmpRoundedAvatarDrawable = new RoundedAvatarDrawable(image);
                imageView_itemImage.setImageDrawable(tmpRoundedAvatarDrawable);
                Log.i("Image download", "Download complete!!");
                imageView_itemImage.postInvalidate();
            } else {
                Toast.makeText(context, "이미지가 존재하지 않습니다.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
