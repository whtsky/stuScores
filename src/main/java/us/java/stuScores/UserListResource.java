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
    public Boolean addUser(
        @FormParam("userName") String userName,
        @FormParam("passWord") String passWord
    ){
        try
        {
            Statement statement = createStatement();

            ResultSet r = statement.executeQuery("SELECT COUNT(*) AS rowcount FROM user WHERE name = '" + userName + "';");
            r.next();
            int rows = r.getInt("rowcount");
            r.close();

            if (rows != 0) {
                return false;
            }
            statement.executeUpdate("INSERT INTO user (name, password) VALUES ('" + userName + "','" + passWord + "');");
            return true;
        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            e.printStackTrace();
            return null;
        }
    }
}