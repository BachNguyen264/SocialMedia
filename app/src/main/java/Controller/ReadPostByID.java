package Controller;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Database;
import Model.Post;

public class ReadPostByID {

    private Post post;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ReadPostByID(int ID, Database database) {
        String select = "SELECT * FROM `posts` WHERE `ID` = "+ID+" ;";
        try {
            ResultSet rs = database.getStatement().executeQuery(select);
            rs.next();
            post = new Post();
            post.setID(rs.getInt("ID"));
            post.setContent(rs.getString("Content"));
            post.setDateTimeFromString(rs.getString("DateTime"));
            post.setUser(new ReadUserByID(rs.getInt("User"), database).getUser());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Post getPost() {
        return post;
    }

}
