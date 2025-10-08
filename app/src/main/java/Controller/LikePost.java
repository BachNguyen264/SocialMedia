package Controller;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.sql.SQLException;

import Model.Database;
import Model.Post;
import Model.User;

public class LikePost {

    private User u;
    private Post p;
    private Database database;

    public LikePost(User u, Post p, Database database) {
        this.u = u;
        this.p = p;
        this.database = database;
    }
    
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean isLiked() {
        boolean liked = false;
        String insert = "INSERT INTO `likes` (`User`, `Post`) VALUES ('"+u.getID()+"', '"+
                p.getID()+"');";
        try {
            database.getStatement().execute(insert);
            liked = true;
        } catch (SQLException e) {
            e.printStackTrace();
            liked = false;
        }
        return liked;
    }

}
