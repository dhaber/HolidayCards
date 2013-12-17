package com.fawnanddoug.holidaycards.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class HolidayList implements Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private long id;
    
    @Column(nullable=false)
    private int year;
    
    protected HolidayList() {}
    
    public HolidayList(int year) {
    	this.year = year;
    }

	public long getId() {
		return id;
	}

	public int getYear() {
		return year;
	}
        
}
