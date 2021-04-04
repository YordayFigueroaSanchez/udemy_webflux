package com.yfsanchez.springboot.webflux.app.controllers;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;

import com.yfsanchez.springboot.webflux.app.models.dao.ProductoDao;
import com.yfsanchez.springboot.webflux.app.models.documents.Producto;

import reactor.core.publisher.Flux;

@Controller
public class ProductoContoller {
	
	@Autowired
	private ProductoDao productoDao;
	private static final Logger log = LoggerFactory.getLogger(ProductoContoller.class);
	@GetMapping({"/listar", "/"})
	public String listar(Model model) {
		Flux<Producto> productos = productoDao.findAll()
				.map(producto -> {
					producto.setNombre(producto.getNombre().toUpperCase());
					return producto;
				});
		productos.subscribe(prod -> log.info(prod.getNombre()));
		
		model.addAttribute("productos", productos);
		model.addAttribute("titulo", "Lista en Flux");
		
		return "listar";
	} 
	
	@GetMapping({"/listar-datadrive"})
	public String listarDataDrive(Model model) {
		Flux<Producto> productos = productoDao.findAll()
				.map(producto -> {
					producto.setNombre(producto.getNombre().toUpperCase());
					return producto;
				}).delayElements(Duration.ofSeconds(12));
		
		productos.subscribe(prod -> log.info(prod.getNombre()));
		
		model.addAttribute("productos", new ReactiveDataDriverContextVariable(productos, 2));
		model.addAttribute("titulo", "Lista en Flux");
		
		return "listar";
	} 
	
	@GetMapping({"/listar-full"})
	public String listarFull(Model model) {
		Flux<Producto> productos = productoDao.findAll()
				.map(producto -> {
					producto.setNombre(producto.getNombre().toUpperCase());
					return producto;
				})
				.repeat(4000);
		
		
		model.addAttribute("productos", productos);
		model.addAttribute("titulo", "Lista en Flux");
		
		return "listar";
	} 
	
	@GetMapping({"/listar-chunked"})
	public String listarChunked(Model model) {
		Flux<Producto> productos = productoDao.findAll()
				.map(producto -> {
					producto.setNombre(producto.getNombre().toUpperCase());
					return producto;
				})
				.repeat(4000);
		
		
		model.addAttribute("productos", productos);
		model.addAttribute("titulo", "Lista en Flux");
		
		return "listar-chunked";
	}

}
