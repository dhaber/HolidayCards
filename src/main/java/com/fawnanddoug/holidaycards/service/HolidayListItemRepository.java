package com.fawnanddoug.holidaycards.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fawnanddoug.holidaycards.domain.Card;
import com.fawnanddoug.holidaycards.domain.CardType;
import com.fawnanddoug.holidaycards.domain.HolidayList;
import com.fawnanddoug.holidaycards.domain.HolidayListItem;

public interface HolidayListItemRepository extends JpaRepository<HolidayListItem, Integer> {
	
	
	public List<HolidayListItem> findByHolidayList(HolidayList holidayList, Sort sort);
	public List<HolidayListItem> findByHolidayListAndConfirmedAddress(HolidayList holidayList, Boolean confirmedAddress, Sort sort);
	public List<HolidayListItem> findByHolidayListAndCard(HolidayList holidayList, Card card, Sort sort);
	public List<HolidayListItem> findByHolidayListAndCardAndConfirmedAddress(HolidayList holidayList, Card card, Boolean confirmedAddress, Sort sort);

}
