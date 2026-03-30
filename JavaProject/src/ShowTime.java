import java.sql.*;
import java.util.ArrayList;
import java.util.*;

public class ShowTime {

    public static ArrayList<String> getDatesForMovie(int movieId) {
        ArrayList<String> list = new ArrayList<>();

        try {
            Connection conn = DataSource.createConnection();

            String sql = "SELECT DISTINCT day FROM showtime WHERE movie_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, movieId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String date = rs.getDate("day").toString();   // ✅ IMPORTANT
                list.add(date);
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static ArrayList<String> getSchedulesForMovieAndDate(int movieId, String day) {
        ArrayList<String> list = new ArrayList<>();

        try {
            Connection conn = DataSource.createConnection();

            String sql = "SELECT schedule FROM showtime WHERE movie_id=? AND day=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, movieId);
            stmt.setString(2, day);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
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


