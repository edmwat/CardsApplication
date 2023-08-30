package com.edmwat.cards.controller;

import com.edmwat.cards.models.Card;
import com.edmwat.cards.services.CardService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member/card")
@AllArgsConstructor
public class MemberCardsController {

    private final CardService cardService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllCard(){
        return cardService.getAllMemberCards();
    }

    @GetMapping
    public ResponseEntity<?> getCard(@RequestParam("id") long id){

        return cardService.getCard(id);
    }
    @PostMapping("/add_new")
    public ResponseEntity<?> addNewCard(@RequestBody Card card){
        return cardService.createNewCard(card);
    }

    @PutMapping("/update_card")
    public ResponseEntity<?> updateCardInfo(@RequestBody Card card){

        return cardService.updateCard(card);
    }
    @DeleteMapping("/delete_card")
    public ResponseEntity<?> deleteCard(@RequestBody Card card){
        return cardService.deleteCard(card);
    }
}
