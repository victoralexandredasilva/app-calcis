package com.victoralexandre.appcalcis.controllers;

import com.victoralexandre.appcalcis.model.Product;
import com.victoralexandre.appcalcis.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.victoralexandre.appcalcis.model.Client;
import com.victoralexandre.appcalcis.repositories.ClientRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Controller
public class ClientController {

//	<---Dependencies--->
	@Autowired
	private ClientService clientService;

//	<---Requests--->
	@GetMapping("clients/create")
	public ModelAndView pageNewClient() {

		ModelAndView mv = new ModelAndView("createClient.html");

		return mv;
	}
	
	@PostMapping("clients/create")
	public ModelAndView registerClient(@RequestParam String name, @RequestParam String phoneNumber, @RequestParam String email, @RequestParam String adress) {

		clientService.createClient(name, phoneNumber, email, adress);
		
		return new ModelAndView("redirect:/clients");
	}

	@PostMapping("/findClients")
	public ModelAndView filter(@RequestParam("modeFilterClients") String modeFilterClients, @RequestParam("nameClient") String nameClient) {
		ModelAndView mv = new ModelAndView("clients.html");

		List<Client> list = clientService.findClientByName(nameClient);

		switch (modeFilterClients) {
			case "ORDENAR POR NOME (A-Z)":
				Collections.sort(list, Comparator.comparing(Client::getName));
				break;
			case "ORDENAR POR NOME (Z-A)":
				Collections.sort(list, Comparator.comparing(Client::getName).reversed());
				break;
			case "ORDENAR POR PEDIDOS (crescente)":
				Collections.sort(list, Comparator.comparingInt(client -> client.getSales().size()));
				break;
			case "ORDENAR POR PEDIDOS (derescente)":
				Collections.sort(list, (c1, c2) -> Integer.compare(c2.getSales().size(), c1.getSales().size()));
				break;
		}


		mv.addObject("clients", list);
		mv.addObject("nameClient", nameClient);
		mv.addObject("modeFilterClients", modeFilterClients);

		return mv;
	}

	@GetMapping("/clients/remove/{id}")
	public ModelAndView removeClient(@PathVariable Long id) {

		clientService.removeClient(id);

		return new ModelAndView("redirect:/clients");
	}

	@GetMapping("/clients/inactives")
	public ModelAndView inactives() {
		ModelAndView mv = new ModelAndView("clients-inactives.html");

		mv.addObject("inactives", clientService.findClientsByActiveFalse());

		return mv;
	}

	@GetMapping("clients/active/{id}")
	public ModelAndView active(@PathVariable("id") Long id) {

		Client client = clientService.findClientById(id);

		if(client == null) {return new ModelAndView("redirect:/clients/inactives");}

		client.setActive(true);

		clientService.saveClient(client);

		return new ModelAndView("redirect:/clients");
	}

	@GetMapping("/clients/edit/{id}")
	public ModelAndView formEdit(@PathVariable("id") Long id) {

		ModelAndView mv = new ModelAndView("editClient.html");
		Client client = clientService.findClientById(id);

		if(client != null) {
			mv.addObject("client", client);
		}
		else {
			return new ModelAndView("redirect:/clients");
		}

		return mv;
	}

	@PostMapping("/clients/edit/{id}")
	public ModelAndView edit(@PathVariable Long id, @RequestParam String name, @RequestParam String phoneNumber, @RequestParam String email, @RequestParam String adress) {

		clientService.editClient(name, phoneNumber, email, adress, id);

		return new ModelAndView("redirect:/clients");
	}
}
