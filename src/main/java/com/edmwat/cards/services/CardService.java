package com.edmwat.cards.services;

import com.edmwat.cards.dto.ApiResponse;
import com.edmwat.cards.models.Card;
import com.edmwat.cards.repository.CardsRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CardService {
    private final CardsRepo cardsRepo;
    private ApiResponse apiResponse;

    public ResponseEntity<?> getAllMemberCards(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Optional<List<Card>> optionalCards = cardsRepo.findByUserId(authentication.getName());

        apiResponse = new ApiResponse("200",true, "Records pulled successfully","",optionalCards.get());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    public ResponseEntity<?> getAllCards(){
        List<Card> optionalCards = cardsRepo.findAll();

        apiResponse = new ApiResponse("200",true, "Records pulled successfully","",optionalCards);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    public ResponseEntity<?> createNewCard(Card card) {
       try{
           Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

           card.setStatus("To Do");
           card.setUserId(authentication.getName());
           card.setCreationDate(LocalDateTime.now());

           Card save = cardsRepo.save(card);
           if(save != null){
               apiResponse = new ApiResponse("200",true, "Record created successfully","",List.of(save));
           }else
               apiResponse = new ApiResponse("400",false, "Record creation failed","");

       }catch (Exception e){
           e.printStackTrace();
           apiResponse = new ApiResponse("500",false, "Internal Server Error",e.getMessage());
       }
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    public ResponseEntity<?> updateCard(Card card) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            Optional<Card> optionalCard = cardsRepo.findByIdAndUserId(card.getId(),authentication.getName());

            if (!optionalCard.isPresent()) {
                apiResponse = new ApiResponse("404", false, "Record not found!", "");
                return new ResponseEntity<>(apiResponse, HttpStatus.OK);
            }

            Card save = cardsRepo.save(card);
            if (save != null) {
                apiResponse = new ApiResponse("200", true, "Record update successfully", "", List.of(save));
            } else
                apiResponse = new ApiResponse("400", false, "Record update failed", "");

        } catch (Exception e) {
            e.printStackTrace();
            apiResponse = new ApiResponse("500", false, "Internal Server Error", e.getMessage());
        }
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    public ResponseEntity<?> deleteCard(Card card){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            Optional<Card> optionalCard = cardsRepo.findByIdAndUserId(card.getId(),authentication.getName());

            if (!optionalCard.isPresent()) {
                apiResponse = new ApiResponse("404", false, "Record not found!", "");
                return new ResponseEntity<>(apiResponse, HttpStatus.OK);
            }

            cardsRepo.delete(card);

            apiResponse = new ApiResponse("200", true, "Record deleted successfully", "");

        } catch (Exception e) {
            e.printStackTrace();
            apiResponse = new ApiResponse("500", false, "Internal Server Error", e.getMessage());
        }
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    public ResponseEntity<?> getCard(long id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            Optional<Card> optionalCard = cardsRepo.findByIdAndUserId(id,authentication.getName());

            if (!optionalCard.isPresent()) {
                apiResponse = new ApiResponse("404", false, "Record not found!", "");
                return new ResponseEntity<>(apiResponse, HttpStatus.OK);
            }
            apiResponse = new ApiResponse("200", true, "Record update successfully", "", List.of(optionalCard.get()));

        } catch (Exception e) {
            e.printStackTrace();
            apiResponse = new ApiResponse("500", false, "Internal Server Error", e.getMessage());
        }
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}