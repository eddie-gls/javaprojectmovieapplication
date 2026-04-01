import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

// Booking model + data access helpers for the booking table.
public class Booking {

    // Primary key from booking table.
    private int id;
    // Linked movie identifier.
    private int filmId;
    // Linked user identifier.
    private int userId;
    // Number of reserved seats.
    private int tickets;
    // Booking date (stored as text in this model).
    private String day;
    // Selected timeslot for the movie.
    private String schedule;
    // Whether the student discount was applied.
    private boolean student;
    // Final amount for this booking.
    private double totalPrice;
    // 0 = unpaid, 1 = paid.
    private int paymentStatus;
    private String movieName;




    public Booking(int id, int filmId, int userId, int tickets,
                   String day, String schedule, boolean student,
                   double totalPrice, int paymentStatus, String movieName) {

            // Initialize all fields from database/form values.
        this.id = id;
        this.filmId = filmId;
        this.userId = userId;
        this.tickets = tickets;
        this.day = day;
        this.schedule = schedule;
        this.student = student;
        this.totalPrice = totalPrice;
        this.paymentStatus = paymentStatus;
        this.movieName = movieName;
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
    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
    

    public static boolean addBooking(
            int userId,
            Movie movie,
            int tickets,
            String bookingDate,
            String timeslot,
            boolean student
    ) {

        // Choose standard or discounted ticket price.
        double pricePerTicket = student ? movie.getDiscount() : movie.getPrice();
        // Compute total amount for all requested tickets.
        double totalPrice = pricePerTicket * tickets;

        try {
            Connection conn = DataSource.createConnection();

            // Insert a new booking row with payment_status defaulting to unpaid in DB.
            String sql = "INSERT INTO booking (film_id, user_id, tickets, booking_date, timeslot, student, total_price) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, movie.getId());
            stmt.setInt(2, userId);
            stmt.setInt(3, tickets);
            // Store date and timeslot in dedicated columns.
            stmt.setDate(4, java.sql.Date.valueOf(bookingDate));
            stmt.setString(5, timeslot);
            stmt.setBoolean(6, student);
            stmt.setDouble(7, totalPrice);

            // executeUpdate returns the number of inserted rows.
            int rows = stmt.executeUpdate();
            conn.close();

            // Success if at least one row has been created.
            return rows > 0;

        } catch (Exception e) {
            // Any SQL/parsing issue returns false to the caller.
            e.printStackTrace();
            return false;
        }
    }
    public static Booking getLastUnpaidBooking(int userId) {

        try {
            Connection conn = DataSource.createConnection();

            // Fetch the most recent unpaid booking for the given user.
            String sql = 
                "SELECT b.id, b.film_id, b.user_id, b.tickets, b.booking_date, " +
                "b.timeslot, b.student, b.total_price, b.payment_status, " +
                "m.moviename " +
                "FROM booking b " +
                "JOIN movie m ON b.film_id = m.id " +
                "WHERE b.user_id=? AND b.payment_status=0 " +
                "ORDER BY b.id DESC LIMIT 1";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Map the database row to a Booking object.
                Booking b = new Booking(
                        rs.getInt("id"),
                        rs.getInt("film_id"),
                        rs.getInt("user_id"),
                        rs.getInt("tickets"),
                        rs.getString("booking_date"),
                        rs.getString("timeslot"),
                        rs.getBoolean("student"),
                        rs.getDouble("total_price"),
                        rs.getInt("payment_status"), 
                        rs.getString("moviename")
                );
                conn.close();
                return b;
            }

            conn.close();

        } catch (Exception e) {
            // Return null when no booking can be fetched due to an error.
            e.printStackTrace();
        }

        // No unpaid booking found for this user.
        return null;
    }
    public static void setBookingPaid(int bookingId) {

        try {
            Connection conn = DataSource.createConnection();

            // Mark the targeted booking as paid.
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

            // Cleanup helper used when a user cancels pending payment flow.
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
        // Will contain both paid and unpaid bookings for this user.
        List<Booking> list = new ArrayList<>();

        try {
            Connection conn = DataSource.createConnection();
            // Retrieve all bookings linked to the user account.
            String sql = "SELECT b.id, b.film_id, b.user_id, b.tickets, b.booking_date, " +
        "b.timeslot, b.student, b.total_price, b.payment_status, " +
        "m.moviename " +
        "FROM booking b " +
        "JOIN movie m ON b.film_id = m.id " +
        "WHERE b.user_id = ? AND b.payment_status = 1 " +
        "ORDER BY b.booking_date DESC";

            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                // Convert each result row into a Booking object and append it.
                Booking b = new Booking(
                    rs.getInt("id"),
                    rs.getInt("film_id"),
                    rs.getInt("user_id"),
                    rs.getInt("tickets"),
                    rs.getString("booking_date"),
                    rs.getString("timeslot"),
                    rs.getBoolean("student"),
                    rs.getDouble("total_price"),
                    rs.getInt("payment_status"),
                    rs.getString("moviename")
                );
                list.add(b);
            }

            conn.close();
        } catch (Exception e) {
            // Return what has been loaded so far if an error occurs.
            e.printStackTrace();
        }

        return list;
    }



}