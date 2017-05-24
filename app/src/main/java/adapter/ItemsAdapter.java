package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.noctisdrakon.librarytest.R;

import java.util.List;

import model.Item;

/**
 * Created by NoctisDrakon on 24/05/2017.
 */

public class ItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "ItemsAdapter";
    private List<Item> list;
    private Context context;
    private final View.OnClickListener listener;
    private final String headerTitle;

    public ItemsAdapter(List<Item> list, Context context, View.OnClickListener listener, String headerTitle) {
        this.list = list;
        this.context = context;
        this.listener = listener;
        this.headerTitle = headerTitle;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        switch (viewType) {
            case Item.TYPE_ACCOMODTION:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.accomodation_element, parent, false);
                return new AccomodationViewHolder(v);
            case Item.TYPE_HEADER:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.accomodation_header_element, parent, false);
                return new HeaderViewHolder(v);
        }
        throw new IllegalArgumentException("None of the declared types match with the current found type, please implement it correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Item a = getItem(position);

        switch (a.type) {
            case Item.TYPE_HEADER:
                HeaderViewHolder hvh = (HeaderViewHolder) holder;
                hvh.title.setText(headerTitle);
                break;

            case Item.TYPE_ACCOMODTION:
                AccomodationViewHolder avh = (AccomodationViewHolder) holder;
                avh.accomodationTitle.setText(a.title);
                avh.accomodationPrice.setText(String.format(context.getString(R.string.euro_format), a.price));
                avh.accomodationRating.setRating(a.rate);
                if (a.imageUrl != null && !TextUtils.isEmpty(a.imageUrl)) {
                    Glide.with(context)
                            .load(a.imageUrl)
                            .crossFade()
                            .into(avh.accomodationImage);
                }
                avh.parent.setTag(a);
                avh.parent.setOnClickListener(listener);
                break;


        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class AccomodationViewHolder extends RecyclerView.ViewHolder {
        LinearLayout parent;
        ImageView accomodationImage;
        TextView accomodationPrice;
        TextView accomodationTitle;
        RatingBar accomodationRating;

        AccomodationViewHolder(View itemView) {
            super(itemView);
            parent = (LinearLayout) itemView.findViewById(R.id.parent);
            accomodationImage = (ImageView) itemView.findViewById(R.id.accomodation_image);
            accomodationPrice = (TextView) itemView.findViewById(R.id.accomodation_price);
            accomodationTitle = (TextView) itemView.findViewById(R.id.accomodation_title);
            accomodationRating = (RatingBar) itemView.findViewById(R.id.accomodation_rating);
        }
    }

    private Item getItem(int index) {
        return list.get(index);
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).type;
    }


    public static class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }

}