package com.victoralexandre.appcalcis.services;

import com.victoralexandre.appcalcis.model.Category;
import com.victoralexandre.appcalcis.model.Product;
import com.victoralexandre.appcalcis.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product findFirstByName(String name) {
        Product product = productRepository.findFirstByName(name);

        return product;
    }

    public Product findProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow();

        return product;
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public Product instantiateProduct(String name, String supplier, Double costPrice, Double salePrice, Category category, Integer quantity) {
        Product product = new Product();

        product.setName(name);
        product.setQuantity(quantity);
        product.setCategory(category);
        product.setSupplier(supplier);
        product.setSalePrice(salePrice);
        product.setCostPrice(costPrice);

        Double totalValue = product.getSalePrice() * product.getQuantity();
        product.setTotalValue(totalValue);

        return product;
    }

    public Product editProduct(Long id, String name, String supplier, Category category, Integer quantity, Double costPrice, Double salePrice) {

        Product product = findProductById(id);

        if(product == null) { return null; }

        product.setName(name);
        product.setSupplier(supplier);
        product.setCostPrice(costPrice);
        product.setSalePrice(salePrice);
        product.setQuantity(quantity);
        product.setCategory(category);
        product.setTotalValue(product.getSalePrice() * product.getQuantity());

        if(!product.isActive() && product.getQuantity() > 0) {
            product.setActive(true);
        }

        return product;
    }

    public List<Product> findProductsByActiveFalse() {
        List<Product> products = productRepository.findByActiveFalse();

        return products;
    }

    public List<Product> findProductsByActiveTrue() {
        List<Product> products = productRepository.findByActiveTrue();

        return products;
    }

    public List<Product> searchProducts(String name) {
        List<Product> products = productRepository.findProductByName(name);

        return products;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
