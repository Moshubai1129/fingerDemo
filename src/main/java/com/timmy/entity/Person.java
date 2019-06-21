package com.timmy.entity;

public class Person {
    private int id;

    private String name;

    private Integer rollId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getRollId() {
        return rollId;
    }

    public void setRollId(Integer rollId) {
        this.rollId = rollId;
    }

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", rollId=" + rollId
				+ "]";
	}
    
    
}