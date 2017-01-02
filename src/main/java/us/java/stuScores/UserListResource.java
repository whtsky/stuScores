package us.java.stuScores;

/**
 * Created by 15437 on 2017/1/1.
 */
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static us.java.stuScores.JDBC.*;
import static us.java.stuScores.Student.GetAllStudent;
import static us.java.stuScores.User.GetAllUser;

import java.sql.*;

@Path("user")
public class UserListResource {

    @Secured
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User[] getUser(){
        return GetAllUser();
    }

    @Secured
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public User[] addUser(
        @FormParam("username") String userName,
        @FormParam("password") String passWord
    ){
        try
        {
            Statement statement = createStatement();
            statement.executeUpdate("INSERT INTO user (name, password) VALUES ('" + userName + "','" + passWord + "');");
            return GetAllUser();
        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            e.printStackTrace();
            return GetAllUser();
        }
    }
}