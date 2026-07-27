package com.cognizant.loan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.loan.model.Loan;

@RestController
public class LoanController {

    /**
     * Get loan details by loan number
     * 
     * @param number Loan number
     * @return Loan details
     */
    @GetMapping("/loans/{number}")
    public Loan getLoan(@PathVariable String number) {
        // Dummy response without any backend connectivity
        return new Loan(number, "car", 400000.0, 3258.0, 18);
    }
}