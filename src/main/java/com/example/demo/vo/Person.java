package com.example.demo.vo;

import org.json.JSONObject;

public class Person {
	int id;
	String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + "]";
	}
	
	public  Person returnPerson() {
		
		//synchronized(this) {
			try {
				System.out.println("waiting ");
				//if(Thread.currentThread().isAlive() && !Thread.currentThread().isInterrupted()) {
					Thread.sleep(5000);
					System.out.println("wokup ");
				//}else {
				//	System.out.println("thread is dead::::::::::)");
				//}
				
			}catch(Exception e) {
				//e.printStackTrace();
				System.out.println("THread status-----------------------------------------"+Thread.currentThread().getState());
				System.out.println("interrupted exception happens: "+e.getMessage());
			}
		//}
		
		return this;
	}
	
	public JSONObject toJSON() {
        JSONObject jo = new JSONObject();
        jo.put("id", this.id);
        jo.put("name", this.name);
        return jo;
    }


}
