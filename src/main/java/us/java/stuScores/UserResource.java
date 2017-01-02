package us.java.stuScores;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import static us.java.stuScores.JDBC.*;
import static us.java.stuScores.Student.GetAllStudent;
import static us.java.stuScores.User.GetAllUser;

import java.sql.*;

/**
 * Created by 15437 on 2017/1/1.
 */
@Path("/user/{id}")
public class UserResource {

    @Secured
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser (@PathParam("id") String id){
        try {
            Statement statement = createStatement();
            ResultSet g_rs =  statement.executeQuery("SELECT * FROM user WHERE id = " + id + ";");
            g_rs.next();
            return new User(g_rs.getInt("id"), g_rs.getString("name"));
        }catch (SQLException e){
            return null;
        }
    }

    @Secured
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public User[] modifyUser (
        @PathParam("id") String id,
        @FormParam("password") String passWord
    ){
        try {
            Statement statement = createStatement();
            statement.executeUpdate("UPDATE user SET password = '" + passWord + "'WHERE id = " + id + ";");
            return GetAllUser();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Secured
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public User[] deleteUser(@PathParam("id") String id){
        try {
            Statement statement = createStatement();
            statement.executeUpdate("DELETE FROM user WHERE id = " + id + ";");
            return GetAllUser();
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
