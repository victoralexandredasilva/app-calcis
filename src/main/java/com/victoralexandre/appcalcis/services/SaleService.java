package com.victoralexandre.appcalcis.services;

import com.victoralexandre.appcalcis.enums.FormOfPayment;
import com.victoralexandre.appcalcis.model.Client;
import com.victoralexandre.appcalcis.model.Product;
import com.victoralexandre.appcalcis.model.Sale;
import com.victoralexandre.appcalcis.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ClientService clientService;

    public List<Sale> findSaleMomentBetween(LocalDateTime start, LocalDateTime end) {
        return saleRepository.findByMomentBetween(start, end);
    }

    public void createSales(String productName, String clientName, Integer quantity, FormOfPayment payment) throws Exception {

        Product product = productService.findFirstByName(productName);
        Client client = (clientService.findFirstByName(clientName));

        if(product.getQuantity() < 0 || product.getQuantity() < quantity) {
            throw new Exception("ESTOQUE INSUFICIENTE PARA VENDA!! Quantidade de Produtos no Estoque: " + product.getQuantity());
        }

        Sale sale = instantiateSale(product, client, quantity, payment);
        saveSale(sale);

        executeSale(client, product, sale, quantity);

    }

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public Sale instantiateSale(Product product, Client client, Integer quantity, FormOfPayment payment) {

        Sale sale = new Sale();
        sale.setMoment(LocalDateTime.now());
        sale.setClient(client);
        sale.setProduct(product);
        sale.setQuantity(quantity);
        sale.setPayment(payment);

        double total = product.getSalePrice() * quantity;

        sale.setTotal(total);

        return sale;
    }

    public void saveSale(Sale sale) {
        saleRepository.save(sale);
    }

    public void executeSale(Client client, Product product, Sale sale, Integer quantity) {
        client.getSales().add(sale);
        product.setQuantity(product.getQuantity() - quantity);
        product.setTotalValue(product.getSalePrice() * product.getQuantity());
        if(product.getQuantity() == 0) {
            product.setActive(false);
        }
        productService.saveProduct(product);
    }

    public Sale findSaleById(Long id) {
        return saleRepository.findById(id).orElseThrow();
    }

    public void removeSale(Long id) {
        saleRepository.deleteById(id);
    }

    public void editSale(Long id, Product product, Client client, Integer quantity, FormOfPayment payment) {
        Sale sale = findSaleById(id);
        sale.setProduct(product);
        sale.setClient(client);
        sale.setQuantity(quantity);
        sale.setPayment(payment);

        double total = product.getSalePrice() * quantity;
        sale.setTotal(total);

        saveSale(sale);
    }

}
