package us.java.stuScores;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import static us.java.stuScores.JDBC.createStatement;

class FirstRunMessage {
    public boolean firstrun;
    FirstRunMessage(boolean first) {
        this.firstrun = first;
    }
}

@Path("firstrun")
public class InitResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public FirstRunMessage checkFirstRun(){
        if(User.count() == 0) {
            return new FirstRunMessage(true);
        }
        return new FirstRunMessage(false);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public User addUser(
        @FormParam("userName") String userName,
        @FormParam("passWord") String passWord
    ){
        try {
            Statement statement = createStatement();
            UUID uuid = UUID.randomUUID();
            String token = uuid.toString();
            statement.executeUpdate("INSERT INTO user (name, password, token) VALUES ('" + userName + "', '" + passWord + "', '" + token + "');" );
            ResultSet r = statement.executeQuery("SELECT * FROM user WHERE name = '" + userName + "';");
            r.next();
            return new User(r.getInt("id"), r.getString("name"));
        } catch(SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            e.printStackTrace();
            return null;
        }
    }
}