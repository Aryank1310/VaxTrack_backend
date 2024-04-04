package com.hughes.vms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hughes.vms.model.Vaccination_centers;

import java.util.List;
@Repository
public interface VaccinationCentersRepository extends JpaRepository <Vaccination_centers, Integer>{
    List<Vaccination_centers> readByPincode(int pincode);
}
