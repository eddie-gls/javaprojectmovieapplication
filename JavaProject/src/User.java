
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// User domain model + lightweight session holder for the logged-in account.
public class User {

    // Database primary key.
    private int id;
    // Login email.
    private String email;
    // Login password (stored as plain string in current implementation).
    private String password;
    // User role/status (example: customer, employee).
    private String statut;

    // In-memory reference to the currently authenticated user.
    private static User currentUser = null;

    public User(int id, String email, String password, String statut) {
        // Build a complete user object from database values.
        this.id = id;
        this.email = email;
        this.password = password;
        this.statut = statut;
    }

    public int getId() { return id; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getStatut() { return statut; }

    // Returns currently logged-in user (or null when no session exists).
    public static User getCurrentUser() { return currentUser; }

    // Stores authenticated user in memory after login.
    public static void setCurrentUser(User user) { currentUser = user; }

    // Convenience check for authentication state.
    public static boolean isLoggedIn() { return currentUser != null; }

    // Clears session user reference (logout).
    public static void logout() { currentUser = null; }

    // Creates a new customer account in database.
    public static boolean addUser(String email, String password) {
        try {
            Connection conn = DataSource.createConnection();

            // New users created from this flow are assigned customer role.
            String sql = "INSERT INTO user (email, password, statut) VALUES (?, ?, 'customer')";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, email);
            stmt.setString(2, password);

            // executeUpdate returns number of inserted rows.
            int rows = stmt.executeUpdate();
            conn.close();

            // Success when at least one row is created.
            return rows > 0;

        } catch (Exception ex) {
            // Any SQL issue returns false to caller.
            ex.printStackTrace();
            return false;
        }
    }
}