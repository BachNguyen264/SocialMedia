package Controller;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Comment;
import Model.Database;
import Model.Post;
import Model.User;

public class ReadUserComments {

    private ArrayList<Post> posts;
    private ArrayList<Comment> comments;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ReadUserComments(User u, Database database) {
        posts = new ArrayList<>();
        comments = new ArrayList<>();
        String select = "SELECT * FROM `comments` WHERE `User` = "+u.getID()+" ;";
        try {
            ResultSet rs = database.getStatement().executeQuery(select);
            ArrayList<Integer> postsIDs = new ArrayList<>();
            while (rs.next()) {
                Comment c = new Comment();
                c.setID(rs.getInt("ID"));
                c.setContent(rs.getString("Content"));
                c.setUser(u);
                c.setDateTimeFromString(rs.getString("DateTime"));
                comments.add(c);
                postsIDs.add(rs.getInt("Post"));
            }
            for (int i=0;i<postsIDs.size();i++) {
                posts.add(new ReadPostByID(postsIDs.get(i), database).getPost());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

}
