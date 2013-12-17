package com.fawnanddoug.holidaycards.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class HolidayListItem implements Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private long id;
    
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
    
    @Column
    private boolean gift;

    @Column(name="sentcard")
    private boolean sentCard;
    
    @Column(name="receivedcard")
    private boolean receivedCard;
    
    protected HolidayListItem() {}
    
    public HolidayListItem(HolidayList holidayList, Address address, CardType cardType, Card card, boolean gift, boolean sentCard, boolean receivedCard) {
    	this.holidayList = holidayList;
    	this.address = address;
    	this.cardType = cardType;
    	this.card = card;
    	this.gift = gift;
    	this.sentCard = sentCard;
    	this.receivedCard = receivedCard;
    }

	public long getId() {
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

}
