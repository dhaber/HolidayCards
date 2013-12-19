package com.fawnanddoug.holidaycards.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fawnanddoug.holidaycards.domain.Address;
import com.fawnanddoug.holidaycards.domain.Card;
import com.fawnanddoug.holidaycards.domain.CardType;
import com.fawnanddoug.holidaycards.domain.HolidayList;
import com.fawnanddoug.holidaycards.domain.HolidayListItem;
import com.fawnanddoug.holidaycards.service.AddressRepository;
import com.fawnanddoug.holidaycards.service.CardRepository;
import com.fawnanddoug.holidaycards.service.HolidayListItemRepository;
import com.fawnanddoug.holidaycards.service.HolidayListRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	private final HolidayListRepository holidayListRepository;
	private final HolidayListItemRepository holidayListItemRepository;
	private final CardRepository cardRepository;
	private final AddressRepository addressRepository;

	@Autowired
	public AdminController(HolidayListRepository holidayListRepository, HolidayListItemRepository holidayListItemRepository, CardRepository cardRepository, AddressRepository addressRepository) {
		this.holidayListRepository = holidayListRepository;
		this.holidayListItemRepository = holidayListItemRepository;
		this.cardRepository = cardRepository;
		this.addressRepository = addressRepository;
	}		

	@RequestMapping(method=RequestMethod.GET)
	public String admin(Model model) {
		model.addAttribute("cards", cardRepository.findOrderByIdDesc());
		model.addAttribute("holidayLists", holidayListRepository.findOrderByYear());
		return "admin";
	}
	
	@RequestMapping(value="/card", method=RequestMethod.POST)
	public String newCard(@RequestParam String name, @RequestParam int count, RedirectAttributes redirectAttributes) {
		Card card = new Card(name, count);
		card = cardRepository.save(card);
		redirectAttributes.addFlashAttribute("message", "Added card " + card.getName());
		return "redirect:/admin";
	}
	
	@RequestMapping(value="/list", method=RequestMethod.POST)
	@Transactional
	public String newList(@RequestParam int year, @RequestParam int defaultCardId, RedirectAttributes redirectAttributes) {
		// Make sure we got a good default card
		Card card = cardRepository.findOne(defaultCardId);
		
		// Now create the list
		HolidayList list = holidayListRepository.save(new HolidayList(year));
				
		// Now add all addresses to the list
		List<Address> addresses = addressRepository.findAll();
		
		for (Address address : addresses) {
			holidayListItemRepository.save(
					new HolidayListItem(
							list, 
							address, 
							CardType.HOLIDAY, 
							card, 
							false, 			// no gift 
							false, 		// haven't sent card
							false));	// haven't received a card
		}
		
		redirectAttributes.addFlashAttribute("message", "Added Holiday List For Year " + list.getYear());
		return "redirect:/admin";
	}
	

	
}
