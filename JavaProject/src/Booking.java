import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
public class Booking {

    private int id;
    private int filmId;
    private int userId;
    private int tickets;
    private String day;
    private String schedule;
    private boolean student;
    private double totalPrice;
    private int paymentStatus;

    public Booking(int id, int filmId, int userId, int tickets,
                   String day, String schedule, boolean student,
                   double totalPrice, int paymentStatus) {

        this.id = id;
        this.filmId = filmId;
        this.userId = userId;
        this.tickets = tickets;
        this.day = day;
        this.schedule = schedule;
        this.student = student;
        this.totalPrice = totalPrice;
        this.paymentStatus = paymentStatus;
    }


    public int getId() { return id; }
    public int getFilmId() { return filmId; }
    public int getUserId() { return userId; }
    public int getTickets() { return tickets; }
    public String getDay() { return day; }
    public String getSchedule() { return schedule; }
    public boolean isStudent() { return student; }
    public double getTotalPrice() { return totalPrice; }
    public int getPaymentStatus() { return paymentStatus; }
    

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
            stmt.setDate(4, java.sql.Date.valueOf(bookingDate));    // ✅ date séparée
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
    public static Booking getLastUnpaidBooking(int userId) {

        try {
            Connection conn = DataSource.createConnection();

            String sql = "SELECT * FROM booking WHERE user_id=? AND payment_status=0 ORDER BY id DESC LIMIT 1";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Booking b = new Booking(
                        rs.getInt("id"),
                        rs.getInt("film_id"),
                        rs.getInt("user_id"),
                        rs.getInt("tickets"),
                        rs.getString("booking_date"),
                        rs.getString("timeslot"),
                        rs.getBoolean("student"),
                        rs.getDouble("total_price"),
                        rs.getInt("payment_status")
                );
                conn.close();
                return b;
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public static void setBookingPaid(int bookingId) {

        try {
            Connection conn = DataSource.createConnection();

            String sql = "UPDATE booking SET payment_status=1 WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bookingId);
            stmt.executeUpdate();

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void deleteAllUnpaidBookings(int userId) {
        try {
            Connection conn = DataSource.createConnection();

            String sql = "DELETE FROM booking WHERE user_id=? AND payment_status=0";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);

            stmt.executeUpdate();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static List<Booking> getBookingsForUser(int userId) {
        List<Booking> list = new ArrayList<>();

        try {
            Connection conn = DataSource.createConnection();
            String sql = "SELECT * FROM booking WHERE user_id=?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Booking b = new Booking(
                    rs.getInt("id"),
                    rs.getInt("film_id"),
                    rs.getInt("user_id"),
                    rs.getInt("tickets"),
                    rs.getString("booking_date"),
                    rs.getString("timeslot"),
                    rs.getBoolean("student"),
                    rs.getDouble("total_price"),
                    rs.getInt("payment_status")
                );
                list.add(b);
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }



}