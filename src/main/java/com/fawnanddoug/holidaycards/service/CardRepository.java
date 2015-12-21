package com.fawnanddoug.holidaycards.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fawnanddoug.holidaycards.domain.Card;

public interface CardRepository extends JpaRepository<Card, Integer> {

	//@Cacheable("cards")
	@Query("select c from Card c order by id desc")
	public List<Card> findOrderByIdDesc();
	
	@Query("select c from Card c order by name asc")
	public List<Card> findOrderByNameAsc();
	
	@Query("select c from Card c where id = 35")
	public Card findNoneCard();

}
