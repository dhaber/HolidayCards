package com.fawnanddoug.holidaycards.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fawnanddoug.holidaycards.domain.HolidayList;

public interface HolidayListRepository extends JpaRepository<HolidayList, Long> {

	@Cacheable("holidaylists")
	public List<HolidayList> findAllOrderByYear();
	
}
