/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author 932562301
 */
public class user {
    private int Id;
    private String mail;
    private String password;
    private String statu;
    
    public void setID(int ID){
        Id=ID;
    }
    public int getId(){
        return Id;
    }
    public void setMail(String mail){
        this.mail=mail;
    }
    public String getMail(){
        return mail;
    }
    public void setpassword(String password){
        this.password=password;
    }
    public String getpassword(){
        return password;
    }
    public void setStatu(String statu){
        this.statu=statu;
    }
    public String getStatu(){
        return statu;
    }
}
