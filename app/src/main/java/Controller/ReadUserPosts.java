package Controller;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Database;
import Model.Post;
import Model.User;

public class ReadUserPosts {

    private ArrayList<Post> posts;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ReadUserPosts(User u, Database database) {
        posts = new ArrayList<>();
        String select = "SELECT * FROM `posts` WHERE `User` = "+u.getID()+" ;";
        try {
            ResultSet rs = database.getStatement().executeQuery(select);
            while (rs.next()) {
                Post p = new Post();
                p.setID(rs.getInt("ID"));
                p.setContent(rs.getString("Content"));
                p.setDateTimeFromString(rs.getString("DateTime"));
                p.setUser(u);
                posts.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

}
