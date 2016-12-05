package us.java.stuScores;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Exam {
	public String id;
	public long date;
	public String name;
	
	public Exam() {
		id = "";
		date = 0;
		name = "";
	}
	
	public Exam(String id, long date, String name) {
		this.id = id;
		this.date = date;
		this.name = name;
	}
}