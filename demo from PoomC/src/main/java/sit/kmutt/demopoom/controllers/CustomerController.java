package sit.kmutt.demopoom.controllers;

import jakarta.servlet.http.HttpServletResponse;
import sit.kmutt.demopoom.entities.Customer;
import sit.kmutt.demopoom.services.CustomerService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/all")
    public String getAllCustomer(Model model) {
        var customers = customerService.getAllCustomers();
        model.addAttribute("customers",customers);
        return "customer_list";
    }

    @GetMapping("")
    public String getCustomerDetail(@RequestParam int id, Model model) {
        var customer = customerService.getCustomerById(id);
        model.addAttribute("customer", customer);
        model.addAttribute("isDeleteMode", false);
        return "customer_detail";
    }

    @GetMapping("/create")
    public String createForm() {
        return "create_form";
    }

    @PostMapping("/create")
    public void createCustomer(Customer customer, HttpServletResponse httpServletResponse) throws IOException {
        customerService.createCustomer(customer);
        httpServletResponse.sendRedirect("/customers/page");
    }

    @GetMapping("/delete")
    public String deleteCustomer(@RequestParam int id, Model model) {
        var customer = customerService.getCustomerById(id);  // ควรดึงข้อมูลก่อนลบเพื่อแสดงรายละเอียด
        customerService.deleteCustomer(id);  // ลบข้อมูล
        model.addAttribute("customer", customer);
        model.addAttribute("isDeleteMode", true);
        return "customer_detail";
    }

    @GetMapping("/update")
    public String updateForm(@RequestParam int id,Model model) {
        var customer = customerService.getCustomerById(id);
        model.addAttribute("customer",customer);
        return "update_form";
    }

    @PostMapping("/update")
    public void updateCustomer(Customer customer, HttpServletResponse httpServletResponse) throws IOException {
        customerService.updateCustomer(customer);
        httpServletResponse.sendRedirect("/customers?id="+customer.getId());
    }

    @GetMapping("/searchBetween")
    public String searchBetween(@RequestParam int upper, @RequestParam int lower,Model model) {
        var customers = customerService.searchBetween(upper,lower);
        model.addAttribute("customers",customers);
        return "customer_list";
    }

    @GetMapping("/searchContent")
    public String searchContent(@RequestParam String content,Model model) {
        var customers = customerService.searchContent(content);
        model.addAttribute("customers",customers);
        return "customer_list";
    }

    @GetMapping("/searchMix")
    public String searchContent(@RequestParam(required = false) String content,
                                @RequestParam(required = false) Integer upper,
                                @RequestParam(required = false) Integer lower,
                                Model model) {
        // กำหนดค่า default ในกรณีที่ upper หรือ lower เป็น null
        if (upper == null) {
            upper = Integer.MAX_VALUE;  // กำหนด upper สูงสุดเท่าที่ข้อมูลรองรับ
        }
        if (lower == null) {
            lower = Integer.MIN_VALUE;  // กำหนด lower ต่ำสุดเท่าที่ข้อมูลรองรับ
        }

        // ดึงข้อมูลลูกค้าที่ตรงตามเงื่อนไข
        var customers = customerService.searchMix(content, upper, lower);
        model.addAttribute("customers", customers);

        // เพิ่มค่า upper, lower, content กลับไปที่ model เพื่อใช้แสดงในฟอร์ม
        model.addAttribute("upper", upper == Integer.MAX_VALUE ? null : upper);
        model.addAttribute("lower", lower == Integer.MIN_VALUE ? null : lower);
        model.addAttribute("content", content);

        return "customer_list";
    }

    @GetMapping("/page")
    public String pageContent(@RequestParam(defaultValue = "10") int pageSize,
                              @RequestParam(defaultValue = "0") int pageNumber,Model model) {
        var customers = customerService.getAllCustomers(PageRequest.of(pageNumber,pageSize));
        model.addAttribute("customers",customers);
        return "customer_list_page";
    }
    
}
