package com.fawnanddoug.holidaycards.web;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.fawnanddoug.holidaycards.domain.Address;
import com.fawnanddoug.holidaycards.domain.Card;
import com.fawnanddoug.holidaycards.domain.HolidayList;
import com.fawnanddoug.holidaycards.domain.HolidayListItem;
import com.fawnanddoug.holidaycards.service.AddressRepository;
import com.fawnanddoug.holidaycards.service.CardRepository;
import com.fawnanddoug.holidaycards.service.HolidayListItemRepository;
import com.fawnanddoug.holidaycards.service.HolidayListRepository;

@Controller
public class HomeController {
	
	private final HolidayListRepository holidayListRepository;
	private final HolidayListItemRepository holidayListItemRepository;
	private final CardRepository cardRepository;
	private final AddressRepository addressRepository;

	@Autowired
	public HomeController(HolidayListRepository holidayListRepository, HolidayListItemRepository holidayListItemRepository, CardRepository cardRepository, AddressRepository addressRepository) {
		this.holidayListRepository = holidayListRepository;
		this.holidayListItemRepository = holidayListItemRepository;
		this.cardRepository = cardRepository;
		this.addressRepository = addressRepository;
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
	
	@RequestMapping(value="/hli/{id}/card", method=RequestMethod.POST)
	@ResponseBody
	public String updateCard(@PathVariable int id, @RequestParam int cardId) {
		HolidayListItem item = holidayListItemRepository.findOne(id);
		Card card = cardRepository.findOne(cardId);
		
		item.setCard(card);
		holidayListItemRepository.save(item);
		return "success";
		
	}
	
	@RequestMapping(value="/hli/{id}/received", method=RequestMethod.POST)
	@ResponseBody
	public String updateReceived(@PathVariable int id, @RequestParam(required=false) boolean receivedCard) {
		HolidayListItem item = holidayListItemRepository.findOne(id);		
		item.setReceivedCard(receivedCard);
		holidayListItemRepository.save(item);
		return "success";
		
	}
	
	@RequestMapping(value="/address", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Address updateAddress(@RequestParam int id, 
			@RequestParam(required=false) String address, 
			@RequestParam(required=false) String address2, 
			@RequestParam(required=false) String city, 
			@RequestParam(required=false) String state, 
			@RequestParam(required=false) String zip, 
			@RequestParam(required=false) String country,
			@RequestParam(required=false) String firstname,
			@RequestParam(required=false) String lastname) {
		
		Address item = addressRepository.findOne(id);
		item.setAddress(address);
		item.setAddress2(address2);
		item.setCity(city);
		item.setState(state);
		item.setZip(zip);
		item.setCountry(country);
		item.setFirstname(firstname);
		item.setLastname(lastname);
		
		item = addressRepository.save(item);
		return item;
		
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
