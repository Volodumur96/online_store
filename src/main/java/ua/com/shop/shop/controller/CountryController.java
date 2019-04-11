package ua.com.shop.shop.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.com.shop.shop.dto.request.CountryRequest;
import ua.com.shop.shop.dto.response.CountryResponse;
import ua.com.shop.shop.dto.response.DataResponse;
import ua.com.shop.shop.exeption.WrongInputException;
import ua.com.shop.shop.service.CountryService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/country")
public class CountryController {

    @Autowired
    private CountryService countryService;


    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public DataResponse<CountryResponse> getCountries(@RequestParam Integer page,@RequestParam Integer size){
        return countryService.findAll(page,size);
    }

    @GetMapping("/selector")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public List<CountryResponse> findAll() {
        return countryService.findAllSelector();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public CountryResponse findOne(@PathVariable Long id) throws WrongInputException {
        return countryService.findOneById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public CountryResponse save(@RequestBody CountryRequest countryRequest) {
        return countryService.save(countryRequest);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public CountryResponse update(@RequestBody CountryRequest countryRequest, @RequestParam Long id) throws WrongInputException {
        return countryService.update(countryRequest, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void delete(@PathVariable Long id) throws WrongInputException {
        countryService.delete(id);
    }
}
