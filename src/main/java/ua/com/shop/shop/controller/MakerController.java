package ua.com.shop.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.com.shop.shop.dto.request.MakerRequest;
import ua.com.shop.shop.dto.response.DataResponse;
import ua.com.shop.shop.dto.response.MakerResponse;
import ua.com.shop.shop.entity.Maker;
import ua.com.shop.shop.exeption.WrongInputException;
import ua.com.shop.shop.service.MakerService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/maker")
public class MakerController {

    @Autowired
    private MakerService makerService;

    @GetMapping("/public")
    public DataResponse<MakerResponse> getMakers(@RequestParam Integer page,@RequestParam Integer size){
        return makerService.findAll(page,size);
    }

    @GetMapping("/public/{id}")
    public MakerResponse findOne(@PathVariable Long id) throws WrongInputException {
        return makerService.findOneById(id);
    }

    @GetMapping("/public/selector")
    public List<MakerResponse> findAll() {
        return makerService.findAllSelector();
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public MakerResponse save(@RequestBody MakerRequest makerRequest) throws WrongInputException {
        return makerService.save(makerRequest);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public MakerResponse update( @RequestParam Long id, @RequestBody MakerRequest makerRequest) throws WrongInputException {
        return makerService.update(id, makerRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void delete(@PathVariable Long id) throws WrongInputException {
        makerService.delete(id);
    }
}
