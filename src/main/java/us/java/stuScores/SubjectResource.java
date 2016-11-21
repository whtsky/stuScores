package us.java.stuScores;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by 15437 on 2016/11/21.
 */

@Path("/subject/{id}")
public class SubjectResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Subject getSubject(@PathParam("id") String id) {
        return new Subject("MIAOMIAOMIAO", id);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public Student modifyStudent(
            @PathParam("id") String id,
            @FormParam("name") String name
    ) {
        return new Student(name, id);
    }
}
