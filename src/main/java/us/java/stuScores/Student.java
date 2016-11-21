package us.java.stuScores;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by 15437 on 2016/11/21.
 */
@XmlRootElement
public class Student {
    public String name;
    public String id;
    public String class_id;

    public Student(){
        name = "";
        id = "";
    }

    public Student(String name, String id){
        this.name = name;
        this.id = id;
    }
}
/*
/student -> Array[Student]
/student/<id> -> Studnet
PUT /student/<id? -> Modify
POST /student -> new
*/