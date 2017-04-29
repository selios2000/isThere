package samsung.sec.com.isthere;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import VO.Shop;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class DataCurrentItem_Adapter_back extends RecyclerView.Adapter<DataCurrentItem_Adapter_back.ViewHolder> implements Filterable {
    private ArrayList<Shop> mArrayList;
    private ArrayList<Shop> mFilteredList;
    private Context context;
    private String mapsearchtext = "";

    public DataCurrentItem_Adapter_back(ArrayList<Shop> arrayList, Context appcontext) {
        mArrayList = arrayList;
        mFilteredList = arrayList;
        context = appcontext;
    }
    public void setSearchItem(String mapsearchtext){
        this.mapsearchtext = mapsearchtext;
    }

    @Override
    public DataCurrentItem_Adapter_back.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.searchlist_current_row_back, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataCurrentItem_Adapter_back.ViewHolder viewHolder, int i) {
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
        else {
            viewHolder.itemcount_current.setVisibility(View.GONE);
           // viewHolder.itemcount_current_else.setVisibility(View.GONE);
        }

        viewHolder.itemcount_current.setText(stock+ " 개 남음");
        viewHolder.martname_current.setText(mFilteredList.get(i).getShop_name());
        viewHolder.martposition_current.setText(mFilteredList.get(i).getShop_info());
        viewHolder.martdistance_current.setText(String.valueOf(mFilteredList.get(i).getDistance())+ "M");
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
        private TextView itemcount_current;
        private ImageView item_img_current;
        private TextView martname_current;
        private TextView martposition_current;
        private TextView itemcount_current_else;
        private TextView martdistance_current;
        private LinearLayout itemListlayoutcurrentid;
        private TextView textViewReserve1;
        public String shop_id;

        public ViewHolder(View view) {
            super(view);
            itemid_current = (TextView) view.findViewById(R.id.itemid_current_back);
            itemcount_current = (TextView) view.findViewById(R.id.itemcount_current_back);
            item_img_current = (ImageView) view.findViewById(R.id.item_img_current_back);
            martname_current = (TextView) view.findViewById(R.id.martname_current_back);
            martposition_current = (TextView) view.findViewById(R.id.martposition_current_back);
            martdistance_current = (TextView) view.findViewById(R.id.martdistance_current_back);
            textViewReserve1 = (TextView) view.findViewById(R.id.textViewReserve1_back);
            itemListlayoutcurrentid= (LinearLayout) view.findViewById(R.id.itemListlayoutcurrentid);
            item_img_current.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShopsActivity.class);
                    String  item_name = itemid_current.getText().toString();
                    intent.putExtra("martname_current",martname_current.getText().toString());
                    intent.putExtra("martposition_current",martposition_current.getText().toString());
                    intent.putExtra("shop_id",shop_id);
                    context.startActivity(intent);
                }
            });
            martposition_current.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShopsActivity.class);
                    String  item_name = itemid_current.getText().toString();
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
}