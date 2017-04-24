package samsung.sec.com.isthere;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
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

public class DataCurrentItem_Adapter extends RecyclerView.Adapter<DataCurrentItem_Adapter.ViewHolder> implements Filterable {
    private ArrayList<Shop> mArrayList;
    private ArrayList<Shop> mFilteredList;
    private Context context;
    private String itemName;

    public DataCurrentItem_Adapter(ArrayList<Shop> arrayList, Context appcontext, String item_name) {
        mArrayList = arrayList;
        mFilteredList = arrayList;
        context = appcontext;
        itemName = item_name;
    }

    @Override
    public DataCurrentItem_Adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.searchlist_current_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataCurrentItem_Adapter.ViewHolder viewHolder, int i) {
        viewHolder.itemid_current.setText(itemName);

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

        viewHolder.itemcount_current.setText(String.valueOf(mFilteredList.get(i).getStock_stock()));
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
        private TextView martdistance_current;
        private LinearLayout itemidlayoutlist;
        private TextView textViewReserve1;

        public ViewHolder(View view) {
            super(view);
            itemid_current = (TextView) view.findViewById(R.id.itemid_current);
            itemcount_current = (TextView) view.findViewById(R.id.itemcount_current);
            item_img_current = (ImageView) view.findViewById(R.id.item_img_current);
            martname_current = (TextView) view.findViewById(R.id.martname_current);
            martposition_current = (TextView) view.findViewById(R.id.martposition_current);
            martdistance_current = (TextView) view.findViewById(R.id.martdistance_current);
            textViewReserve1 = (TextView) view.findViewById(R.id.textViewReserve1);
            textViewReserve1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ReserveActivity.class);
                    String  item_name = itemid_current.getText().toString();
                    String  shop_id= itemid_current.getText().toString();
                    intent.putExtra("itemname",itemName);
                    context.startActivity(intent);
                }
            });
        }
    }
}