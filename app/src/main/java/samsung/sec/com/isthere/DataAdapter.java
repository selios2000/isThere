package samsung.sec.com.isthere;

/**
 * Created by 빠빵 on 2017-04-23.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> implements Filterable {
    private ArrayList<String> mArrayList;
    private ArrayList<String> mFilteredList;
    private Context context;
    public DataAdapter(ArrayList<String> arrayList,Context appcontext) {
        mArrayList = arrayList;
        mFilteredList = arrayList;
        context=appcontext;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, int i) {
        viewHolder.itemid.setText(mFilteredList.get(i));
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
        private TextView itemid;
        private LinearLayout itemidlayout;
        public ViewHolder(View view) {
            super(view);
            itemid = (TextView)view.findViewById(R.id.itemid);
            itemidlayout =(LinearLayout)view.findViewById(R.id.itemlayoutid);
            itemidlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text=itemid.getText().toString();
                    Intent intent = new Intent(context, MapsActivity.class);
                    intent.putExtra("itemname",text);
                    context.startActivity(intent);
                }
            });
            itemid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text=itemid.getText().toString();
                    Intent intent = new Intent(context, MapsActivity.class);
                    intent.putExtra("itemname",text);
               //     context.startActivity(intent);
                }
            });
        }
    }
}