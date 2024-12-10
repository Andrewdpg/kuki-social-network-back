package com.kuki.social_networking.repository;

import com.kuki.social_networking.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, String> {
}
