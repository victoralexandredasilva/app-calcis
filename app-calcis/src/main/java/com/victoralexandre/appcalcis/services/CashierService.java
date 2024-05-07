package com.victoralexandre.appcalcis.services;


import com.victoralexandre.appcalcis.model.Sale;


import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.*;


@Service
public class CashierService {

    @Autowired
    private SaleService saleService;


    public Double totalCost(String modePeriodCashier) {
        List<Sale> sales = filterPeriodCashier(modePeriodCashier);
        double sum = 0;
        for(Sale sale : sales) {
            sum += sale.getProduct().getCostPrice() * sale.getQuantity();
        }
        return sum;
    }

    public Double totalInvoicing(String modePeriodCashier) {
        List<Sale> sales = filterPeriodCashier(modePeriodCashier);

        double sum = 0;
        for(Sale sale : sales) {
            sum += sale.getTotal();
        }

       return sum;
    }

    public Integer totalSaleQuantity(String modePeriodCashier) {
        List<Sale> sales = filterPeriodCashier(modePeriodCashier);

        Integer quantity = sales.size();

        return quantity;
    }

    public Double grossProfit(String modePeriodCashier) {
        double sale = totalInvoicing(modePeriodCashier);
        double cost = totalCost(modePeriodCashier);

        double totalInvoicing = sale - cost;

        return totalInvoicing;
    }


    public Double profitPercent(String modePeriodCashier) {

        Double percent = (grossProfit(modePeriodCashier) * 100) / totalInvoicing(modePeriodCashier);

        if(percent.isNaN()) {
            return 0.00;
        }

        return percent;
    }



    public List<Sale> filterPeriodCashier(String modePeriodCashier) {

        LocalDateTime start = null;

        switch (modePeriodCashier) {
            case "HOJE":
                start = LocalDate.now().atStartOfDay();
                break;
            case "ONTEM":
                start = LocalDate.now().minusDays(1).atStartOfDay();
                System.out.println("AQUI ESTÁ A SEGUINTE DATA = " + start);
                break;
            case "7 DIAS":
                start = LocalDate.now().minusDays(7).atStartOfDay();
                break;
            case "1 MÊS":
                start = LocalDate.now().minusDays(30).atStartOfDay();
                break;
            case "2 MESES":
                start = LocalDate.now().minusMonths(2).atStartOfDay();
                break;
            case "3 MESES":
                start = LocalDate.now().minusMonths(3).atStartOfDay();
                break;
            case "ULTIMO ANO":
                start = LocalDate.now().minusYears(1).atStartOfDay();
                break;
            default:
                start = LocalDate.now().minusDays(7).atStartOfDay();
        }

        List<Sale> list = saleService.findSaleMomentBetween(start, LocalDateTime.now());

        return list ;
    }

}
