package com.fawnanddoug.holidaycards.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="cardtype")
public class CardType implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue
    private int id;
    
    @Column(nullable=false)
    private String name;
    
    protected CardType() {}
    
    public CardType(String name) {
    	this.name = name;
    }

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
}
