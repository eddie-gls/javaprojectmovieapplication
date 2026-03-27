import java.sql.*;
import java.util.ArrayList;

public class ShowTime {

    // ✅ Retourne toutes les dates disponibles pour un film
    public static ArrayList<String> getDatesForMovie(int movieId) {
        ArrayList<String> list = new ArrayList<>();

        try {
            Connection conn = DataSource.createConnection();

            // day est ton champ contenant la DATE
            String sql = "SELECT DISTINCT day FROM showtime WHERE movie_id=?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, movieId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("day"));
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ✅ Retourne tous les horaires pour un film ET une date donnée
    public static ArrayList<String> getSchedulesForMovieAndDate(int movieId, String day) {
        ArrayList<String> list = new ArrayList<>();

        try {
            Connection conn = DataSource.createConnection();

            // schedule est ton champ contenant l'heure
            String sql = "SELECT schedule FROM showtime WHERE movie_id=? AND day=?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, movieId);
            stmt.setString(2, day);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("schedule"));
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
