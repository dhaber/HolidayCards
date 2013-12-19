package com.fawnanddoug.holidaycards.web;

import java.util.Calendar;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.fawnanddoug.holidaycards.domain.Card;
import com.fawnanddoug.holidaycards.domain.HolidayList;
import com.fawnanddoug.holidaycards.domain.HolidayListItem;
import com.fawnanddoug.holidaycards.service.CardRepository;
import com.fawnanddoug.holidaycards.service.HolidayListItemRepository;
import com.fawnanddoug.holidaycards.service.HolidayListRepository;

@Controller
public class HomeController {
	
	private final HolidayListRepository holidayListRepository;
	private final HolidayListItemRepository holidayListItemRepository;
	private final CardRepository cardRepository;

	@Autowired
	public HomeController(HolidayListRepository holidayListRepository, HolidayListItemRepository holidayListItemRepository, CardRepository cardRepository) {
		this.holidayListRepository = holidayListRepository;
		this.holidayListItemRepository = holidayListItemRepository;
		this.cardRepository = cardRepository;
	}

	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home(Model model) {
		HolidayList list = getCurrentHolidayList();
		model.addAttribute("currentList", list);
		model.addAttribute("cards", cardRepository.findOrderByIdDesc());
		model.addAttribute("holidayLists", holidayListRepository.findOrderByYear());
		model.addAttribute("holidayListItems", 
				holidayListItemRepository.findByHolidayList(
						list, 
						new Sort(
								new Sort.Order("address.firstname"), 
								new Sort.Order("address.lastname")
								)
						));
		return "home";
	}
	
	@RequestMapping(value="/currentList", method=RequestMethod.POST)
	public String home(@RequestParam int id) {
		HolidayList list = holidayListRepository.findOne(id);
		RequestContextHolder.currentRequestAttributes().setAttribute("holidayList", list, RequestAttributes.SCOPE_SESSION);
		return "redirect:/";
		
	}
	
	@RequestMapping(value="/{id}/card", method=RequestMethod.POST)
	@ResponseBody
	public String updateCard(@PathParam("id") int id, @RequestParam int cardId) {
		HolidayListItem item = holidayListItemRepository.findOne(id);
		Card card = cardRepository.findOne(cardId);
		
		item.setCard(card);
		holidayListItemRepository.save(item);
		return "success";
		
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
