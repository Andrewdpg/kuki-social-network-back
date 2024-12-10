package com.kuki.social_networking.service.implementation;

import com.kuki.social_networking.model.Country;
import com.kuki.social_networking.repository.CountryRepository;
import com.kuki.social_networking.service.interfaces.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This service provides methods to interact with the country repository.
 */
@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    /**
     * Retrieves a list of all countries from the repository.
     *
     * @return a list of {@link Country} objects representing all countries.
     */
    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }
}