package Model;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.CT060104.socialmedia.R;

import java.util.ArrayList;

public class CommentsAdapter extends BaseAdapter {

    private final ArrayList<Comment> comments;
    private final Activity activity;

    public CommentsAdapter(ArrayList<Comment> comments, Activity activity) {
        this.comments = comments;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Comment getItem(int i) {
        return comments.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        @SuppressLint({"InflateParams", "ViewHolder"})
        View v = activity.getLayoutInflater().inflate(R.layout.comment, null);

        TextView comment_author = v.findViewById(R.id.comment_author);
        TextView comment_content = v.findViewById(R.id.comment_content);
        TextView comment_date = v.findViewById(R.id.comment_date);

        Comment comment = comments.get(i);

        comment_author.setText(comment.getUser().getName());
        comment_content.setText(comment.getContent());
        comment_date.setText(comment.getDateToString());

        return v;
    }
}
