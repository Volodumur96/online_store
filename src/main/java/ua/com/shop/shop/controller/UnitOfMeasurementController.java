package ua.com.shop.shop.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.com.shop.shop.dto.request.UnitOfMeasurementRequest;
import ua.com.shop.shop.dto.response.DataResponse;
import ua.com.shop.shop.dto.response.UnitOfMeasurementResponse;
import ua.com.shop.shop.exeption.WrongInputException;
import ua.com.shop.shop.service.UnitOfMeasurementService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/unit")
public class UnitOfMeasurementController {

    @Autowired
    private UnitOfMeasurementService unitService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public DataResponse<UnitOfMeasurementResponse> getUnits(@RequestParam Integer page, @RequestParam Integer size){
        return unitService.findAll(page,size);
    }

    @GetMapping("/selector")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public List<UnitOfMeasurementResponse> findAll() {
        return unitService.findAllSelector();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public UnitOfMeasurementResponse findOne(@PathVariable Long id) throws WrongInputException {
        return unitService.findOneById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public UnitOfMeasurementResponse save(@RequestBody UnitOfMeasurementRequest unitRequest) {
        return unitService.save(unitRequest);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public UnitOfMeasurementResponse update(@RequestBody UnitOfMeasurementRequest unitRequest, @RequestParam Long id) throws WrongInputException {
        return unitService.update(unitRequest, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void delete(@PathVariable Long id) throws WrongInputException {
        unitService.delete(id);
    }
}
