package Model;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.CT060104.socialmedia.CommentsActivity;
import com.CT060104.socialmedia.R;

import java.util.ArrayList;

import Controller.DislikePost;
import Controller.LikePost;
import Controller.ReadPostComments;
import Controller.ReadPostLikes;

public class PostsAdapter extends BaseAdapter {

    private final ArrayList<Post> posts;
    private final Activity activity;
    private final User user;
    private final Database database;

    public PostsAdapter(ArrayList<Post> posts, Activity activity, User user, Database database) {
        this.posts = posts;
        this.activity = activity;
        this.user = user;
        this.database = database;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Post getItem(int i) {
        return posts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        @SuppressLint({"InflateParams", "ViewHolder"})
        View v = activity.getLayoutInflater().inflate(R.layout.post, null);

        TextView post_author = v.findViewById(R.id.post_author);
        TextView post_date = v.findViewById(R.id.post_date);
        TextView post_content = v.findViewById(R.id.post_content);
        ImageView post_like = v.findViewById(R.id.post_like);
        TextView post_likes = v.findViewById(R.id.post_likes);
        TextView post_comments = v.findViewById(R.id.post_comments);

        Post post = posts.get(i);

        post_author.setText(post.getUser().getName());
        post_date.setText(post.getDateToString());
        post_content.setText(post.getContent());

        if (user.liked(post)) {
            post_like.setImageResource(R.drawable.liked);
        } else {
            post_like.setImageResource(R.drawable.like);
        }

        //to set likes count
        post_likes.setText(getLikesStatus(new ReadPostLikes(post, database).getLikesCount()));

        post_like.setOnClickListener(v1-> {
            if (!user.liked(post)) {
                //Not Liked ==> on Click ==> Like post
                if (new LikePost(user, post, database).isLiked()) {
                    user.like(post);
                    post_like.setImageResource(R.drawable.liked);
                }
            } else {
                //Liked ==> onClick ==> Dislike Post
                if (new DislikePost(user, post, database).isDisliked()) {
                    user.dislike(post);
                    post_like.setImageResource(R.drawable.like);
                }
            }
            //To refresh onLike and onDislike not only on disLike
            //Refresh likes count
            post_likes.setText(getLikesStatus(new ReadPostLikes(post, database).getLikesCount()));
        });

        int commentsCount = new ReadPostComments(post, database).getCommentsCounter();
        if (commentsCount<2) {
            post_comments.setText(commentsCount + " Comment");
        } else {
            post_comments.setText(commentsCount + " Comments");
        }

        post_comments.setOnClickListener(v1-> {
            Intent intent = new Intent(activity, CommentsActivity.class);
            intent.putExtra("PostID", post.getID());
            activity.startActivity(intent);
        });

        return v;
    }

    private String getLikesStatus(int likesCount) {
        if (likesCount<2) {
            return likesCount + " Like";
        } else {
            return likesCount + " Likes";
        }
    }

}
