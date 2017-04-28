package samsung.sec.com.isthere;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import VO.Shop;

public class DataCurrentItem_Adapter extends RecyclerView.Adapter<DataCurrentItem_Adapter.ViewHolder> implements Filterable {
    private ArrayList<Shop> mArrayList;
    private ArrayList<Shop> mFilteredList;
    private static Context context;
    private String mapsearchtext = "";

    public DataCurrentItem_Adapter(ArrayList<Shop> arrayList, Context appcontext) {
        mArrayList = arrayList;
        mFilteredList = arrayList;
        context = appcontext;
    }
    public void setSearchItem(String mapsearchtext){
        this.mapsearchtext = mapsearchtext;
    }

    @Override
    public DataCurrentItem_Adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.searchlist_current_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataCurrentItem_Adapter.ViewHolder viewHolder, int i) {
        viewHolder.shop_id = mFilteredList.get(i).getShop_id();
        if(mapsearchtext.length() == 0)
            viewHolder.itemid_current.setText(mFilteredList.get(i).getItem_soldTop());
        else
            viewHolder.itemid_current.setText(mapsearchtext);

        Drawable drawable = context.getResources().getDrawable(R.drawable.conv_gs25, null);
        String shop_vendor = mFilteredList.get(i).getShop_vendor();
        if(shop_vendor.equals("GS25"))
            drawable = context.getResources().getDrawable(R.drawable.conv_gs25, null);
        else if(shop_vendor.equals("7Eleven"))
            drawable = context.getResources().getDrawable(R.drawable.conv_7e, null);
        else if(shop_vendor.equals("CU"))
            drawable = context.getResources().getDrawable(R.drawable.conv_cu, null);
        else if(shop_vendor.equals("Ministop"))
            drawable = context.getResources().getDrawable(R.drawable.conv_mini, null);
        viewHolder.item_img_current.setImageDrawable(drawable);

        String stock = "";
        if(mFilteredList.get(i).getStock_stock() > 0)
            stock = String.valueOf(mFilteredList.get(i).getStock_stock());

        viewHolder.martname_current.setText(mFilteredList.get(i).getShop_name());
        viewHolder.martposition_current.setText(mFilteredList.get(i).getShop_info());
        viewHolder.martdistance_current.setText(String.valueOf(mFilteredList.get(i).getDistance())+ "M");

        Resources r = context.getResources();
        if(mFilteredList.get(i).getItem_soldTop().contains("아사히"))
            viewHolder.frameLayoutUp.setBackground(r.getDrawable(R.drawable.asahi));
        if(mFilteredList.get(i).getItem_soldTop().contains("하이네켄"))
            viewHolder.frameLayoutUp.setBackground(r.getDrawable(R.drawable.hein));
        if(mFilteredList.get(i).getItem_soldTop().contains("포스틱"))
            viewHolder.frameLayoutUp.setBackground(r.getDrawable(R.drawable.potato));
        if(mFilteredList.get(i).getItem_soldTop().contains("대니쉬"))
            viewHolder.frameLayoutUp.setBackground(r.getDrawable(R.drawable.milk));
        if(mFilteredList.get(i).getItem_soldTop().contains("풀무"))
            viewHolder.frameLayoutUp.setBackground(r.getDrawable(R.drawable.pul));
        if(mFilteredList.get(i).getItem_soldTop().contains("윌"))
            viewHolder.frameLayoutUp.setBackground(r.getDrawable(R.drawable.will));
        if(mFilteredList.get(i).getItem_soldTop().contains("트레비"))
            viewHolder.frameLayoutUp.setBackground(r.getDrawable(R.drawable.trevi));
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    mFilteredList = mArrayList;
                } else {
                    ArrayList<Shop> filteredList = new ArrayList<>();
                    mFilteredList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<Shop>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView itemid_current;
        private ImageView item_img_current;
        private TextView martname_current;
        private TextView martposition_current;
        private TextView martdistance_current;
        private TextView textViewReserve1;
        private FrameLayout frameLayoutUp;
        public String shop_id;

        public ViewHolder(View view) {
            super(view);
            itemid_current = (TextView) view.findViewById(R.id.itemid_current);
            item_img_current = (ImageView) view.findViewById(R.id.item_img_current);
            martname_current = (TextView) view.findViewById(R.id.martname_current);
            martposition_current = (TextView) view.findViewById(R.id.martposition_current);
            martdistance_current = (TextView) view.findViewById(R.id.martdistance_current);
            textViewReserve1 = (TextView) view.findViewById(R.id.textViewReserve1);
            frameLayoutUp = (FrameLayout) view.findViewById(R.id.framelayout_up);
            item_img_current.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShopsActivity.class);
                    String item_name = itemid_current.getText().toString();
                    intent.putExtra("martname_current",martname_current.getText().toString());
                    intent.putExtra("martposition_current",martposition_current.getText().toString());
                    intent.putExtra("shop_id",shop_id);
                    context.startActivity(intent);
                }
            });
            textViewReserve1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ReserveActivity.class);
                    String  item_name = itemid_current.getText().toString();
                    intent.putExtra("item_name",item_name);
                    intent.putExtra("shop_id",shop_id);
                    context.startActivity(intent);
                }
            });
        }
    }


    private Drawable SampleView() {
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), R.drawable.test);
        int width = image.getWidth();
        int height = image.getHeight();
        int y = height / 3;
        Bitmap roundImage = Bitmap.createBitmap( image , 0, y, width, height-y*2 );

        return new BitmapDrawable(context.getResources(), roundImage);
    }

}

