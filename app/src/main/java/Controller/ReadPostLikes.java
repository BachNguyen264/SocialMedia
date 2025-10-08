package Controller;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Database;
import Model.Post;

public class ReadPostLikes {

    private int likes;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ReadPostLikes(Post p, Database database) {
        likes = 0;
        String select = "SELECT * FROM `likes` WHERE `Post` = "+p.getID()+" ;";
        try {
            ResultSet rs = database.getStatement().executeQuery(select);
            while (rs.next()) {
                likes++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getLikesCount() {
        return likes;
    }

}
