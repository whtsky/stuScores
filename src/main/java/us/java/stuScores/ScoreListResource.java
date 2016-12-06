package us.java.stuScores;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static us.java.stuScores.JDBC.*;
import java.sql.*;

/**
 * Created by 15437 on 2016/12/5.
 */
@Path("score")
public class ScoreListResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Score[] getScore() throws SQLException{
        try {
            Statement statement = createStatement();
            ResultSet r = statement.executeQuery("SELECT COUNT (*) AS rowcount FROM score;");
            r.next();
            int rows = r.getInt("rowcount");
            r.close();
            ResultSet rs = statement.executeQuery("SELECT * FROM score;");
            Score score[] = new Score[rows];
            int i = 0;
            while (rs.next()){
                score[i++] = new Score(rs.getInt("id"), rs.getLong("student_id"), rs.getString("student_name"), rs.getString("exam_name"), rs.getString("subject_name"), rs.getInt("score"));
            }
            return score;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public void addScore(
            @FormParam("id") String id,
            @FormParam("student_id") String student_id,
            @FormParam("exam_id") String exam_id,
            @FormParam("subject_id") String subject_id,
            @FormParam("score") String score
    ) throws SQLException{
        try
        {
            Statement statement = getConnection().createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            ResultSet st_name_rs = statement.executeQuery("SELECT name FROM student WHERE id = " + Long.parseLong(student_id) + ";");
            st_name_rs.next();
            String st_name = st_name_rs.getString("name");
            ResultSet ex_name_rs = statement.executeQuery("SELECT name FROM exam WHERE id = '" + exam_id + "';");
            ex_name_rs.next();
            String ex_name = ex_name_rs.getString("name");
            ResultSet su_name_rs = statement.executeQuery("SELECT name FROM subject WHERE id = '" + subject_id + "';");
            su_name_rs.next();
            String su_name = su_name_rs.getString("name");
            statement.executeUpdate("INSERT INTO score (id, student_id, exam_name, score, student_name, subject_name) VALUES (" + Integer.parseInt(id) + "," + Long.parseLong(student_id) + ",'" + ex_name + "'," + Integer.parseInt(score) + ",'" + st_name + "','" + su_name + "');");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
}
