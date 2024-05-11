package com.victoralexandre.appcalcis.controllers;

import com.victoralexandre.appcalcis.enums.FormOfPayment;
import com.victoralexandre.appcalcis.model.Client;
import com.victoralexandre.appcalcis.model.Product;
import com.victoralexandre.appcalcis.model.Sale;
import com.victoralexandre.appcalcis.repositories.ClientRepository;
import com.victoralexandre.appcalcis.repositories.ProductRepository;
import com.victoralexandre.appcalcis.repositories.SaleRepository;
import com.victoralexandre.appcalcis.services.ClientService;
import com.victoralexandre.appcalcis.services.ProductService;
import com.victoralexandre.appcalcis.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.Collections;
import java.util.List;

@Controller
public class SaleController {

//    <---Dependencies--->
    @Autowired
    private SaleService saleService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ClientService clientService;

//    <---Requests--->
    @GetMapping("/sales/create")
    public ModelAndView createSales() {
        ModelAndView mv = new ModelAndView("createSales.html");

        mv.addObject("products", productService.findProductsByActiveTrue());
        mv.addObject("clients", clientService.getAllClients());

        return mv;
    }

    @PostMapping("/sales/create")
    public ModelAndView registerSale(@RequestParam String nameProduct, @RequestParam String nameClient, @RequestParam Integer quantity, @RequestParam FormOfPayment payment) throws Exception {

       saleService.createSales(nameProduct, nameClient, quantity, payment);

        return new ModelAndView("redirect:/sales");
    }

    @PostMapping("sales/searchProductCreate")
    public ModelAndView searchProductCreate(@RequestParam("nameProduct") String nameProduct) {

        ModelAndView mv = new ModelAndView("/createSales.html");

        mv.addObject("products", productService.searchProducts(nameProduct));
        mv.addObject("clients", clientService.getAllClients());

        return mv;
    }

    @PostMapping("/findSales")
    public ModelAndView findSales(@RequestParam("modePeriod") String modePeriod) {
        ModelAndView mv = new ModelAndView("/sales");

        LocalDateTime start = null;
        LocalDateTime end = LocalDateTime.now();

        switch (modePeriod) {
            case "0":
                start = LocalDateTime.parse("1970-01-01T00:00:00");

                break;
            case "1":
                start = end.minusDays(7);

                break;
            case "2":
                start = end.minusDays(15);

                break;
            case "3":
                start = end.minusDays(30);

                break;
            case "4":
                start = end.minusDays(60);

                break;
            case "5":
                start = end.minusDays(90);

                break;
            case "6":
                start = end.minusDays(365);

                break;
            case "7":
                start = LocalDate.now().atStartOfDay();

                break;
            default:
                start = LocalDateTime.parse("1970-01-01T00:00:00");

        }


        List<Sale> list = saleService.findSaleMomentBetween(start, end);
        Collections.reverse(list);

        mv.addObject("sales", list);

        return mv;
    }

    @GetMapping("/sales/edit/{id}")
    public ModelAndView formEdit(@PathVariable Long id) {

        ModelAndView mv = new ModelAndView("editSales.html");

        Sale sale = saleService.findSaleById(id);

        mv.addObject("sale", sale);
        mv.addObject("clients", clientService.getAllClients());

        return mv;
    }

    @PostMapping("sales/edit/{id}")
    public ModelAndView edit(@PathVariable Long id, @RequestParam String nameProduct, @RequestParam String nameClient, @RequestParam Integer quantity, @RequestParam FormOfPayment payment ) {

        Client client = clientService.findFirstByName(nameClient);
        Product product = productService.findFirstByName(nameProduct);

        if(client == null || product == null) {
            return new ModelAndView("redirect:/sales");
        }

        saleService.editSale(id, product, client, quantity, payment);

        return new ModelAndView("redirect:/sales");
    }

    @GetMapping("sales/remove/{id}")
    public ModelAndView remove(@PathVariable Long id) {

        saleService.removeSale(id);

        return new ModelAndView("redirect:/sales");
    }

}
