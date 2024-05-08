package com.victoralexandre.appcalcis.controllers;

import com.victoralexandre.appcalcis.model.Product;
import com.victoralexandre.appcalcis.model.Sale;
import com.victoralexandre.appcalcis.repositories.CategoryRepository;
import com.victoralexandre.appcalcis.repositories.ClientRepository;
import com.victoralexandre.appcalcis.repositories.ProductRepository;
import com.victoralexandre.appcalcis.repositories.SaleRepository;
import com.victoralexandre.appcalcis.services.CategoryService;
import com.victoralexandre.appcalcis.services.ClientService;
import com.victoralexandre.appcalcis.services.ProductService;
import com.victoralexandre.appcalcis.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class PageController {

//	<---Dependencies--->

	@Autowired
	private ClientService clientService;

	@Autowired
	private ProductService productService;

	@Autowired
	private SaleService saleService;

	@Autowired
	private CategoryService categoryService;

//	<---Requests--->

	@GetMapping("/home")
	public ModelAndView home() {

		ModelAndView mv = new ModelAndView("home.html");

		return mv;
	}

	@GetMapping("/clients")
	public ModelAndView clients() {

		ModelAndView mv = new ModelAndView("clients.html");
		mv.addObject("clients", clientService.findClientsByActiveTrue());
		return mv;
	}

	@GetMapping("/stock")
	public ModelAndView stock() {
		ModelAndView mv = new ModelAndView("stock.html");

		List<Product> list = productService.findProductsByActiveTrue();

		mv.addObject("products", list);

		return mv;
	}

	@GetMapping("/sales")
	public ModelAndView sales() {

		ModelAndView mv = new ModelAndView("sales.html");

		List<Sale> sales = saleService.getAllSales();
		Collections.reverse(sales);

		mv.addObject("sales", sales);

		return mv;
	}

	@GetMapping("/categories")
	public ModelAndView categories() {
		ModelAndView mv = new ModelAndView("categories.html");

		mv.addObject("categories", categoryService.getAllCategories());

		return mv;
	}
}