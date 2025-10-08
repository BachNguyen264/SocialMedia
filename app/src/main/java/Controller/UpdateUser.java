package Controller;

import java.sql.SQLException;

import Model.Database;
import Model.User;

public class UpdateUser {

    private Database database;
    private User user;

    public UpdateUser(User user, Database database) {
        this.database = database;
        this.user = user;
    }

    public boolean isEmailUsed() {
        return new CreateUser(user, database).isEmailUsed();
    }

    public boolean update() {
        boolean updated = false;
        String update = "UPDATE `users` SET `FirstName` = '"+user.getFirstName()+
                "', `LastName` = '"+user.getLastName()+"', `Email` = '"+user.getEmail()
                +"' WHERE `ID` = "+user.getID()+" ;";
        try {
            database.getStatement().execute(update);
            updated = true;
        } catch (SQLException e) {
            e.printStackTrace();
            updated = false;
        }
        return updated;
    }

}
