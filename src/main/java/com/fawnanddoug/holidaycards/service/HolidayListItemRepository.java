package com.fawnanddoug.holidaycards.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fawnanddoug.holidaycards.domain.HolidayList;
import com.fawnanddoug.holidaycards.domain.HolidayListItem;

public interface HolidayListItemRepository extends JpaRepository<HolidayListItem, Long> {
	
	public List<HolidayListItem> findAllByHolidayList(HolidayList holidayList);

}
