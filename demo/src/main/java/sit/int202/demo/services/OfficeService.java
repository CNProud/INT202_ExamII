package sit.int202.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int202.demo.entities.Office;
import sit.int202.demo.repositories.OfficeRepository;

import java.util.List;

@Service
public class OfficeService {
    private final OfficeRepository officeRepository;

    public OfficeService(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    public List<Office> getAllOffices() {
        return officeRepository.findAll();
    }

    public Office getOffice(String officeCode) {
        return officeRepository.findById(officeCode).orElse(null); // ถ้าหาไม่เจอให้ return null กลับไป
    }

    public Office addOffice(Office office) {
        if(office.getOfficeCode() == null || officeRepository.existsById(office.getOfficeCode())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST
                    ,String.format("Office id '%s' already exists", office.getOfficeCode()));
            //BAD_Request : Status Code 400
        }
        return officeRepository.save(office);
    }

    public Office updateOffice(Office office) {
        if(office.getOfficeCode() == null || !officeRepository.existsById(office.getOfficeCode())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST
                    ,String.format("Can't update, office id '%s' does not exists", office.getOfficeCode()));
            //BAD_Request : Status Code 400
        }
        return officeRepository.save(office);
    }

    public Office deleteOffice(String officeCode) {
        Office office = getOffice(officeCode);
        if(office == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST
                    ,String.format("Can't delete, office id '%s' does not exists", officeCode));
            //BAD_Request : Status Code 400
        }
        officeRepository.deleteById(officeCode);
        return office;
    }
}
