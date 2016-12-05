package us.java.stuScores;

/**
 * Created by 15437 on 2016/11/21.
 */
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static us.java.stuScores.JDBC.*;
import java.sql.*;

@Path("exam")
public class ExamListResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)

    public Exam[] getExam() throws SQLException{
        try{
            Statement statement = createStatement();
            ResultSet r = statement.executeQuery("SELECT COUNT (*) AS rowcount FROM exam;");
            r.next();
            int rows = r.getInt("rowcount");
            r.close();
            ResultSet rs = statement.executeQuery("SELECT * FROM exam;");
            Exam exams[] = new Exam[rows];
            int i = 0;
            while (rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("name");
                Long date = rs.getLong("date");
                exams[i++] = new Exam(id, date, name);
            }
            return exams;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public void addStudent(
            @FormParam("name") String name,
            @FormParam("id") String id,
            @FormParam("date") String date
    ) throws SQLException{
        try{
            Statement statement = getConnection().createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("INSERT INTO exam (id, date, name) VALUES ('" + id + "'," + Long.parseLong(date) + ",'" + name + "');");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}