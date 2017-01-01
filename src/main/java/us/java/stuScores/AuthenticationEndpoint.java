package us.java.stuScores;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static us.java.stuScores.JDBC.*;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Random;
import java.util.UUID;

/**
 * Created by 15437 on 2017/1/1.
 */
@Path("/authentication")
public class AuthenticationEndpoint {

    @POST
    @Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
    public Response authenticateUser(@FormParam("username") String username,
                                     @FormParam("password") String password) {

        try {

            // Authenticate the user using the credentials provided
            authenticate(username, password);

            // Issue a token for the user
            String token = issueToken(username);

            // Return the token on the response
            return Response.ok(token).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private void authenticate(String username, String password) throws Exception {
        // Authenticate against a database, LDAP, file or whatever
        Statement statement = getConnection().createStatement();
        ResultSet r = statement.executeQuery("SELECT COUNT(*) AS rowcount FROM user WHERE name = '" + username + "';");
        r.next();
        int rows = r.getInt("rowcount");
        r.close();

        // Throw an Exception if the credentials are invalid
        if(rows == 0){
            Exception e = new Exception();
            throw e;
        }else {
            ResultSet rp = statement.executeQuery("SELECT * FROM user WHERE name = '" + username + "';");
            rp.next();
            String passWord = rp.getString("password");
            if(!(passWord.equals(password))){
                Exception e = new Exception();
                throw e;
            }
        }
    }

    private String issueToken(String username) throws Exception{
        // Issue a token (can be a random String persisted to a database or a JWT token)
        // The issued token must be associated to a user
        // Return the issued token
        UUID uuid = UUID.randomUUID();
        String token = uuid.toString();
        Statement statement = getConnection().createStatement();
        statement.executeUpdate("UPDATE user SET token = '" + token + "' WHERE name = '" + username + "';");
        return token;
    }
}
