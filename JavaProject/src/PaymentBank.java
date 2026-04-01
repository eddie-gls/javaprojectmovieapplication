import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// Helper class for validating payment card data against the payment table.
public class PaymentBank {


    // Returns true if a card with the exact provided information exists.
    public static boolean cardExists(String holder, String number, String exp, String cvv) {

        try {
            Connection conn = DataSource.createConnection();

            // Match all card fields to validate card identity.
            String sql = "SELECT * FROM payment WHERE card_holder=? AND card_number=? AND expiration_date=? AND cvv=?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, holder);
            stmt.setString(2, number);
            stmt.setString(3, exp);
            stmt.setString(4, cvv);

            ResultSet rs = stmt.executeQuery();

            // If one row exists, the card is considered valid in local DB.
            boolean exists = rs.next();
            conn.close();

            return exists;

        } catch (Exception e) {
            // Any SQL error is treated as validation failure.
            e.printStackTrace();
            return false;
        }
    }
}
