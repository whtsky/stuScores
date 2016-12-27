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
    public Subject[] getSubject() {
        Subject subject = new Subject();
        return subject.GetAllSubject();
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public Subject[] addSubject (
            @FormParam("name") String name
    ) throws SQLException{
        try{
            Statement statement = createStatement();
            statement.executeUpdate("INSERT INTO subject (name) VALUES ('" + name + "');");
            Subject subject = new Subject();
            return subject.GetAllSubject();
        }catch (SQLException e){
            e.printStackTrace();
            return  null;
        }
    }
}
