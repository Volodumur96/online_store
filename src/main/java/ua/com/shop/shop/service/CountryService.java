package ua.com.shop.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.com.shop.shop.dto.request.CountryRequest;
import ua.com.shop.shop.dto.response.CountryResponse;
import ua.com.shop.shop.dto.response.DataResponse;
import ua.com.shop.shop.entity.Country;
import ua.com.shop.shop.exeption.WrongInputException;
import ua.com.shop.shop.repository.CountryRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public CountryResponse save(CountryRequest request) {
        return new CountryResponse(countryRequestToCountry(request, null));
    }

    public CountryResponse update(CountryRequest request, Long id) throws WrongInputException {
        return new CountryResponse(countryRequestToCountry(request, findOne(id)));
    }


    public void delete(Long id) throws WrongInputException {
        countryRepository.delete(findOne(id));
    }

    public List<CountryResponse> findAll() {
        return countryRepository.findAll().stream()
                .map(CountryResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Country findOne(Long id) throws WrongInputException {
        return countryRepository.findById(id)
                .orElseThrow(() -> new WrongInputException("Country with id " + id + " not exists"));
    }

    public CountryResponse findOneById(Long id) throws WrongInputException {
        return new CountryResponse(findOne(id));
    }

    public DataResponse<CountryResponse> findAll(Integer page, Integer size){
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<Country> pageCountry = countryRepository.findAll(pageRequest);
        return new DataResponse<CountryResponse>(pageCountry.stream().map(CountryResponse::new).collect(Collectors.toList()), pageCountry);
    }

    public List<CountryResponse> findAllSelector() {
        return countryRepository.findAll().stream()
                .map(CountryResponse::new)
                .collect(Collectors.toList());
    }

//    public DataResponse<CountryResponse> findAll(PaginationRequest pagination) {
//        Page<Country> all = countryRepository.findAll(pagination.mapToPageRequest());
//        return new DataResponse<>(all.get().map(CountryResponse::new).collect(Collectors.toList()), all.getTotalPages(), all.getTotalElements());
//    }

    private Country countryRequestToCountry(CountryRequest request, Country country) {
        if (country == null) {
            country = new Country();
        }
        country.setName(request.getName());
        return countryRepository.save(country);
    }
}