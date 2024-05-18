package com.victoralexandre.appcalcis.repositories;

import com.victoralexandre.appcalcis.model.Category;
import com.victoralexandre.appcalcis.model.Product;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ProductRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    ProductRepository productRepository;

    @Test
    @DisplayName("Deve pegar um usuário do BD com sucesso")
    void findProductByNameCase1() {
        String name = "VANS OLD SCHOOL PRETO N36";

        this.createProduct(name, "Ana Mafra", 45.0, 90.0, 8, 720.00, new Category("UNISSEX"));

         List<Product> products = this.productRepository.findProductByName("VANS");
         products.forEach(System.out::println);
         assertThat(products).isNotEmpty();
        }

    @Test
    @DisplayName("Não deve buscar o usuário que não existe")
    void findProductByNameCase2() {
        String name = "VANS OLD SCHOOL PRETO N36";

        List<Product> products = this.productRepository.findProductByName("VANS");

        if (products.isEmpty()) {
            System.out.println("A LISTA ESTÁ VAZIA");
        }

        assertThat(products).isEmpty();
    }

    private Product createProduct(String name, String supplier, Double costPrice, Double salePrice, Integer quantity, Double totalValue, Category category) {

        Product newProduct = new Product(null, name, supplier, costPrice, salePrice, quantity, totalValue, category);
        this.entityManager.persist(newProduct);
        this.entityManager.flush();

        return newProduct;
    }
}