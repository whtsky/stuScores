package us.java.stuScores;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static us.java.stuScores.JDBC.createStatement;

@XmlRootElement
public class Exam {
	public long id;
	public String date;
	public String name;
    public long subject;


	public Exam(){}
	
	public Exam(long id, String date, String name, long subject_id) {
		this.id = id;
		this.date = date;
		this.name = name;
        this.subject = subject_id;
	}

	public static Exam[] GetAllExam(){
		try{
			Statement statement = createStatement();
			ResultSet r = statement.executeQuery("SELECT COUNT (*) AS rowcount FROM exam;");
			r.next();
			int rows = r.getInt("rowcount");
			r.close();
			ResultSet rs = statement.executeQuery("SELECT * FROM exam;");
			Exam exams[] = new Exam[rows];
			int i = 0;
			while (rs.next()){
				long id = rs.getLong("id");
				String name = rs.getString("name");
				String date = rs.getString("date");
                long subject = rs.getLong("subject_id");
				exams[i++] = new Exam(id, date, name, subject);
			}
			return exams;
		}catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}
}