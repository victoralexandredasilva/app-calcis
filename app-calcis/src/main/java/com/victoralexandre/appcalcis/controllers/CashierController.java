package com.victoralexandre.appcalcis.controllers;

import com.victoralexandre.appcalcis.services.CashierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CashierController {

//    <---Dependencies--->
    @Autowired
    private CashierService cashierService;

//    <---Requests--->

    @GetMapping("/cashier")
    public ModelAndView cashier() {

        ModelAndView mv = new ModelAndView("cashier.html");

        mv.addObject("totalInvoicing", String.format("R$ %.2f", cashierService.totalInvoicing("7 DIAS")));
        mv.addObject("grossProfit", String.format("R$ %.2f",cashierService.grossProfit("7 DIAS")));
        mv.addObject("totalSaleQuantity", cashierService.totalSaleQuantity("7 DIAS"));
        mv.addObject("profitPercent", String.format("%.2f", cashierService.profitPercent("7 DIAS")));
        mv.addObject("modePeriodCashier", "7 DIAS");

        return mv;
    }

    @PostMapping("/filterPeriodCashier")
    public ModelAndView filterPeriodCashier(@RequestParam("modePeriodCashier") String modePeriodCashier) {

        ModelAndView mv = new ModelAndView("/cashier");

        mv.addObject("totalInvoicing", String.format("R$ %.2f",cashierService.totalInvoicing(modePeriodCashier)));
        mv.addObject("grossProfit", String.format("R$ %.2f",cashierService.grossProfit(modePeriodCashier)));
        mv.addObject("totalSaleQuantity", cashierService.totalSaleQuantity(modePeriodCashier));
        mv.addObject("profitPercent", String.format("%.2f", cashierService.profitPercent(modePeriodCashier)));
        mv.addObject("modePeriodCashier", modePeriodCashier);

        return mv;
    }
}
