package us.java.stuScores;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static us.java.stuScores.JDBC.*;
import java.sql.*;
/**
 * Created by 15437 on 2016/11/21.
 */
@Path("subject")
public class SubjectListResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)

    public Subject[] getSubject() throws SQLException {
        try {
            Statement statement = createStatement();
            ResultSet r = statement.executeQuery("SELECT COUNT (*) AS rowcount FROM subject;");
            r.next();
            int rows = r.getInt("rowcount");
            r.close();
            ResultSet rs = statement.executeQuery("SELECT * FROM subject;");
            Subject subjects[] = new Subject[rows];
            int i = 0;
            while (rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("name");
                subjects[i++] = new Subject(name, id);
            }
            return subjects;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public void addSubject (
            @FormParam("name") String name,
            @FormParam("id") String id
    ) throws SQLException{
        try{
            Statement statement = createStatement();
            statement.executeUpdate("INSERT INTO subject (id, name) VALUES ('" + id + "','" + name + "');");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
