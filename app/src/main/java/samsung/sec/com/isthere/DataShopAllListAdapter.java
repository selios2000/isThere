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

public class DataShopAllListAdapter extends RecyclerView.Adapter<DataShopAllListAdapter.ViewHolder> implements Filterable {
    private ArrayList<String> mArrayList;
    private ArrayList<String> mFilteredList;
    private Context context;
    public DataShopAllListAdapter(ArrayList<String> arrayList, Context appcontext) {
        mArrayList = arrayList;
        mFilteredList = arrayList;
        context=appcontext;
    }

    @Override
    public DataShopAllListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shopall_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataShopAllListAdapter.ViewHolder viewHolder, int i) {
        //what all item do here
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
                    ArrayList<String> filteredList = new ArrayList<>();
                    filteredList.add("하이네켄");
                    mFilteredList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<String>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView_All_ItemImage;
        private TextView textView_All_itemName;
        private TextView textView_All_itemPrice;
        public ViewHolder(View view) {
            super(view);
            textView_All_itemName = (TextView)view.findViewById(R.id.textView_All_itemName);
            textView_All_itemPrice = (TextView)view.findViewById(R.id.textView_All_itemPrice);
        }
    }
}