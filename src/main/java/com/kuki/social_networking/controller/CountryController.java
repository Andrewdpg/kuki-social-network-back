package com.kuki.social_networking.controller;


import com.kuki.social_networking.exception.CountryNotFoundException;
import com.kuki.social_networking.mapper.CountryMapper;
import com.kuki.social_networking.model.Country;
import com.kuki.social_networking.repository.CountryRepository;
import com.kuki.social_networking.response.CountryResponse;
import com.kuki.social_networking.service.interfaces.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/country")
@RequiredArgsConstructor
public class CountryController {

    private final CountryMapper countryMapper;
    private final CountryService service;

    @GetMapping("")
    public ResponseEntity<List<CountryResponse>> getCountries(){
        List<Country> countries = service.getAllCountries();
        List<CountryResponse> countryResponses = countries.stream()
                .map(countryMapper::toCountryResponse).toList();
        return ResponseEntity.ok(countryResponses);
    }

    //! Exception Handler

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e){
        return ResponseEntity.status(500).body(e.getMessage());
    }

    @ExceptionHandler(CountryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleIngredientNotFoundException(CountryNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
