package com.sh_trisk.fxkz;

public class Project {

	private int type, id;
	private String name, from, to, start, end;

	public Project(int type, int id, String name, String from, String to, String start, String end) {
		super();
		this.type = type;
		this.id = id;
		this.name = name;
		this.from = from;
		this.to = to;
		this.start = start;
		this.end = end;
	}

	public Project() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

}
