package us.java.stuScores;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by 15437 on 2016/11/21.
 */
@XmlRootElement
public class Subject {
    public String name;
    public long id;
    public Subject() {}

    public Subject(String name, long id){
        this.name = name;
        this.id = id;
    }
}
