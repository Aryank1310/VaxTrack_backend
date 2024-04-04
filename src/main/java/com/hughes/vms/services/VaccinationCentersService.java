package com.hughes.vms.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hughes.vms.model.Vaccination_centers;
import com.hughes.vms.repository.VaccinationCentersRepository;

@Service
public class VaccinationCentersService {
	@Autowired
	VaccinationCentersRepository vcRepo;
	
	public List<Vaccination_centers> getAllCenters()
	{
		return vcRepo.findAll();
	}
	
	public List<Vaccination_centers> readByPincode(int pincode)
	{
		return vcRepo.readByPincode(pincode);
	}
	 public Vaccination_centers registerVaccinationCenter(String name, String address, String phoneNumber,
	            int pincode, String state, String district) {
	        Vaccination_centers center = new Vaccination_centers();
	        center.setName(name);
	        center.setAddress(address);
	        center.setPhoneNumber(phoneNumber);
	        center.setPincode(pincode);
	        center.setState(state);
	        center.setDistrict(district);

	        return vcRepo.save(center);
	    }

}

