package us.java.stuScores;

/**
 * Created by 15437 on 2016/11/21.
 */
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("exam")
public class StudentListResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)

    public Student[] getStudent() {
        Student students[] = new Student[2];
        students[0] = new Student("吴昊天", "2015220201018");
        students[1] = new Student("天", "2015220201033");
        return students;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public Student addStudent(
            @FormParam("name") String name,
            @FormParam("id") String id
    ){
        return new Student(name,id);
    }
}