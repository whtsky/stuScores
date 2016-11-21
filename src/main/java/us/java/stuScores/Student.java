package us.java.stuScores;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by 15437 on 2016/11/21.
 */
@XmlRootElement
public class Student {
    public String name;
    public String ID;

    public Student(){
        name = "";
        ID = "";
    }

    public Student(String name, String id){
        this.name = name;
        this.ID = id;
    }
}
/*
/student -> Array[Student]
/student/<id> -> Studnet
PUT /student/<id? -> Modify
POST /student -> new
*/