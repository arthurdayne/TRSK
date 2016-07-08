package com.sh_trisk.fxkz.bean;

public class Token {
	private int status;
	private String info;
	public Token(int status, String info) {
		super();
		this.status = status;
		this.info = info;
	}
	public Token() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
}
