import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class PaymentBank {


    public static boolean cardExists(String holder, String number, String exp, String cvv) {

        try {
            Connection conn = DataSource.createConnection();

            String sql = "SELECT * FROM payment WHERE card_holder=? AND card_number=? AND expiration_date=? AND cvv=?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, holder);
            stmt.setString(2, number);
            stmt.setString(3, exp);
            stmt.setString(4, cvv);

            ResultSet rs = stmt.executeQuery();

            boolean exists = rs.next();
            conn.close();

            return exists;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
