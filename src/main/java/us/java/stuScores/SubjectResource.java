package us.java.stuScores;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static us.java.stuScores.JDBC.*;
import java.sql.*;

/**
 * Created by 15437 on 2016/11/21.
 */

@Path("/subject/{id}")
public class SubjectResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Subject getSubject(@PathParam("id") String id) throws SQLException{
        try{
            Statement statement = createStatement();
            ResultSet g_rs =  statement.executeQuery("SELECT name FROM subject WHERE id = '" + id + "';");
            g_rs.next();
            return new Subject(g_rs.getString("name"), id);
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public Subject modifySubject(
            @PathParam("id") String id,
            @FormParam("name") String name
    ) throws SQLException{
        try {
            Statement statement = createStatement();
            statement.executeUpdate("UPDATE subject SET name = '" + name + "'WHERE id = '" + id + "';");
            ResultSet p_rs = statement.executeQuery("SELECT name FROM subject WHERE id = '" + id + "';");
            p_rs.next();
            return new Subject(p_rs.getString("name"), id);
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Subject[] deleteSubject(@PathParam("id") String id) throws SQLException{
        try {
            Statement statement = createStatement();
            statement.executeUpdate("DELETE FROM subject WHERE id = '" + id +"';");
            ResultSet r = statement.executeQuery("SELECT COUNT (*) AS rowcount FROM subject;");
            r.next();
            int rows = r.getInt("rowcount");
            r.close();
            ResultSet d_rs = statement.executeQuery("SELECT * FROM subject;");
            Subject subjects[] = new Subject[rows];
            int i = 0;
            while (d_rs.next()){
                String rs_id = d_rs.getString("id");
                String rs_name = d_rs.getString("name");
                subjects[i++] = new Subject(rs_name, rs_id);
            }
            return subjects;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
