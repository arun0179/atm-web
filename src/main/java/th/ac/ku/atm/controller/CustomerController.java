package th.ac.ku.atm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import th.ac.ku.atm.model.Customer;
import th.ac.ku.atm.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @GetMapping
    public String getCustomerPage(Model model){
        //allCustomers has CustomerService.customerList (use in customer.html)
        model.addAttribute("allCustomers",customerService.getCustomers());
        //return customer.html
        return "customer";
    }

    @PostMapping
    public String registerCustomer(@ModelAttribute Customer customer, Model model){
        //add customer in CustomerService.customerList
        customerService.createCustomer(customer);
        model.addAttribute("allCustomers", customerService.getCustomers());
        return "redirect:customer";
    }

//    ArrayList<Customer> customers = new ArrayList<>();
//    @GetMapping("/customer")
//    @RequestMapping("/customer")
//    public String getCustomerPage(Model model) {
//        ArrayList<String> customers = new ArrayList<>();
//        customers.add("Wendy");
//        customers.add("Brent");
//        customers.add("Irene");
        //object
//        List<Customer> customers = new ArrayList<>();
//        customers.add(new Customer(1,"Wendy",1234));
//        customers.add(new Customer(2,"Brent",2345));
//        customers.add(new Customer(3,"Irene",3456));
//        model.addAttribute("allCustomers", customers);
//        //return customer.html
//        return "customer";
//    }
//    @PostMapping("/customer")
//    public String registerCustomer(@ModelAttribute Customer customer, Model model){
//        customers.add(customer);
//        model.addAttribute("allCustomers", customers);
//        return "redirect:customer";
//    }


}
