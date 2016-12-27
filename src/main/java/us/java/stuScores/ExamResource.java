package us.java.stuScores;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static us.java.stuScores.JDBC.*;
import java.sql.*;


/**
 * Created by 15437 on 2016/11/21.
 */
@Path("/exam/{id}")
public class ExamResource{
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Exam getExam(@PathParam("id") String id) throws SQLException{
        try{
            Statement statement = createStatement();
            ResultSet g_rs = statement.executeQuery("SELECT * FROM exam WHERE id = '" + id + "';");
            g_rs.next();
            return new Exam(g_rs.getLong("id"), g_rs.getLong("date"), g_rs.getString("name"));
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public Exam modifyExam(
            @PathParam("id") String id,
            @FormParam("name") String name,
            @FormParam("date") String date
    ) throws SQLException{
        try{
            Statement statement = createStatement();
            statement.executeUpdate("UPDATE exam SET name = '" + name + "', " + "date = " + Long.parseLong(date) + " WHERE id = '" + id + "';");
            ResultSet p_rs = statement.executeQuery("SELECT * FROM exam WHERE id = '" + id + "';");
            p_rs.next();
            return new Exam(p_rs.getLong("id"), p_rs.getLong("date"), p_rs.getString("name"));
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Exam[] deleteExam (
    	@PathParam("id") String id
    ) throws SQLException{
        try{
            Statement statement = createStatement();
            statement.executeUpdate("DELETE FROM exam WHERE id = '" + id + "';");
            ResultSet r = statement.executeQuery("SELECT COUNT (*) AS rowcount FROM exam;");
            r.next();
            int rows = r.getInt("rowcount");
            r.close();
            ResultSet d_rs = statement.executeQuery("SELECT * FROM exam;");
            Exam exams[] = new Exam[rows];
            int i = 0;
            while (d_rs.next()){
                exams[i++] = new Exam(d_rs.getLong("id"), d_rs.getLong("date"), d_rs.getString("name"));
            }
            return exams;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
