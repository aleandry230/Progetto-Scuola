package database;

import java.sql.*;

public class DatabaseController {
    private String url;
    private String username = "root";
    private String password = "";
    private Connection con = null;

    public DatabaseController(String url, String username, String password){
        setUrl(url);
        setUsername(username);
        setPassword(password);
    }

    public Connection DB_Connection() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(getUrl(), getUsername(), getPassword());
            setCon(connection);
            System.out.println("DB connected...");
        }catch (SQLException e){
            System.out.println(e.toString());
        }catch (Exception e){
            System.out.println(e.toString());
        }

        return con;

    }

    public void SELECT_VOTI(int student_id){
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM voti INNER JOIN account WHERE voti.student_id = ?");
            stmt.setInt(1, student_id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String id = rs.getString("student_id");
                float vote = rs.getInt("voto");
                String subject = rs.getString("materia");
                System.out.println(id + " " + vote + " " + subject);
            }

        }catch (SQLException e){
            System.out.println(e.toString());
        }
    }

    public int getUserId(String name, String surname){
        int student_id = 0;
        try{
            PreparedStatement stmt = con.prepareStatement(("SELECT id FROM insegnanti WHERE name=? AND surname=?"));
            stmt.setString(1, name);
            stmt.setString(2, surname);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                student_id = rs.getInt("id");
            }

        }catch (SQLException e){
            System.out.println(e.toString());
        }
        return student_id;
    }


    public void INSERT_VOTO(int student_id, String vote, String subject){
        try {
            Statement stmt = con.createStatement();
            String query = "INSERT INTO voti (student_id, vote, subject) VALUES  (?,?,?)";
            PreparedStatement insert = con.prepareStatement(query);
            float vote_parsed = Float.parseFloat(vote);
            insert.setInt(1, student_id);
            insert.setFloat(2, vote_parsed);
            insert.setString(3, subject);
            insert.execute();
        }catch (SQLException e){
            System.out.println(e.toString());
        }
    }

    public void DELETE_VOTO(String subject){
        try{
            String query = "DELETE FROM voti where materia = ?";
            PreparedStatement delete = con.prepareStatement(query);
            delete.setString(1, subject);
            delete.execute();
        }catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public boolean isLogin(String email, String password){
        boolean res = false;
        try{
            PreparedStatement stmt = con.prepareStatement(("SELECT * FROM insegnanti WHERE email=? AND password=? "));
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if(rs.next() == true){
                res = true;
            }

        }catch (SQLException e){
            System.out.println(e.toString());
        }
        return res;
    }

    public User getUserInfo(String email, String password){
        User user = null;

        try{
            PreparedStatement stmt = con.prepareStatement(("SELECT * FROM insegnanti WHERE email=? AND password=?"));
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if(rs.next() == true){
                user = new User(rs.getString("name"),rs.getString("surname"), rs.getInt("id"));
            }

        }catch (SQLException e){
            System.out.println(e.toString());
        }
        return user;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Connection getCon() {
        return con;
    }
}
