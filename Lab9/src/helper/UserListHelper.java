package helper;

import entity.UserList;

import javax.servlet.ServletContext;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class UserListHelper {

    public static final String USERS_FILENAME = "WEB-INF/users.dat";
    private static String USERS_PATH = null;

    public static UserList readUserList(ServletContext context) {
        try {
            USERS_PATH = context.getRealPath(USERS_FILENAME);
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(USERS_PATH));
            return (UserList) in.readObject();
        } catch (Exception e) {
            return new UserList();
        }
    }

    public static void saveUserList(UserList users) {
        synchronized (users) {
            try {
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(USERS_PATH));
                out.writeObject(users);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}