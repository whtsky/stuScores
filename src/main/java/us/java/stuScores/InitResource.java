package us.java.stuScores;

import javax.xml.bind.annotation.XmlRootElement;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import static us.java.stuScores.JDBC.createStatement;

@Path("firstrun")
public class InitResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String checkFirstRun(){
        if(User.count() == 0) {
            return "true";
        }
        return "false";
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public Response addUser(
        @FormParam("username") String userName,
        @FormParam("password") String passWord
    ){
        try {
            Statement statement = createStatement();
            UUID uuid = UUID.randomUUID();
            String token = uuid.toString();
            statement.executeUpdate("INSERT INTO user (name, password, token) VALUES ('" + userName + "', '" + passWord + "', '" + token + "');" );
            return Response.ok(token).build();
        } catch(SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            e.printStackTrace();
            return null;
        }
    }
}