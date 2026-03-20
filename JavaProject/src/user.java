/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class user {

    private int id;
    private String email;
    private String password;
    private String statut;

    // -------- CONSTRUCTEURS --------
    public user() {}

    public user(String email, String password, String statut) {
        this.email = email;
        this.password = password;
        this.statut = statut;
    }

    // -------- GETTERS / SETTERS --------
    public int getId() { return id; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getStatut() { return statut; }

    public void setId(int id) { this.id = id; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setStatut(String statut) { this.statut = statut; }

    // --------- METHODES SQL ---------

    // Vérifie si un email existe déjà
    public static boolean emailExists(String email) {
        try {
            Connection conn = DataSource.createConnection();
            String sql = "SELECT * FROM user WHERE email = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, email);

            ResultSet rs = st.executeQuery();
            return rs.next(); // true = email déjà utilisé
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Ajoute un user dans la base
    public boolean addUser() {
        try {
            Connection conn = DataSource.createConnection();
            String sql = "INSERT INTO user (email, password, statut) VALUES (?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, this.email);
            st.setString(2, this.password);
            st.setString(3, this.statut);

            st.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Supprime un user par email
    public static boolean deleteUser(String email) {
        try {
            Connection conn = DataSource.createConnection();
            String sql = "DELETE FROM user WHERE email = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, email);

            st.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Vérifie login et retourne un User rempli
    public static user login(String email, String password) {
        try {
            Connection conn = DataSource.createConnection();
            String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, email);
            st.setString(2, password);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                user u = new user();
                u.id = rs.getInt("id");
                u.email = rs.getString("email");
                u.password = rs.getString("password");
                u.statut = rs.getString("statut");
                return u; // Login réussi
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // Login incorrect
    }
}
