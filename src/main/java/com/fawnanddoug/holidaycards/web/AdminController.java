package com.fawnanddoug.holidaycards.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

	/**
	 * Display the Admin Page
	 * @param model
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String admin(Model model) {
		model.addAttribute("cards", cardRepository.findOrderByIdDesc());
		model.addAttribute("holidayLists", holidayListRepository.findOrderByYear());
		return "admin";
	}
	
	/**
	 * Add a new card with the given name and count
	 * 
	 * @param name
	 * @param count
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="/card", method=RequestMethod.POST)
	public String newCard(@RequestParam String name, @RequestParam int count, RedirectAttributes redirectAttributes) {
		Card card = new Card(name, count);
		card = cardRepository.save(card);
		redirectAttributes.addFlashAttribute("message", "Added card " + card.getName());
		return "redirect:/admin";
	}
	
	/**
	 * Add a new list for the given year
	 * 
	 * @param year
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="/list", method=RequestMethod.POST)
	public String newList(@RequestParam int year, RedirectAttributes redirectAttributes) {
		
		// Create the new list
		HolidayList list = holidayListRepository.save(new HolidayList(year));
		
		// If this is the first list create it from the addresses
		if (holidayListRepository.count() == 1) {
			createItemsFromAddresses(list);
			
		// otherwise use the previous list as a template
		} else {
			createItemsFromPrevious(list);
		}
		
		redirectAttributes.addFlashAttribute("message", "Added Holiday List For Year " + list.getYear());
		return "redirect:/admin";
	}
	
	/**
	 * Use all available addresses to create holiday list items
	 * 
	 * @param list
	 */
	private void createItemsFromAddresses(HolidayList list) {
		
		// Use the default card
		Card card = cardRepository.findOne(0);
		
		// Find all addresses to the list
		List<Address> addresses = addressRepository.findAll();
		
		for (Address address : addresses) {
			holidayListItemRepository.save(
					new HolidayListItem(
							list, 
							address, 
							CardType.HOLIDAY, 
							card, 
							false, 		// no gift 
							false, 		// haven't sent card
							false, 		// haven't received a card
							false		// haven't confirmed address
							));	
		}		
		
	}
	
	/**
	 * Use the previous list as a template for the new one
	 * 
	 * @param list
	 */
	private void createItemsFromPrevious(HolidayList list) {
		HolidayList lastYear = holidayListRepository.findByYear(list.getYear() - 1);
		// Find the previous items
		List<HolidayListItem> items = holidayListItemRepository.findByHolidayList(lastYear, null);
		
		for (HolidayListItem item : items) {
			holidayListItemRepository.save(
					new HolidayListItem(
							list, 
							item.getAddress(), 
							item.getCardType(), 
							item.getCard(), 
							false, 		// no gift 
							false, 		// haven't sent card
							false,		// haven't received a card
							false		// haven't confirmed address
							));
							
			
		}
	}	
	
}
