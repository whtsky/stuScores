package us.java.stuScores;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by 15437 on 2016/11/21.
 */
@Path("/student/{id}")
public class StudentResource{
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Student getStudent(@PathParam("id") String id) {
        return new Student("MIAOMIAOMIAO", id);
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

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteStudent(@PathParam("id") String id) {
    	return "删除成功";
    }
    
}
