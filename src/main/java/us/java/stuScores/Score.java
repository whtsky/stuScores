package us.java.stuScores;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by 15437 on 2016/11/21.
 */
@XmlRootElement
public class Score {
    public long id;
    public long student_id;
    public String student_name;
    public String exam_name;
    public String subject_name;
    public int score;

    public Score(){
        id = 0;
        student_id = 0;
        student_name = "";
        exam_name = "";
        subject_name = "";
        score = 0;
    }
    public Score(int id, long student_id, String st_name, String ex_name, String su_name, int score){
        this.id = id;
        this.student_id = student_id;
        this.exam_name = ex_name;
        this.student_name = st_name;
        this.subject_name = su_name;
        this.score = score;
    }
}
