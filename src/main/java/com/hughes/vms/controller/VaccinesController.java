package com.hughes.vms.controller;


import com.hughes.vms.model.Vaccines;
import com.hughes.vms.services.VaccinesService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class VaccinesController {

    @Autowired
    VaccinesService vaccinesService;

    @PostMapping("/vaccines/register")
    public Vaccines insertVaccine(@RequestBody Vaccines vaccine) {
        return vaccinesService.insertVaccine(vaccine);
    }
    
    @RequestMapping(value="/vaccines",method=RequestMethod.GET)
	public List<Vaccines> readVaccines(){
		return vaccinesService.getVaccines();
	}
}
