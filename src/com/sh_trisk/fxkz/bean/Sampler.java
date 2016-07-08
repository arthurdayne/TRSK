package com.sh_trisk.fxkz.bean;

public class Sampler {
	private String name,vm_no,dig_time,install_time,create_time,status;
	private int code_no;
	private double pos_x,current_1,current_2;
	
	public Sampler(String name, String vm_no, String dig_time, String install_time, String create_time, String status,
			int code_no, double pos_x, double current_1, double current_2) {
		super();
		this.name = name;
		this.vm_no = vm_no;
		this.dig_time = dig_time;
		this.install_time = install_time;
		this.create_time = create_time;
		this.status = status;
		this.code_no = code_no;
		this.pos_x = pos_x;
		this.current_1 = current_1;
		this.current_2 = current_2;
	}

	public Sampler() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVm_no() {
		return vm_no;
	}

	public void setVm_no(String vm_no) {
		this.vm_no = vm_no;
	}

	public String getDig_time() {
		return dig_time;
	}

	public void setDig_time(String dig_time) {
		this.dig_time = dig_time;
	}

	public String getInstall_time() {
		return install_time;
	}

	public void setInstall_time(String install_time) {
		this.install_time = install_time;
	}

	public int getCode_no() {
		return code_no;
	}

	public void setCode_no(int code_no) {
		this.code_no = code_no;
	}

	public double getPos_x() {
		return pos_x;
	}

	public void setPos_x(double pos_x) {
		this.pos_x = pos_x;
	}

	public double getCurrent_1() {
		return current_1;
	}

	public void setCurrent_1(double current_1) {
		this.current_1 = current_1;
	}

	public double getCurrent_2() {
		return current_2;
	}

	public void setCurrent_2(double current_2) {
		this.current_2 = current_2;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
