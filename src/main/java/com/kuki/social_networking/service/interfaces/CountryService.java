package com.kuki.social_networking.service.interfaces;

import com.kuki.social_networking.model.Country;

import java.util.List;

public interface CountryService {

    /**
     * Retrieves a list of all countries.
     *
     * @return a list of Country objects representing all countries.
     */
    List<Country> getAllCountries();
    
}