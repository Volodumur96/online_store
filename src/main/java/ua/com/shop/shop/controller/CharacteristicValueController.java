package ua.com.shop.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import ua.com.shop.shop.dto.request.CharacteristicValueRequest;
import ua.com.shop.shop.dto.response.CharacteristicValueResponse;
import ua.com.shop.shop.dto.response.DataResponse;
import ua.com.shop.shop.exeption.WrongInputException;
import ua.com.shop.shop.service.CharacteristicValueService;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/characteristicValue")
public class CharacteristicValueController {
    @Autowired
    private CharacteristicValueService characteristicValueService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public DataResponse<CharacteristicValueResponse> getCharacteristic(@RequestParam Integer page, @RequestParam Integer size)                                                                     {
        return characteristicValueService.findAll(page,size);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public CharacteristicValueResponse findOne(@PathVariable Long id) throws WrongInputException {
        return characteristicValueService.findOneById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public CharacteristicValueResponse save(@RequestBody @Valid CharacteristicValueRequest characteristicValueRequest) throws WrongInputException {
        return characteristicValueService.save(characteristicValueRequest);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public CharacteristicValueResponse update(@RequestBody CharacteristicValueRequest characteristicValueRequest, @RequestParam Long id) throws WrongInputException {
        return characteristicValueService.update(characteristicValueRequest, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void delete(@PathVariable Long id) throws WrongInputException {
        characteristicValueService.delete(id);
    }
}
