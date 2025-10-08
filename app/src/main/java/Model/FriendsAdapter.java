package Model;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.CT060104.socialmedia.R;

import java.util.ArrayList;

import Controller.AddFriend;
import Controller.RemoveFriend;

public class FriendsAdapter extends BaseAdapter {

    private final ArrayList<User> users;
    private final Activity activity;
    private final User currentUser;
    private final Database database;

    public FriendsAdapter(ArrayList<User> users, Activity activity, User currentUser, Database database) {
        this.users = users;
        this.activity = activity;
        this.currentUser = currentUser;
        this.database = database;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public User getItem(int i) {
        return users.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        @SuppressLint({"InflateParams", "ViewHolder"})
        View v = activity.getLayoutInflater().inflate(R.layout.friend, null);

        TextView user_name = v.findViewById(R.id.user_name);
        Button follow = v.findViewById(R.id.follow);
        TextView unfollow = v.findViewById(R.id.unfollow);

        User user = users.get(i);

        user_name.setText(user.getName());

        if (currentUser.isFriend(user)) {
            follow.setVisibility(View.GONE);
            unfollow.setVisibility(View.VISIBLE);
        } else {
            follow.setVisibility(View.VISIBLE);
            unfollow.setVisibility(View.GONE);
        }

        follow.setOnClickListener(v1-> {
            if (new AddFriend(currentUser, database, user).isAdded()) {
                currentUser.addFriend(user);
                follow.setVisibility(View.GONE);
                unfollow.setVisibility(View.VISIBLE);
            }
        });

        unfollow.setOnClickListener(v1-> {
            if (new RemoveFriend(currentUser, database, user).isRemoved()) {
                currentUser.removeFriend(user);
                follow.setVisibility(View.VISIBLE);
                unfollow.setVisibility(View.GONE);
            }
        });

        return v;
    }
}
