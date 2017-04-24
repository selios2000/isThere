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
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static samsung.sec.com.isthere.R.drawable.listshape;

public class DataListAdapter extends RecyclerView.Adapter<DataListAdapter.ViewHolder> implements Filterable {
    private ArrayList<String> mArrayList;
    private ArrayList<String> mFilteredList;
    private Context context;
    public DataListAdapter(ArrayList<String> arrayList,Context appcontext) {
        mArrayList = arrayList;
        mFilteredList = arrayList;
        context=appcontext;
    }

    @Override
    public DataListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.searchlist_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataListAdapter.ViewHolder viewHolder, int i) {
        viewHolder.itemidlist.setText(mFilteredList.get(i));
        if(i==0){
            Drawable drawable = context.getResources().getDrawable(R.drawable.listshape);
            viewHolder.itemidlayoutRoot.setBackground(drawable);
        }else if(i==1){
            Drawable drawable = context.getResources().getDrawable(R.drawable.listshape_second);
            viewHolder.itemidlayoutRoot.setBackground(drawable);
        }else{
            Drawable drawable = context.getResources().getDrawable(R.drawable.listshape_third);
            viewHolder.itemidlayoutRoot.setBackground(drawable);
        }
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
        private TextView itemidlist;
        private LinearLayout itemidlayoutlist;
        private LinearLayout itemidlayoutRoot;
        public ViewHolder(View view) {
            super(view);
            itemidlist = (TextView)view.findViewById(R.id.itemListid);
            itemidlayoutlist =(LinearLayout)view.findViewById(R.id.itemListlayoutid);
            itemidlayoutRoot=(LinearLayout)view.findViewById(R.id.itemListlayoutidRoot);
            itemidlayoutlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text=itemidlist.getText().toString();
                    Intent intent = new Intent(context, MapsActivity.class);
                    intent.putExtra("itemname",text);
                    context.startActivity(intent);
                }
            });
        }
    }
}