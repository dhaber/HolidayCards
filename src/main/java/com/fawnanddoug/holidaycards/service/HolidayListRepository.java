package com.fawnanddoug.holidaycards.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fawnanddoug.holidaycards.domain.HolidayList;

public interface HolidayListRepository extends JpaRepository<HolidayList, Integer> {

	@Cacheable("holidaylists")
	@Query("select h from holidaylist h order by year asc")
	public List<HolidayList> findOrderByYear();
	
	public HolidayList findByYear(int year);
	
}
