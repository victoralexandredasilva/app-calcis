package com.victoralexandre.appcalcis.model;

import com.victoralexandre.appcalcis.enums.FormOfPayment;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Table(name="sales")
public class Sale implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime moment;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    private Integer quantity;

    private FormOfPayment payment;


    private Double total;

    public Sale(Long id, LocalDateTime moment, Product product, Integer quantity, Client client, FormOfPayment payment) {
        this.id = id;
        this.moment = moment;
        this.product = product;
        this.quantity = quantity;
        this.client = client;
        this.payment= payment;
    }

    public Sale() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getMoment() {
        return moment;
    }

    public void setMoment(LocalDateTime moment) {
        this.moment = moment;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public FormOfPayment getPayment() {
        return payment;
    }

    public void setPayment(FormOfPayment payment) {
        this.payment = payment;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    //Métodos
    public void executarVenda() {
        int quantityStock = this.product.getQuantity();

        if(product.getQuantity() > 0) {
            quantityStock -= this.quantity;
            this.product.setQuantity(quantityStock);
        }
        else {
            System.out.println("Não há Produtos Suficientes no Estoque");
        }

    }

    public void total() {
        this.total =  quantity * this.product.getSalePrice();
    }
}
