package com.fawnanddoug.holidaycards.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fawnanddoug.holidaycards.domain.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

}
