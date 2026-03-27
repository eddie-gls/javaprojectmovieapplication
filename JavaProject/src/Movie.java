import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
public class Movie {

    private int id;
    private String name;
    private String genre;
    private String publicationDate;
    private String urlTrailer;
    private String runningTime;
    private double price;
    private double discount;
    private String picture;

    public Movie(int id, String name, String genre, String publicationDate,
                 String urlTrailer, String runningTime, double price,
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

    public int getId() { return id; }
    public String getName() { return name; }
    public String getGenre() { return genre; }
    public String getPublicationDate() { return publicationDate; }
    public String getUrlTrailer() { return urlTrailer; }
    public String getRunningTime() { return runningTime; }
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
                        rs.getInt("ID"),
                        rs.getString("moviename"),
                        rs.getString("genre"),
                        rs.getString("publicationdate"),
                        rs.getString("urltrailer"),
                        rs.getString("runningtime"),
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
                        rs.getInt("ID"),
                        rs.getString("moviename"),
                        rs.getString("genre"),
                        rs.getString("publicationdate"),
                        rs.getString("urltrailer"),
                        rs.getString("runningtime"),
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
