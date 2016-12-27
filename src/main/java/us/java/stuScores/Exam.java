package us.java.stuScores;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Exam {
	public long id;
	public long date;
	public String name;
	
	public Exam(long id, long date, String name) {
		this.id = id;
		this.date = date;
		this.name = name;
	}
}