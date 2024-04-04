package com.hughes.vms.repository;

import com.hughes.vms.model.Vaccines;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccinesRepository extends JpaRepository<Vaccines, Long> {


}
