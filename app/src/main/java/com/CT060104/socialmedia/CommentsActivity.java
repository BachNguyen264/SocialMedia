package com.CT060104.socialmedia;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Controller.CreateComment;
import Controller.DislikePost;
import Controller.LikePost;
import Controller.ReadPostByID;
import Controller.ReadPostComments;
import Controller.ReadPostLikes;
import Controller.ReadUser;
import Model.Comment;
import Model.CommentsAdapter;
import Model.Database;
import Model.Post;
import Model.User;

public class CommentsActivity extends AppCompatActivity {

    private Database database;
    private Post p;
    private TextView post_comments;
    private ListView comments_list;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        TextView post_author = findViewById(R.id.post_author);
        TextView post_date = findViewById(R.id.post_date);
        TextView post_content = findViewById(R.id.post_content);
        ImageView post_like = findViewById(R.id.post_like);
        TextView post_likes = findViewById(R.id.post_likes);
        post_comments = findViewById(R.id.post_comments);

        EditText comment_edittext = findViewById(R.id.comment_edittext);
        Button post = findViewById(R.id.post);

        comments_list = findViewById(R.id.comments_list);

        database = new Database();

        int postID = getIntent().getIntExtra("PostID", -1);
        p = new ReadPostByID(postID, database).getPost();

        SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        String savedEmail = sharedPreferences.getString("Email", "");
        String savedPassword = sharedPreferences.getString("Password", "");
        User user = new ReadUser(savedEmail, savedPassword, database).getUser();

        post_author.setText(p.getUser().getName());
        post_date.setText(p.getDateToString());
        post_content.setText(p.getContent());

        if (user.liked(p)) {
            post_like.setImageResource(R.drawable.liked);
        } else {
            post_like.setImageResource(R.drawable.like);
        }

        //to set likes count
        post_likes.setText(getLikesStatus(new ReadPostLikes(p, database).getLikesCount()));

        post_like.setOnClickListener(v1-> {
            if (!user.liked(p)) {
                //Like post
                if (new LikePost(user, p, database).isLiked()) {
                    post_like.setImageResource(R.drawable.liked);
                    user.liked(p);
                }
            } else {
                //Dislike Post
                if (new DislikePost(user, p, database).isDisliked()) {
                    post_like.setImageResource(R.drawable.like);
                    user.dislike(p);
                }
            }
            //Refresh likes count
            post_likes.setText(getLikesStatus(new ReadPostLikes(p, database).getLikesCount()));
        });

        refreshComments();

        post.setOnClickListener(v-> {
            if (comment_edittext.getText().toString().equals("")) {
                Toast.makeText(this, "Cannot publish empty comment", Toast.LENGTH_SHORT).show();
                return;
            }
            Comment c = new Comment(comment_edittext.getText().toString(), user);
            if (new CreateComment(c, p, user, database).commented()) {
                comment_edittext.setText("");
                refreshComments();
            }
        });

    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void refreshComments() {
        ReadPostComments readPostComments = new ReadPostComments(p, database);
        int commentsCount = readPostComments.getCommentsCounter();
        if (commentsCount<2) {
            post_comments.setText(commentsCount + " Comment");
        } else {
            post_comments.setText(commentsCount + " Comments");
        }

        ArrayList<Comment> comments = readPostComments.getComments();
        comments_list.setAdapter(new CommentsAdapter(comments, this));
    }

    private String getLikesStatus(int likesCount) {
        if (likesCount<2) {
            return likesCount + " Like";
        } else {
            return likesCount + " Likes";
        }
    }

}