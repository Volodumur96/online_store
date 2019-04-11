package ua.com.shop.shop.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.com.shop.shop.dto.request.CategoryRequest;
import ua.com.shop.shop.dto.request.CharacteristicRequest;
import ua.com.shop.shop.dto.response.CategoryResponse;
import ua.com.shop.shop.dto.response.CharacteristicResponse;
import ua.com.shop.shop.dto.response.DataResponse;
import ua.com.shop.shop.entity.Characteristic;
import ua.com.shop.shop.exeption.WrongInputException;
import ua.com.shop.shop.service.CharacteristicService;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/characteristic")
public class CharacteristicController {

    @Autowired
    private CharacteristicService characteristicService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public DataResponse<CharacteristicResponse> getCharacteristics(@RequestParam Integer page, @RequestParam Integer size){
        return characteristicService.findAll(page,size);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public CharacteristicResponse findOne(@PathVariable Long id) throws WrongInputException {
        return characteristicService.findOneById(id);
    }

    @GetMapping("/selector")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public List<CharacteristicResponse> findAll() {
        return characteristicService.findAllSelector();
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public CharacteristicResponse save(@RequestBody @Valid CharacteristicRequest characteristicRequest) throws WrongInputException {
        return characteristicService.save(characteristicRequest);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public CharacteristicResponse update(@RequestBody CharacteristicRequest characteristicRequest, @RequestParam Long id) throws WrongInputException {
        return characteristicService.update(characteristicRequest, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void delete(@PathVariable Long id) throws WrongInputException {
        characteristicService.delete(id);
    }
}
