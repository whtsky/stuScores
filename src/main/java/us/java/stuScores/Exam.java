package us.java.stuScores;

public class Exam {
	public int subject_id;
	public int date;
	public String name;
	
	public Exam() {}
	
	public Exam(int subject_id, int date, String name) {
		this.subject_id = subject_id;
		this.date = date;
		this.name = name;
	}
}