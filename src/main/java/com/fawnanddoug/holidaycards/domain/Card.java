package com.fawnanddoug.holidaycards.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Card implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue
    private int id;
    
    @Column(nullable=false)
    private String name;
	
    @Column(nullable=false, name="\"count\"")
    private int count;
    
    protected Card() {}
    
    public Card(String name, int count) {
    	this.name = name;
    	this.count = count;
    }

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getCount() {
		return count;
	}
    
}
