package ua.com.shop.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.com.shop.shop.dto.request.CharacteristicRequest;
import ua.com.shop.shop.dto.response.CharacteristicResponse;
import ua.com.shop.shop.dto.response.DataResponse;
import ua.com.shop.shop.entity.Category;
import ua.com.shop.shop.entity.Characteristic;
import ua.com.shop.shop.exeption.WrongInputException;
import ua.com.shop.shop.repository.CharacteristicRepository;


import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CharacteristicService {

    @Autowired
    private CharacteristicRepository characteristicRepository;

    @Autowired
    private CategoryService categoryService;

    public CharacteristicResponse save(CharacteristicRequest request) throws WrongInputException {
        return new CharacteristicResponse(characteristicRequestToCharacteristics(request, null));
    }

    public CharacteristicResponse update(CharacteristicRequest request, Long id) throws WrongInputException {
        return new CharacteristicResponse(characteristicRequestToCharacteristics(request, findOne(id)));
    }


    public void delete(Long id) throws WrongInputException {
        characteristicRepository.delete(findOne(id));
    }

    public List<CharacteristicResponse> findAll() {
        return characteristicRepository.findAll().stream()
                .map(CharacteristicResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Characteristic findOne(Long id) throws WrongInputException {
        return characteristicRepository.findById(id)
                .orElseThrow(() -> new WrongInputException("Characteristic with id " + id + " not exists"));
    }

    public CharacteristicResponse findOneById(Long id) throws WrongInputException {
        return new CharacteristicResponse(findOne(id));
    }

    public DataResponse<CharacteristicResponse> findAll(Integer page, Integer size){
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<Characteristic> pageCharacteristic= characteristicRepository.findAll(pageRequest);
        return new DataResponse<CharacteristicResponse>(pageCharacteristic.stream().map(CharacteristicResponse::new).collect(Collectors.toList()), pageCharacteristic);
    }


    public List<CharacteristicResponse> findAllSelector() {
        return characteristicRepository.findAll().stream()
                .map(CharacteristicResponse::new)
                .collect(Collectors.toList());
    }

    private Characteristic characteristicRequestToCharacteristics(CharacteristicRequest request, Characteristic characteristic) throws WrongInputException {
        if (characteristic == null) {
            characteristic = new Characteristic();
        }

        characteristic.setName(request.getName());
        characteristic = characteristicRepository.save(characteristic);

        for (Long category : request.getCategories()) {
            Category category1 = categoryService.findOne(category);
            characteristic.getCategories().add(category1);
        }

        return characteristicRepository.save(characteristic);
    }
}
