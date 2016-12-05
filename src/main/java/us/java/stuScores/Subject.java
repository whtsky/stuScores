package us.java.stuScores;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by 15437 on 2016/11/21.
 */
@XmlRootElement
public class Subject {
    public String name;
    public String id;
    public Subject(){
        name = "";
        id = "";
    }

    public Subject(String name, String id){
        this.name = name;
        this.id = id;
    }
}
