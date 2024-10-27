package sit.int202.demo.controllers;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sit.int202.demo.entities.Office;
import sit.int202.demo.repositories.OfficeRepository;
import sit.int202.demo.services.OfficeService;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/offices")
public class OfficeController {
    private final OfficeService service;

    public OfficeController(OfficeService service) {
        this.service = service;
    }

    @GetMapping("/update")
    public String updateForm(@RequestParam String officeCode, Model model) {
        Office office = service.getOffice(officeCode);
        model.addAttribute("office", office);
        return "update_form";
    }

    @PostMapping("/update")
    public void updateOffice(Office office, HttpServletResponse response) throws IOException {
        service.updateOffice(office);
        response.sendRedirect("/offices/all");
    }

    @GetMapping("/create")
    public String createForm() {
        return "create_form";
    }

    @PostMapping("/create")
    public void createOffice(Office office, HttpServletResponse response) throws IOException {
        service.addOffice(office);
        response.sendRedirect("/offices/all");
    }

    @GetMapping("/delete")
    public String deleteOfficeById(@RequestParam String officeCode, Model model) {
        Office office = service.deleteOffice(officeCode);
        model.addAttribute("office", office);
        model.addAttribute("message", "Office deleted successfully");
        return "office_details";
    }

    @GetMapping("")
    public String getOfficeById(@RequestParam String officeCode, Model model) {
        Office office = service.getOffice(officeCode);
        model.addAttribute("office", office);
        return "office_details";
    }

    @GetMapping("/all")
    public String allOffices(Model model) {
        List<Office> officeList = service.getAllOffices();
        model.addAttribute("offices", officeList);
        return "office_list";
    }
}
