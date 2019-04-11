package ua.com.shop.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.com.shop.shop.dto.request.CharacteristicValueRequest;
import ua.com.shop.shop.dto.response.CharacteristicValueResponse;
import ua.com.shop.shop.dto.response.DataResponse;
import ua.com.shop.shop.entity.CharacteristicValue;
import ua.com.shop.shop.entity.Product;
import ua.com.shop.shop.exeption.WrongInputException;
import ua.com.shop.shop.repository.CharacteristicValueRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class CharacteristicValueService {
    @Autowired
    private CharacteristicValueRepository characteristicValueRepository;

    @Autowired
    private CharacteristicService characteristicService;

    @Autowired
    private ProductService productService;

    @Autowired
    UnitOfMeasurementService unitService;

    public CharacteristicValueResponse save(CharacteristicValueRequest request) throws WrongInputException {
        return new CharacteristicValueResponse(characteristicRequestToCharacteristics(request, null));
    }

    public CharacteristicValueResponse update(CharacteristicValueRequest request, Long id) throws WrongInputException {
        return new CharacteristicValueResponse(characteristicRequestToCharacteristics(request, findOne(id)));
    }


    public void delete(Long id) throws WrongInputException {
        characteristicValueRepository.delete(findOne(id));
    }

    public List<CharacteristicValueResponse> findAll() {
        return characteristicValueRepository.findAll().stream()
                .map(CharacteristicValueResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public CharacteristicValue findOne(Long id) throws WrongInputException {
        return characteristicValueRepository.findById(id)
                .orElseThrow(() -> new WrongInputException("Characteristic with id " + id + " not exists"));
    }

    public CharacteristicValueResponse findOneById(Long id) throws WrongInputException {
        return new CharacteristicValueResponse(findOne(id));
    }

    public DataResponse<CharacteristicValueResponse> findAll(Integer page, Integer size){
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<CharacteristicValue> pageCharacteristic = characteristicValueRepository.findAll(pageRequest);
        return new DataResponse<CharacteristicValueResponse>(pageCharacteristic.stream().map(CharacteristicValueResponse::new).collect(Collectors.toList()), pageCharacteristic);
    }

    private CharacteristicValue characteristicRequestToCharacteristics(CharacteristicValueRequest request, CharacteristicValue characteristic) throws WrongInputException {
        if (characteristic == null) {
            characteristic = new CharacteristicValue();
        }

        characteristic.setValue(request.getValue());
        characteristic.setUnitOfMeasurement(unitService.findOne(request.getUnitOfMeasurement()));
        characteristic.setCharacteristic(characteristicService.findOne(request.getCharacteristic()));
        characteristic = characteristicValueRepository.save(characteristic);



        for (Long product : request.getProducts()) {
            Product product1 = productService.findOne(product);
            characteristic.getProducts().add(product1);
        }

        return characteristicValueRepository.save(characteristic);
    }
}
