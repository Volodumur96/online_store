package ua.com.shop.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.com.shop.shop.dto.request.UnitOfMeasurementRequest;
import ua.com.shop.shop.dto.response.DataResponse;
import ua.com.shop.shop.dto.response.UnitOfMeasurementResponse;
import ua.com.shop.shop.entity.UnitOfMeasurement;
import ua.com.shop.shop.exeption.WrongInputException;
import ua.com.shop.shop.repository.UnitOfMeasurementRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UnitOfMeasurementService {
    @Autowired
    private UnitOfMeasurementRepository unitOfMeasurementRepository;

    public UnitOfMeasurementResponse save(UnitOfMeasurementRequest request) {
        return new UnitOfMeasurementResponse(unitRequestToUnit(request, null));
    }

    public UnitOfMeasurementResponse update(UnitOfMeasurementRequest request, Long id) throws WrongInputException {
        return new UnitOfMeasurementResponse(unitRequestToUnit(request, findOne(id)));
    }


    public void delete(Long id) throws WrongInputException {
        unitOfMeasurementRepository.delete(findOne(id));
    }

    public List<UnitOfMeasurementResponse> findAll() {
        return unitOfMeasurementRepository.findAll().stream()
                .map(UnitOfMeasurementResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public UnitOfMeasurement findOne(Long id) throws WrongInputException {
        return unitOfMeasurementRepository.findById(id)
                .orElseThrow(() -> new WrongInputException("Unit with id " + id + " not exists"));
    }

    public UnitOfMeasurementResponse findOneById(Long id) throws WrongInputException {
        return new UnitOfMeasurementResponse(findOne(id));
    }

    public DataResponse<UnitOfMeasurementResponse> findAll(Integer page, Integer size){
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<UnitOfMeasurement> pageUnit = unitOfMeasurementRepository.findAll(pageRequest);
        return new DataResponse<UnitOfMeasurementResponse>(pageUnit.stream().map(UnitOfMeasurementResponse::new).collect(Collectors.toList()), pageUnit);
    }

    public List<UnitOfMeasurementResponse> findAllSelector() {
        return unitOfMeasurementRepository.findAll().stream()
                .map(UnitOfMeasurementResponse::new)
                .collect(Collectors.toList());
    }


    private UnitOfMeasurement unitRequestToUnit(UnitOfMeasurementRequest request, UnitOfMeasurement unit) {
        if (unit == null) {
            unit = new UnitOfMeasurement();
        }
        unit.setName(request.getName());
        return unitOfMeasurementRepository.save(unit);
    }
}
