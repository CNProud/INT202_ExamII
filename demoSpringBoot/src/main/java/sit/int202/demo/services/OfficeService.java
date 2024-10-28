package sit.int202.demo.services;

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
        return officeRepository.findById(officeCode).orElse(null);
    }

    public Office addOffice(Office office) {
        if (office.getOfficeCode() == null || officeRepository.existsById(office.getOfficeCode())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Office id %s already exists", office.getOfficeCode()));
        }
        return officeRepository.save(office);
    }

    public Office updateOffice(Office office) {
        if (office.getOfficeCode() == null || !officeRepository.existsById(office.getOfficeCode())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Can't update, Office id %s does not exists", office.getOfficeCode()));
        }
        return officeRepository.save(office);
    }

    public Office deleteOffice(String officeCode) {
        Office office = getOffice(officeCode);
        if (office == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Can't delete, Office id %s does not exists", officeCode));
        }
        officeRepository.deleteById(officeCode);
        return office;
    }
}
