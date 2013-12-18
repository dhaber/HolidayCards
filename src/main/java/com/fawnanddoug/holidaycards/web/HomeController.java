package com.fawnanddoug.holidaycards.web;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.fawnanddoug.holidaycards.domain.HolidayList;
import com.fawnanddoug.holidaycards.service.HolidayListItemRepository;
import com.fawnanddoug.holidaycards.service.HolidayListRepository;

@Controller
public class HomeController {
	
	private final HolidayListRepository holidayListRepository;
	private final HolidayListItemRepository holidayListItemRepository;

	@Autowired
	public HomeController(HolidayListRepository holidayListRepository, HolidayListItemRepository holidayListItemRepository) {
		this.holidayListRepository = holidayListRepository;
		this.holidayListItemRepository = holidayListItemRepository;
	}

	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home(Model model) {
		HolidayList list = getCurrentHolidayList();
		model.addAttribute("currentList", list);
		model.addAttribute("holidayLists", holidayListRepository.findOrderByYear());
		model.addAttribute("holidayListItems", holidayListItemRepository.findByHolidayList(list));
		return "home";
	}
	
	/**
	 * @return  The currently seelcted holiday list or the default one
	 */
	private HolidayList getCurrentHolidayList() {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		
		HolidayList list = (HolidayList) requestAttributes.getAttribute("holidayList", RequestAttributes.SCOPE_SESSION);
		if (list == null) {
			int year = Calendar.getInstance().get(Calendar.YEAR);
			List<HolidayList> lists = this.holidayListRepository.findOrderByYear();
			list = getCurrentHolidayList(lists, year);
			requestAttributes.setAttribute("holidayList", list, RequestAttributes.SCOPE_SESSION);			
		}
		
		return list;
	}

	/**
	 * Return the holiday list for this year or the most recent one if there is none
	 * 
	 * @param lists
	 * @param year
	 * @return
	 */
	private HolidayList getCurrentHolidayList(List<HolidayList> lists, int year) {
		HolidayList lastList = null;
		for (HolidayList list : lists) {
			if (list.getYear() == year) {
				return list;
			}
			lastList = list;
		}
		return lastList;
	}
	
}
