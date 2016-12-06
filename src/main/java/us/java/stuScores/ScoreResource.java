package us.java.stuScores;

/**
 * Created by 15437 on 2016/12/5.
 */
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static us.java.stuScores.JDBC.*;
import java.sql.*;

@Path("/score/{id}")
public class ScoreResource{
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Score getScore (@PathParam("id") String id) throws SQLException {
        try{
            Statement statement = createStatement();
            ResultSet g_rs = statement.executeQuery("SELECT * FROM score WHERE id = " + Integer.parseInt(id) + ";");
            g_rs.next();
            return new Score(Integer.parseInt(id), g_rs.getLong("student_id"), g_rs.getString("student_name"), g_rs.getString("exam_name"), g_rs.getString("subject_name"), g_rs.getInt("score"));
        }catch (SQLException e){
            e.printStackTrace();
            return  null;
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public Score modifyScore (
            @PathParam("id") String id,
            @FormParam("student_id") String student_id,
            @FormParam("exam_id") String exam_id,
            @FormParam("subject_id") String subject_id,
            @FormParam("score") String score
    )throws SQLException {
        try {
            Statement statement = createStatement();
            ResultSet st_name_rs = statement.executeQuery("SELECT name FROM student WHERE id = " + Long.parseLong(student_id) + ";");
            st_name_rs.next();
            String st_name = st_name_rs.getString("name");
            ResultSet ex_name_rs = statement.executeQuery("SELECT name FROM exam WHERE id = '" + exam_id + "';");
            ex_name_rs.next();
            String ex_name = ex_name_rs.getString("name");
            ResultSet su_name_rs = statement.executeQuery("SELECT name FROM subject WHERE id = '" + subject_id + "';");
            su_name_rs.next();
            String su_name = su_name_rs.getString("name");
            statement.executeUpdate("UPDATE score SET student_id = " + Long.parseLong(student_id) + ", exam_name = '" + ex_name + "', " + "subject_name = '" + su_name + "', " + "student_name = '" + st_name + "', score = " + Integer.parseInt(score) + "  WHERE id = " + Integer.parseInt(id) + ";");
            ResultSet p_rs = statement.executeQuery("SELECT * FROM score WHERE id = " + Integer.parseInt(id) + ";");
            p_rs.next();
            return new Score(Integer.parseInt(id), p_rs.getLong("student_id"), p_rs.getString("student_name"), p_rs.getString("exam_name"), p_rs.getString("subject_name"), p_rs.getInt("score"));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Score[] deleteScore(@PathParam("id") String id) throws SQLException{
        try {
            Statement statement = createStatement();
            statement.executeUpdate("DELETE FROM score WHERE id = " + Integer.parseInt(id) +";");
            ResultSet r = statement.executeQuery("SELECT COUNT (*) AS rowcount FROM score;");
            r.next();
            int rows = r.getInt("rowcount");
            r.close();
            ResultSet d_rs = statement.executeQuery("SELECT * FROM score;");
            Score score[] = new Score[rows];
            int i = 0;
            while (d_rs.next()){
                score[i++] = new Score(d_rs.getInt("id"), d_rs.getLong("student_id"), d_rs.getString("student_name"), d_rs.getString("exam_name"), d_rs.getString("subject_name"), d_rs.getInt("score"));
            }
            return score;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
