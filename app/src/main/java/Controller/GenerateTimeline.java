package Controller;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Database;
import Model.Post;
import Model.User;

public class GenerateTimeline {

    private final ArrayList<Post> posts;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public GenerateTimeline(User user, Database database) {
        posts = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        if (user.getFriendsIDs().size()!=0) {
            for (int i=0;i<user.getFriendsIDs().size();i++) {
                sb.append("`User` = ").append(user.getFriendsIDs().get(i));
                if (i!=user.getFriendsIDs().size()-1) {
                    sb.append(" OR ");
                } else {
                    sb.append(";");
                }
            }

            String select = "SELECT * FROM `posts` WHERE "+ sb;
            try {
                ResultSet rs = database.getStatement().executeQuery(select);
                ArrayList<Integer> usersIDs = new ArrayList<>();
                while (rs.next()) {
                    Post p = new Post();
                    p.setID(rs.getInt("ID"));
                    p.setContent(rs.getString("Content"));
                    usersIDs.add(rs.getInt("User"));
                    p.setDateTimeFromString(rs.getString("DateTime"));
                    posts.add(p);
                }
                for (int i=0;i<usersIDs.size();i++) {
                    posts.get(i).setUser(new ReadUserByID(usersIDs.get(i), database).getUser());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

}
