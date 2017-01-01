package us.java.stuScores;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static us.java.stuScores.JDBC.*;
import static us.java.stuScores.Subject.GetAllSubject;

import java.sql.*;

/**
 * Created by 15437 on 2016/11/21.
 */

@Path("/subject/{id}")
public class SubjectResource {
    @Secured
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Subject getSubject(@PathParam("id") String id){
        try{
            Statement statement = createStatement();
            ResultSet g_rs =  statement.executeQuery("SELECT name FROM subject WHERE id = " + id + ";");
            g_rs.next();
            return new Subject(g_rs.getString("name"), Long.valueOf(id));
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Secured
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public Subject modifySubject(
            @PathParam("id") String id,
            @FormParam("name") String name
    ){
        try {
            Statement statement = createStatement();
            statement.executeUpdate("UPDATE subject SET name = '" + name + "'WHERE id = " + id + ";");
            ResultSet p_rs = statement.executeQuery("SELECT name FROM subject WHERE id = " + id + ";");
            p_rs.next();
            return new Subject(p_rs.getString("name"), Long.valueOf(id));
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Secured
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Subject[] deleteSubject(@PathParam("id") String id){
        try {
            Statement statement = createStatement();
            statement.executeUpdate("DELETE FROM subject WHERE id = " + id + ";");
            return GetAllSubject();
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
