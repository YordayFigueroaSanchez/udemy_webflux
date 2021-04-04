package com.yfsanchez.springboot.webflux.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.yfsanchez.springboot.webflux.app.models.dao.ProductoDao;
import com.yfsanchez.springboot.webflux.app.models.documents.Producto;

import reactor.core.publisher.Flux;

@Controller
public class ProductoContoller {
	
	@Autowired
	private ProductoDao productoDao;
	
	@GetMapping({"/listar", "/"})
	public String listar(Model model) {
		Flux<Producto> productos = productoDao.findAll();
		
		model.addAttribute("productos", productos);
		model.addAttribute("titulo", "Lista en Flux");
		
		return "listar";
	} 

}
