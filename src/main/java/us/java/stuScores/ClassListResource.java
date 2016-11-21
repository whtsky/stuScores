package us.java.stuScores;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("class")
public class ClassListResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)

    public StudentClass[] getStudentClass() {
        StudentClass studentClasses[] = new StudentClass[2];
        studentClasses[0] = new StudentClass("一班", "2015220201");
        studentClasses[1] = new StudentClass("二班", "2015220202");
        return studentClasses;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public StudentClass addClass(
            @FormParam("name") String name,
            @FormParam("id") String id
    ){
        return new StudentClass(name,id);
    }
}