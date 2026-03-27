import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
public class Movie {

    private int id;
    private String name;
    private String genre;
    private String publicationDate;
    private String urlTrailer;
    private int runningTime;
    private double price;
    private double discount;
    private String picture;

    public Movie(int id, String name, String genre, String publicationDate,
                 String urlTrailer, int runningTime, double price,
                 double discount, String picture) {

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


    // ✅ Récupérer tous les films
    public static ArrayList<Movie> getAllMovies() {

        ArrayList<Movie> list = new ArrayList<>();

        try {
            Connection conn = DataSource.createConnection();
            String sql = "SELECT * FROM movie";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
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
    public boolean addMovie() {
        try {
            Connection conn = DataSource.createConnection();

            String sql = "INSERT INTO movie (moviename, genre, publicationdate, urltrailer, runningtime, price, discount, picture) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql);

            // PARAM 1
            st.setString(1, this.name);

            // PARAM 2
            st.setString(2, this.genre);

            // -----------------------
            // PARAM 3 : publicationdate
            // -----------------------
            String[] parts = this.publicationDate.split("/");

            if (parts.length != 3) {
                JOptionPane.showMessageDialog(null,
                    "La date doit être au format JJ/MM/AAAA.\nExemple : 12/03/2026",
                    "Format incorrect",
                    JOptionPane.ERROR_MESSAGE
                );
                return false; // ON ARRETE ICI → AVANT les autres st.setXXX()
            }

            String mysqlDate = parts[2] + "-" + parts[1] + "-" + parts[0];

            st.setString(3, mysqlDate);   //  <<<<<<<<<<<<<<  PARAMÈTRE 3 OK

            // PARAM 4
            st.setString(4, this.urlTrailer);

            // PARAM 5
            st.setInt(5, this.runningTime);

            // PARAM 6
            st.setDouble(6, this.price);

            // PARAM 7
            st.setDouble(7, this.discount);

            // PARAM 8
            st.setString(8, this.picture);

            // EXECUTION
            st.executeUpdate();
            conn.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean movieExists(String moviename) {
        try {
            Connection conn = DataSource.createConnection();
            String sql = "SELECT 1 FROM movie WHERE moviename = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, moviename);

            ResultSet rs = st.executeQuery();

            boolean exists = rs.next();  // lire AVANT de fermer !

            rs.close();
            st.close();
            conn.close();

            return exists;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // ✅ Récupérer un film par son nom
    public static Movie getMovieByName(String name) {

        try {
            Connection conn = DataSource.createConnection();
            String sql = "SELECT * FROM movie WHERE moviename=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
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