import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class Booking {

    public static boolean addBooking(
            int userId,
            Movie movie,
            int tickets,
            String bookingDate,
            String timeslot,
            boolean student
    ) {

        double pricePerTicket = student ? movie.getDiscount() : movie.getPrice();
        double totalPrice = pricePerTicket * tickets;

        try {
            Connection conn = DataSource.createConnection();

            String sql = "INSERT INTO booking (film_id, user_id, tickets, booking_date, timeslot, student, total_price) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, movie.getId());
            stmt.setInt(2, userId);
            stmt.setInt(3, tickets);
            stmt.setString(4, bookingDate);    // ✅ date séparée
            stmt.setString(5, timeslot);       // ✅ créneau séparé
            stmt.setBoolean(6, student);
            stmt.setDouble(7, totalPrice);

            int rows = stmt.executeUpdate();
            conn.close();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}