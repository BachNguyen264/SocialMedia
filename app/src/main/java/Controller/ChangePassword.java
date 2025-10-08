package Controller;

import java.sql.SQLException;

import Model.Database;

public class ChangePassword {

    private String password;
    private int ID;
    private Database database;

    public ChangePassword(String password, int ID, Database database) {
        this.password = password;
        this.ID = ID;
        this.database = database;
    }

    public boolean isChanged() {
        boolean isChanged = false;
        String update = "UPDATE `users` SET `Password` = '"+password+"' WHERE `ID` = "+ID+" ;";
        try {
            database.getStatement().execute(update);
            isChanged = true;
        } catch (SQLException e) {
            e.printStackTrace();
            isChanged = false;
        }
        return isChanged;
    }

}
