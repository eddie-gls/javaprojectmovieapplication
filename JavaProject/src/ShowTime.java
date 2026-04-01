import java.sql.*;
import java.util.ArrayList;
import java.util.*;

// Helper class used to fetch available show dates and schedules for movies.
public class ShowTime {

    // Returns all distinct available dates for a given movie id.
    public static ArrayList<String> getDatesForMovie(int movieId) {
        ArrayList<String> list = new ArrayList<>();

        try {
            Connection conn = DataSource.createConnection();

            // Query unique dates from showtime table for selected movie.
            String sql = "SELECT DISTINCT day FROM showtime WHERE movie_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, movieId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Convert SQL Date to String format used by combo boxes.
                String date = rs.getDate("day").toString();
                list.add(date);
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Returns all schedules for one movie on one selected day.
    public static ArrayList<String> getSchedulesForMovieAndDate(int movieId, String day) {
        ArrayList<String> list = new ArrayList<>();

        try {
            Connection conn = DataSource.createConnection();

            // Query schedules filtered by movie and day.
            String sql = "SELECT schedule FROM showtime WHERE movie_id=? AND day=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, movieId);
            stmt.setString(2, day);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Convert SQL Time to String for UI display.
                String time = rs.getTime("schedule").toString();
                list.add(time);
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    

    
        
    }


