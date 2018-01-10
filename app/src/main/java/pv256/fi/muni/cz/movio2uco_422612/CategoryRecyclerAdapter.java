package pv256.fi.muni.cz.movio2uco_422612;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import pv256.fi.muni.cz.movio2uco_422612.entities.Movie;

/**
 * Created by pato on 2.11.2017.
 */

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_CATEGORY = 0;
    private static final int TYPE_MOVIE = 1;
    private static final int TYPE_MO_DATA = 2;

    private Context mContext;
    private List<Object> mData;

    public CategoryRecyclerAdapter(Context context, List<Object> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        switch (viewType) {
            case TYPE_CATEGORY:
                view = inflater.inflate(R.layout.list_item_category, parent, false);
                return new ViewHolder_category(view);
            case TYPE_MOVIE:
                view = inflater.inflate(R.layout.list_item_movie, parent, false);
                return new ViewHolder_movie(view, mContext);
            case TYPE_MO_DATA:
                view = inflater.inflate(R.layout.no_data_fragment, parent, false);
                return new ViewHolder_empty(view);
        }
        return null;
    }

    public void setItems(List<Object> items) {
        this.mData = items;
        notifyDataSetChanged();
        notifyItemRangeChanged(0, mData.size());
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position) instanceof Movie ? TYPE_MOVIE : TYPE_CATEGORY;
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int rowType = getItemViewType(position);
        switch (rowType) {
            case TYPE_MOVIE:
                ViewHolder_movie movieHolder = (ViewHolder_movie) holder;
                Movie movie = (Movie) mData.get(position);
                movieHolder.title.setText(movie.getTitle());
                movieHolder.rating.setText(Float.toString(movie.getPopularity()));
                Picasso.with(mContext).load("https://image.tmdb.org/t/p/w500/" + movie.getBackdrop()).into(movieHolder.image);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((MainActivity) CategoryRecyclerAdapter.this.getContext()).onMovieSelect(position);
                    }
                });
                break;
            case TYPE_CATEGORY:
                ViewHolder_category categoryHolder = (ViewHolder_category) holder;
                categoryHolder.text.setText((String) mData.get(position));
                break;
            case TYPE_MO_DATA:
                ViewHolder_empty emptyView = (ViewHolder_empty) holder;
                emptyView.text.setText(mData.get(position).toString() /*noDataLabel*/);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder_category extends RecyclerView.ViewHolder {
        public TextView text;

        public ViewHolder_category(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.category_name);
        }
    }

    public static class ViewHolder_movie extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView image;
        public TextView rating;
        private RelativeLayout mLayout;

        public ViewHolder_movie(View itemView, final Context context) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.movie_title);
            image = (ImageView) itemView.findViewById(R.id.movie_image);
            rating = (TextView) itemView.findViewById(R.id.movie_rating);
            mLayout = (RelativeLayout) itemView.findViewById(R.id.layout);
            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (context != null) {
                        ((MainActivity) context).onMovieSelect(getAdapterPosition());
                    }
                }
            };
            View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (context != null) {
                        ((MainActivity) context).onMovieLongClick(getAdapterPosition());
                        return true;
                    }
                    return false;
                }
            };
            mLayout.setOnClickListener(clickListener);
            mLayout.setOnLongClickListener(longClickListener);
        }
    }

    public static class ViewHolder_empty extends RecyclerView.ViewHolder {
        public TextView text;

        public ViewHolder_empty(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.no_data_text);
        }
    }

}
