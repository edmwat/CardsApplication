package com.edmwat.cards.controller;

import com.edmwat.cards.services.CardService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminCardsController {
    private final CardService cardService;

    @GetMapping("/cards/all")
    public ResponseEntity<?> getAllCard(){
        return cardService.getAllCards();
    }
}
