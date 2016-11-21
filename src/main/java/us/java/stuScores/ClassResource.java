package us.java.stuScores;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by 15437 on 2016/11/21.
 */
@Path("/class/{id}")
public class ClassResource{
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public StudentClass getStudentClass(@PathParam("id") String id) {
        return new StudentClass("MIAOMIAOMIAO", id);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public StudentClass modifyStudentClass(
            @PathParam("id") String id,
            @FormParam("name") String name
    ) {
        return new StudentClass(name, id);
    }
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteStudentClass (
    	@PathParam("id") String id
    ) {
    	return "删除成功";
    }
    
}
