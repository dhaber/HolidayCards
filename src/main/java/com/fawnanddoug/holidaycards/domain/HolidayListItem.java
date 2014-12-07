package com.fawnanddoug.holidaycards.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="holidaylistitem")
public class HolidayListItem implements Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private int id;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="holidaylistid")
    private HolidayList holidayList;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="addressid")
    private Address address;
        
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="cardtypeid")
    private CardType cardType;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="cardid")
    private Card card;
    
    @Column(columnDefinition = "bit")
    private boolean gift;

    @Column(name="sentcard", columnDefinition = "bit")
    private boolean sentCard;
    
    @Column(name="receivedcard", columnDefinition = "bit")
    private boolean receivedCard;
    
    @Column(name="confirmedaddress", columnDefinition = "bit")
    private boolean confirmedAddress;
    
    protected HolidayListItem() {}
    
    public HolidayListItem(HolidayList holidayList, Address address, CardType cardType, Card card, boolean gift, boolean sentCard, boolean receivedCard, boolean confirmedAddress) {
    	this.holidayList = holidayList;
    	this.address = address;
    	this.cardType = cardType;
    	this.card = card;
    	this.gift = gift;
    	this.sentCard = sentCard;
    	this.receivedCard = receivedCard;
    	this.confirmedAddress = confirmedAddress;
    }

	public int getId() {
		return id;
	}

	public HolidayList getHolidayList() {
		return holidayList;
	}

	public Address getAddress() {
		return address;
	}

	public CardType getCardType() {
		return cardType;
	}

	public Card getCard() {
		return card;
	}

	public boolean isGift() {
		return gift;
	}

	public boolean isSentCard() {
		return sentCard;
	}

	public boolean isReceivedCard() {
		return receivedCard;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public void setReceivedCard(boolean receivedCard) {
		this.receivedCard = receivedCard;
	}

	public boolean isConfirmedAddress() {
		return confirmedAddress;
	}

	public void setConfirmedAddress(boolean confirmedAddress) {
		this.confirmedAddress = confirmedAddress;
	}

}
