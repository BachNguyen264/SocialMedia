package Controller;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.sql.SQLException;

import Model.Database;
import Model.Post;

public class CreatePost {

    private Post post;
    private Database database;

    public CreatePost(Post post, Database database) {
        this.post = post;
        this.database = database;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean isPosted() {
        boolean isPosted = false;
        String insert = "INSERT INTO `posts` (`Content` ,`User` ,`DateTime`) VALUES " +
                "('"+post.getContent()+"', '"+post.getUser().getID()+"', '"+
                post.getDateTimeToString()+"');";
        try {
            database.getStatement().execute(insert);
            isPosted = true;
        } catch (SQLException e) {
            e.printStackTrace();
            isPosted = false;
        }
        return isPosted;
    }

}
