package us.java.stuScores;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
/**
 * Created by 15437 on 2016/11/21.
 */
@Path("subject")
public class SubjectListResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)

    public Subject[] getSubject() {
        Subject subjects[] = new Subject[2];
        subjects[0] = new Subject("C语言", "01");
        subjects[1] = new Subject("数据结构", "02");
        return subjects;
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public Subject addSubject(
            @FormParam("name") String name,
            @FormParam("id") String id
    ){
        return new Subject(name,id);
    }
}
