/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author gallo
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class movie {
    private String moviename;
    private int id;
    private String genre;
    private String publicationdate;
    private String urltrailer;
    private int runningtime;
    private float price;
    private float discount;
    private String picture;

    public movie(String moviename, String genre, String publicationdate, String urltrailer, int runningtime, float price, float discount, String picture) {
        this.moviename = moviename;
        this.genre = genre;
        this.publicationdate = publicationdate;
        this.urltrailer = urltrailer;
        this.runningtime = runningtime;
        this.price = price;
        this.discount = discount;
        this.picture = picture;
    }

    private movie() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getMoviename() {
        return moviename;
    }

    public int getId() {
        return id;
    }

    public String getGenre() {
        return genre;
    }

    public String getPublicationdate() {
        return publicationdate;
    }

    public String getUrltrailer() {
        return urltrailer;
    }

    public int getRunningtime() {
        return runningtime;
    }

    public float getPrice() {
        return price;
    }

    public float getDiscount() {
        return discount;
    }

    public String getPicture() {
        return picture;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setPublicationdate(String publicationdate) {
        this.publicationdate = publicationdate;
    }

    public void setUrltrailer(String urltrailer) {
        this.urltrailer = urltrailer;
    }

    public void setRunningtime(int runningtime) {
        this.runningtime = runningtime;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
    // Vérify if a movie already exist
    public static boolean movieExists(String moviename) {
        try {
            Connection conn = DataSource.createConnection();
            String sql = "SELECT * FROM movie WHERE moviename = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, moviename);

            ResultSet rs = st.executeQuery();
            return rs.next(); // true = email déjà utilisé
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    // Add a movie
    public boolean addMovie() {
        try {
            Connection conn = DataSource.createConnection();
            String sql = "INSERT INTO movie (id, moviename, genre, publicationdate, urltrailer, runningtime, price, discount, picture) VALUES (?, ?, ?,?,?,?,?,?,?)";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, this.moviename);
            st.setString(2, this.genre);
            st.setString(3, this.publicationdate);
            st.setString(4, this.urltrailer);
            st.setInt(5, this.runningtime);
            st.setFloat(6, this.price);
            st.setFloat(7,this.discount);
            st.setString(8,this.picture);
            

            st.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean deleteMovie(String moviename) {
        try {
            Connection conn = DataSource.createConnection();
            String sql = "DELETE FROM movie WHERE moviename = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, moviename);

            st.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static movie login(String moviename, String genre, String publicationdate, String urltrailer, int runningtime, float price, float discount, String picture) {
        try {
            Connection conn = DataSource.createConnection();
            String sql = "SELECT * FROM movie WHERE moviename = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, moviename);
            

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                movie m = new movie();
                m.id=rs.getInt("id");
                m.moviename = rs.getString("moviename");
                m.genre = rs.getString("genre");
                m.publicationdate= rs.getString("publicationdate");
                m.urltrailer = rs.getString("urltrailer");
                m.runningtime=rs.getInt("runningtime");
                m.price=rs.getFloat("price");
                m.discount= rs.getFloat("discount");
                m.picture= rs.getString("picture");
                
                return m; // Login réussi
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // Login incorrect
    }
}
