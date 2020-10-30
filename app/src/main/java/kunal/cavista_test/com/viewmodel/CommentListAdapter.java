package kunal.cavista_test.com.viewmodel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kunal.cavista_test.com.R;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.CommentViewHolder> {

    Context mContext;

    ArrayList<String> mList;

    public CommentListAdapter(Context context, ArrayList<String> list) {

        mContext = context;

        mList = list;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CommentViewHolder view = new CommentViewHolder(LayoutInflater.from(mContext).inflate(R.layout.comment_item, null));
        view.comment = view.itemView.findViewById(R.id.cmnt);
        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.comment.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView comment;

        public CommentViewHolder(View itemView) {
            super(itemView);
        }

    }


}
