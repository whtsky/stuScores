package us.java.stuScores;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import static us.java.stuScores.JDBC.*;
import java.sql.*;

/**
 * Created by 15437 on 2017/1/1.
 */
@Path("/user/{id}")
public class UserResource {

    @Secured
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public void modifyUser (
        @PathParam("id") String id,
        @FormParam("password") String passWord
    ){
        try {
            Statement statement = createStatement();
            statement.executeUpdate("UPDATE user SET password = '" + passWord + "'WHERE id = " + id + ";");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
