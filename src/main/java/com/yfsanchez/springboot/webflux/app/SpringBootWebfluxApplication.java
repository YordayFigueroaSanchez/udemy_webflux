package com.yfsanchez.springboot.webflux.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.yfsanchez.springboot.webflux.app.models.dao.ProductoDao;
import com.yfsanchez.springboot.webflux.app.models.documents.Producto;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootWebfluxApplication implements CommandLineRunner{

	@Autowired
	private ProductoDao dao;
	
	private final static Logger log = LoggerFactory.getLogger(SpringBootWebfluxApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebfluxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Flux.just(
				new Producto("Product01",100.11),
				new Producto("Product02",200.22),
				new Producto("Product03",300.33),
				new Producto("Product04",400.44),
				new Producto("Product05",500.55),
				new Producto("Product06",600.66),
				new Producto("Product07",700.77),
				new Producto("Product08",800.88),
				new Producto("Product09",900.99)
				)
		.flatMap(producto -> dao.save(producto))
		.subscribe(producto -> log.info("Insert "+ producto.getId()+" " + producto.getNombre()));
		
	}

}
