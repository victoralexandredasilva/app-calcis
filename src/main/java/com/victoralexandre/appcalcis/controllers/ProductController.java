package com.victoralexandre.appcalcis.controllers;

import com.victoralexandre.appcalcis.model.Category;
import com.victoralexandre.appcalcis.model.Product;
import com.victoralexandre.appcalcis.repositories.CategoryRepository;
import com.victoralexandre.appcalcis.repositories.ClientRepository;
import com.victoralexandre.appcalcis.repositories.ProductRepository;
import com.victoralexandre.appcalcis.services.CategoryService;
import com.victoralexandre.appcalcis.services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProductController {

//    <---Dependencies--->

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

//    <---Requests--->

    @GetMapping("/products/create")
    public ModelAndView pageNewProduct() {
        ModelAndView mv = new ModelAndView("createProduct.html");

        return mv;
    }

    @PostMapping("/products/create")
    public ModelAndView createProduct(@RequestParam String name, @RequestParam String supplier, @RequestParam Double costPrice, @RequestParam Double salePrice, @RequestParam Integer quantity, @RequestParam String category) {

        Product existing = productService.findFirstByName(name);

        if(existing != null) {
            return new ModelAndView("redirect:/products/edit/" + existing.getId());
        }

        if(salePrice < costPrice) {
            ModelAndView mv2 = new ModelAndView("redirect:/products/create");
            mv2.addObject("error", "O preço de custo é maior que o preço de venda!");
            return mv2;
        }

        Category newCategory = categoryService.findFirstByName(category);

        if(newCategory == null) {
            ModelAndView mv2 = new ModelAndView("redirect:/products/create");
            mv2.addObject("error", "A categoria informada não foi encontrada no banco de dados");
            return mv2;
        }

        Product newProduct = productService.instantiateProduct(name, supplier, costPrice, salePrice, newCategory, quantity);

        productService.saveProduct(newProduct);

        return new ModelAndView("redirect:/stock");
    }

    @GetMapping("/products/remove/{id}")
    public ModelAndView removeProduct(@PathVariable("id") Long id) {
        Product product = productService.findProductById(id);

        product.setActive(false);

        productService.saveProduct(product);

        return new ModelAndView("redirect:/stock");
    }

    @GetMapping("/products/edit/{id}")
    public ModelAndView pageEditProduct(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("editProduct.html");

        Product product = productService.findProductById(id);

        if(product != null) {
            mv.addObject("product", product);
        }
        else {
            return new ModelAndView("redirect:/stock");
        }

        return mv;
    }

    @PostMapping("/products/edit/{id}")
    public ModelAndView editProduct(@PathVariable("id") Long id,@RequestParam String name, @RequestParam String supplier, @RequestParam Double costPrice, @RequestParam Double salePrice, @RequestParam Integer quantity, @RequestParam String category) {

        Category findedCategory = categoryService.findFirstByName(category);

        if(findedCategory == null) {return new ModelAndView("redirect:/products/edit/" + id);}

        Product product = productService.editProduct(id, name, supplier, findedCategory, quantity, costPrice, salePrice);

        if(product == null) {return new ModelAndView("redirect:/products");}

        productService.saveProduct(product);

        return new ModelAndView("redirect:/stock");
    }

    @GetMapping("/products/inactives")
    public ModelAndView pageInactivesProducts() {

        ModelAndView mv = new ModelAndView("inactives.html");

        mv.addObject("inactives", productService.findProductsByActiveFalse());

        return mv;
    }

    @GetMapping("products/active/{id}")
    public ModelAndView activeProducts(@PathVariable("id") Long id) {

        Product product = productService.findProductById(id);

        if(product == null) {return new ModelAndView("redirect:/products/inactives");}

        product.setActive(true);

        productService.saveProduct(product);

        return new ModelAndView("redirect:/stock");
    }

    @PostMapping("/findProducts")
    public ModelAndView searchProducts(@RequestParam("nameProduct") String nameProduct, @RequestParam("modeFilter") String modeFilter) {

        ModelAndView mv = new ModelAndView("stock.html");

        List<Product> list = productService.searchProducts(nameProduct);

        switch (modeFilter) {
            case "1":
                Collections.sort(list, Comparator.comparing(Product::getName));
                break;
            case "2":
                Collections.sort(list, Comparator.comparing(Product::getName).reversed());
                break;
            case "3":
                Collections.sort(list, Comparator.comparingDouble(Product::getSalePrice));
                break;
            case "4":
                Collections.sort(list, Comparator.comparingDouble(Product::getSalePrice).reversed());
                break;
            case "5":
                Collections.sort(list, Comparator.comparingDouble(Product::getQuantity));
                break;
            case "6":
                Collections.sort(list, Comparator.comparingDouble(Product::getQuantity).reversed());
                break;
        }

        mv.addObject("products", list);
        mv.addObject("productName", nameProduct);

        return mv;
    }

}
