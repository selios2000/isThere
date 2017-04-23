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

public class DataCurrentItem_Adapter extends RecyclerView.Adapter<DataCurrentItem_Adapter.ViewHolder> implements Filterable {
    private ArrayList<DataCurrentItem> mArrayList;
    private ArrayList<DataCurrentItem> mFilteredList;
    private Context context;

    public DataCurrentItem_Adapter(ArrayList<DataCurrentItem> arrayList, Context appcontext) {
        mArrayList = arrayList;
        mFilteredList = arrayList;
        context = appcontext;
    }

    @Override
    public DataCurrentItem_Adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.searchlist_current_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataCurrentItem_Adapter.ViewHolder viewHolder, int i) {
        viewHolder.itemid_current.setText(mFilteredList.get(i).getItemid_current());
        Drawable drawable = context.getResources().getDrawable(R.drawable.conv_gs25);
        viewHolder.item_img_current.setImageDrawable(drawable);

        viewHolder.itemcount_current.setText(mFilteredList.get(i).getItemcount_current());
        viewHolder.martname_current.setText(mFilteredList.get(i).getMartname_current());
        viewHolder.martposition_current.setText(mFilteredList.get(i).getMartposition_current());
        viewHolder.martdistance_current.setText(mFilteredList.get(i).getMartdistance_current());
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
                    ArrayList<DataCurrentItem> filteredList = new ArrayList<>();
                    mFilteredList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<DataCurrentItem>) filterResults.values;
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
                    String  itemname= itemid_current.getText().toString();
                    intent.putExtra("itemname",itemname);
                    context.startActivity(intent);
                }
            });
        }
    }
}