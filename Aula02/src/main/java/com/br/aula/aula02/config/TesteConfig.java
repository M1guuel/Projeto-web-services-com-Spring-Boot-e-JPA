package com.br.aula.aula02.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.br.aula.aula02.entities.Category;
import com.br.aula.aula02.entities.Order;
import com.br.aula.aula02.entities.OrderItem;
import com.br.aula.aula02.entities.Payment;
import com.br.aula.aula02.entities.Product;
import com.br.aula.aula02.entities.User;
import com.br.aula.aula02.entities.enums.Enums;
import com.br.aula.aula02.repositories.CategoryRepositories;
import com.br.aula.aula02.repositories.OrderItemRepositories;
import com.br.aula.aula02.repositories.OrderRepositories;
import com.br.aula.aula02.repositories.ProductRepositories;
import com.br.aula.aula02.repositories.UserRepositories;

@Configuration
@Profile("test")
public class TesteConfig implements CommandLineRunner {

	@Autowired
	private UserRepositories userRepositories;
	
	@Autowired
	private OrderRepositories orderRepositories;

	@Autowired
	private CategoryRepositories catRepositories;
	
	@Autowired
	private ProductRepositories productRepositories;
	@Autowired
	private OrderItemRepositories orderItemRepositories;

	@Override
	public void run(String... args) throws Exception {
		
		Category cat1 = new Category(null, "Electronics");
		Category cat2 = new Category(null, "Books");
		Category cat3 = new Category(null, "Computers");
		
		Product p1 = new Product(null, "The Lord of the Rings", "Lorem ipsum dolor sit amet, consectetur.", 90.5, ""); 
		Product p2 = new Product(null, "Smart TV", "Nulla eu imperdiet purus. Maecenas ante.", 2190.0, ""); 
		Product p3 = new Product(null, "Macbook Pro", "Nam eleifend maximus tortor, at mollis.", 1250.0, ""); 
		Product p4 = new Product(null, "PC Gamer", "Donec aliquet odio ac rhoncus cursus.", 1200.0, ""); 
		Product p5 = new Product(null, "Rails for Dummies", "Cras fringilla convallis sem vel faucibus.", 100.99, "");
		productRepositories.saveAll(Arrays.asList(p1,p2,p3,p4,p5));
		p1.getCategory().add(cat2);
		p2.getCategory().add(cat3);
		p2.getCategory().add(cat1);
		p3.getCategory().add(cat3);
		p4.getCategory().add(cat3);
		p5.getCategory().add(cat2);
		User u1 = new User("Maria", "Maria@gmail.com", "1234456");
		User u2 = new User("Pedro", "Exemple2@gmail.com", "123456");

		Order o1 = new Order(null, Enums.PAID, Instant.parse("2019-06-20T19:53:07Z"), u1);
		Order o2 = new Order(null, Enums.WAITIN_PAYMENT, Instant.parse("2019-07-21T03:42:10Z"), u2);
		Order o3 = new Order(null, Enums.WAITIN_PAYMENT, Instant.parse("2019-07-22T15:21:22Z"), u1);

		userRepositories.saveAll(Arrays.asList(u1, u2));
		orderRepositories.saveAll(Arrays.asList(o1, o2, o3));
		catRepositories.saveAll(Arrays.asList(cat1, cat2, cat3));
		productRepositories.saveAll(Arrays.asList(p1,p2,p3,p4,p5));
		
		OrderItem oi1 = new OrderItem(o1, p1, 2, p1.getPrice()); 
		OrderItem oi2 = new OrderItem(o1, p3, 1, p3.getPrice()); 
		OrderItem oi3 = new OrderItem(o2, p3, 2, p3.getPrice()); 
		OrderItem oi4 = new OrderItem(o3, p5, 2, p5.getPrice()); 
		
		 orderItemRepositories.saveAll(Arrays.asList(oi1,oi2,oi3,oi4));
		 
		 Payment pay = new Payment(null, Instant.parse("2019-06-20T21:53:07Z"), o1);
		 o1.setPayment(pay);
		 orderRepositories.save(o1);

	}
}
