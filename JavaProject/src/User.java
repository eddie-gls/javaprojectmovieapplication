
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class User {

    private int id;
    private String email;
    private String password;
    private String statut;

    private static User currentUser = null;

    public User(int id, String email, String password, String statut) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.statut = statut;
    }

    public int getId() { return id; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getStatut() { return statut; }

    public static User getCurrentUser() { return currentUser; }
    public static void setCurrentUser(User user) { currentUser = user; }
    public static boolean isLoggedIn() { return currentUser != null; }
    public static void logout() { currentUser = null; }

    public static boolean addUser(String email, String password) {
        try {
            Connection conn = DataSource.createConnection();

            String sql = "INSERT INTO user (email, password, statut) VALUES (?, ?, 'customer')";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, email);
            stmt.setString(2, password);

            int rows = stmt.executeUpdate();
            conn.close();

            return rows > 0;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}