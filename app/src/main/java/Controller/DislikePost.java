package Controller;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.sql.SQLException;

import Model.Database;
import Model.Post;
import Model.User;

public class DislikePost {

    private User u;
    private Post p;
    private Database database;

    public DislikePost(User u, Post p, Database database) {
        this.u = u;
        this.p = p;
        this.database = database;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean isDisliked() {
        boolean disliked = false;
        String delete = "DELETE FROM `likes` WHERE `User` = "+u.getID()+" AND `Post` = "
                +p.getID()+" ;";
        try {
            database.getStatement().execute(delete);
            disliked = true;
        } catch (SQLException e) {
            e.printStackTrace();
            disliked = false;
        }
        return disliked;
    }

}
