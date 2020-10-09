package kunal.cavista_test.com.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import kunal.cavista_test.com.R;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    List<Images> Photos;
    Context mContext;

    public PhotoAdapter(Context context, List<Images> photos) {
        mContext = context;
        this.Photos = photos;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        PhotoViewHolder vh = new PhotoViewHolder(LayoutInflater.from(mContext).inflate(R.layout.rv_item, null));
        vh.photo = vh.itemView.findViewById(R.id.photo);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        String link = Photos.get(position).getId();

        Picasso.get()
                .load(link)
                .into(holder.photo);

    }

    @Override
    public int getItemCount() {
        return Photos.size();
    }

    public void addImages(List<Images> images) {

        for (Images im : images) {
            if (im.getId().contains("https://i.imgur.com/"))
                Photos.add(im);
        }


        notifyDataSetChanged();


    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;

        public PhotoViewHolder(View itemView) {
            super(itemView);
        }
    }


}
