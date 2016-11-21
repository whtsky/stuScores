package us.java.stuScores;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by 15437 on 2016/11/21.
 */
@Path("/student/{ID}")
public class StudentResource{
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Student getStudent(@PathParam("ID") String ID) {
        return new Student("MIAOMIAOMIAO", ID);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public Student modifyStudent(
            @PathParam("ID") String ID,
            @FormParam("name") String name
    ) {
        return new Student(name, ID);
    }
}
