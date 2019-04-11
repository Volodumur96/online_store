package ua.com.shop.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.com.shop.shop.dto.request.MakerRequest;
import ua.com.shop.shop.dto.response.DataResponse;
import ua.com.shop.shop.dto.response.MakerResponse;
import ua.com.shop.shop.entity.Maker;
import ua.com.shop.shop.exeption.WrongInputException;
import ua.com.shop.shop.repository.MakerRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MakerService {

    @Autowired
    private MakerRepository makerRepository;

    @Autowired
    private CountryService countryService;

    public MakerResponse save(MakerRequest makerRequest) throws WrongInputException {
        return new MakerResponse(makerRequestToMaker(null, makerRequest));
    }

    public MakerResponse update(Long id, MakerRequest makerRequest) throws WrongInputException {
        return new MakerResponse(makerRequestToMaker(findOne(id), makerRequest));
    }

    public void delete(Long id) throws WrongInputException {
        makerRepository.delete(findOne(id));
    }

    @Transactional
    public Maker findOne(Long id) throws WrongInputException {
        return makerRepository.findById(id).orElseThrow(() -> new WrongInputException("Maker with id " + id + " not exists"));
    }

    public MakerResponse findOneById(Long id) throws WrongInputException {
        return new MakerResponse(findOne(id));
    }

    public DataResponse<MakerResponse> findAll(Integer page, Integer size){
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<Maker> pageMaker = makerRepository.findAll(pageRequest);
        return new DataResponse<MakerResponse>(pageMaker.stream().map(MakerResponse::new).collect(Collectors.toList()), pageMaker);
    }

    public List<MakerResponse> findAllSelector() {
        return makerRepository.findAll().stream()
                .map(MakerResponse::new)
                .collect(Collectors.toList());
    }

    private Maker makerRequestToMaker(Maker maker, MakerRequest request) throws WrongInputException {
        if (maker == null) {
            maker = new Maker();
        }
        maker.setCountry(countryService.findOne(request.getCountryId()));
        maker.setName(request.getName());
        return makerRepository.save(maker);
    }
}
