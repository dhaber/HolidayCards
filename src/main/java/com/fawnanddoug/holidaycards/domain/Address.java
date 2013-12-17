package com.fawnanddoug.holidaycards.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue
    private long id;
    
    @Column(nullable=false)
    private String firstname;

    @Column(nullable=false)
    private String lastName;
    
    @Column(nullable=true)
    private String spouse;
    
    @Column(nullable=true)
    private String children;
    
    @Column(nullable=true)
    private String greeting;
    
    @Column(nullable=true)
    private String address;
    
    @Column(nullable=true)
    private String address2;
    
    @Column(nullable=true)
    private String city;
    
    @Column(nullable=true)
    private String state;
    
    @Column(nullable=true)
    private String zip;
    
    @Column(nullable=true)
    private String country;
    
    @Column(nullable=true)
    private String email;
    
    protected Address() {}
    
    public Address(String firstname, String lastname, String address, String address2, String city, String state, String zip, String country) {
    	this.firstname = firstname;
    	this.lastName = lastname;
    	this.address = address;
    	this.address2 = address2;
    	this.city = city;
    	this.state = state;
    	this.zip = zip;
    	this.country = country;
    }

	public long getId() {
		return id;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastName() {
		return lastName;
	}

	public String getSpouse() {
		return spouse;
	}

	public String getChildren() {
		return children;
	}

	public String getGreeting() {
		return greeting;
	}

	public String getAddress() {
		return address;
	}

	public String getAddress2() {
		return address2;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getZip() {
		return zip;
	}

	public String getCountry() {
		return country;
	}

	public String getEmail() {
		return email;
	}
    
}