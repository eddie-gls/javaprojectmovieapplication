import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

// Movie domain model + helper methods for CRUD operations on the movie table.
public class Movie {

    // Primary key from database.
    private int id;
    // Display name of the movie.
    private String name;
    // Movie category (Action, Romance, etc.).
    private String genre;
    // Release date as entered/loaded in string format.
    private String publicationDate;
    // Trailer link.
    private String urlTrailer;
    // Duration in minutes.
    private int runningTime;
    // Standard ticket price.
    private double price;
    // Discounted ticket price.
    private double discount;
    // Poster/image path or URL.
    private String picture;

    public Movie(int id, String name, String genre, String publicationDate,
                 String urlTrailer, int runningTime, double price,
                 double discount, String picture) {

        // Constructor used when loading an existing movie from database.
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.publicationDate = publicationDate;
        this.urlTrailer = urlTrailer;
        this.runningTime = runningTime;
        this.price = price;
        this.discount = discount;
        this.picture = picture;
    }

    public Movie(String name, String genre, String publicationDate,
                 String urlTrailer, int runningTime, double price,
                 double discount, String picture) {

        // Constructor used before inserting a new movie.
        this.name = name;
        this.genre = genre;
        this.publicationDate = publicationDate;
        this.urlTrailer = urlTrailer;
        this.runningTime = runningTime;
        this.price = price;
        this.discount = discount;
        this.picture = picture;
    }


    public int getId() { return id; }
    public String getName() { return name; }
    public String getGenre() { return genre; }
    public String getPublicationDate() { return publicationDate; }
    public String getUrlTrailer() { return urlTrailer; }
    public int getRunningTime() { return runningTime; }
    public double getPrice() { return price; }
    public double getDiscount() { return discount; }
    public String getPicture() { return picture; }


    // Retrieve all movies from database.
    public static ArrayList<Movie> getAllMovies() {

        ArrayList<Movie> list = new ArrayList<>();

        try {
            Connection conn = DataSource.createConnection();
            // Select every movie row.
            String sql = "SELECT * FROM movie";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Map each row to a Movie object.
                Movie m = new Movie(
                        rs.getInt("id"),
                        rs.getString("moviename"),
                        rs.getString("genre"),
                        rs.getString("publicationdate"),
                        rs.getString("urltrailer"),
                        rs.getInt("runningtime"),
                        rs.getDouble("price"),
                        rs.getDouble("discount"),
                        rs.getString("picture")
                );
                list.add(m);
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Insert this movie object into the database.
    public boolean addMovie() {
        try {
            Connection conn = DataSource.createConnection();

            String sql = "INSERT INTO movie (moviename, genre, publicationdate, urltrailer, runningtime, price, discount, picture) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql);

            // Parameter 1: movie name.
            st.setString(1, this.name);

            // Parameter 2: genre.
            st.setString(2, this.genre);

            // Parameter 3: convert DD/MM/YYYY to YYYY-MM-DD for MySQL.
            String[] parts = this.publicationDate.split("/");

            if (parts.length != 3) {
                // Stop insertion if the date format is invalid.
                JOptionPane.showMessageDialog(null,
                    "La date doit être au format JJ/MM/AAAA.\nExemple : 12/03/2026",
                    "Format incorrect",
                    JOptionPane.ERROR_MESSAGE
                );
                return false;
            }

            String mysqlDate = parts[2] + "-" + parts[1] + "-" + parts[0];

            st.setString(3, mysqlDate);

            // Parameter 4: trailer URL.
            st.setString(4, this.urlTrailer);

            // Parameter 5: running time.
            st.setInt(5, this.runningTime);

            // Parameter 6: base ticket price.
            st.setDouble(6, this.price);

            // Parameter 7: discounted ticket price.
            st.setDouble(7, this.discount);

            // Parameter 8: image path/link.
            st.setString(8, this.picture);

            // Execute INSERT statement.
            st.executeUpdate();
            conn.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Check if a movie already exists by exact name.
    public static boolean movieExists(String moviename) {
        try {
            Connection conn = DataSource.createConnection();
            String sql = "SELECT 1 FROM movie WHERE moviename = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, moviename);

            ResultSet rs = st.executeQuery();

            // Read result before closing resources.
            boolean exists = rs.next();

            rs.close();
            st.close();
            conn.close();

            return exists;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update a single column for a movie identified by name.
    public static boolean updateField(String moviename, String field, String value) {
        try {
            Connection conn = DataSource.createConnection();
            String sql = "UPDATE movie SET " + field + " = ? WHERE moviename = ?";
            PreparedStatement st = conn.prepareStatement(sql);

            st.setString(1, value);
            st.setString(2, moviename);

            st.executeUpdate();
            conn.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve one movie by its exact name.
    public static Movie getMovieByName(String name) {

        try {
            Connection conn = DataSource.createConnection();
            String sql = "SELECT * FROM movie WHERE moviename=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Build and return a Movie object from the matched row.
                Movie m = new Movie(
                        rs.getInt("id"),
                        rs.getString("moviename"),
                        rs.getString("genre"),
                        rs.getString("publicationdate"),
                        rs.getString("urltrailer"),
                        rs.getInt("runningtime"),
                        rs.getDouble("price"),
                        rs.getDouble("discount"),
                        rs.getString("picture")
                );
                conn.close();
                return m;
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Delete one movie by exact name.
    public static boolean deleteMovie(String moviename) {
        try {
            Connection conn = DataSource.createConnection();
            String sql = "DELETE FROM movie WHERE moviename = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, moviename);

            // Number of deleted rows.
            int rows = st.executeUpdate();

            st.close();
            conn.close();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve one movie by primary key.
    public static Movie getById(int id) {
        try {
            Connection conn = DataSource.createConnection();
            String sql = "SELECT * FROM movie WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Map row to Movie object and return it.
                Movie m = new Movie(
                    rs.getInt("id"),
                    rs.getString("moviename"),
                    rs.getString("genre"),
                    rs.getString("publicationdate"),
                    rs.getString("urltrailer"),
                    rs.getInt("runningtime"),
                    rs.getDouble("price"),
                    rs.getDouble("discount"),
                    rs.getString("picture")
                );
                conn.close();
                return m;
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
//a