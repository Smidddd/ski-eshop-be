package sk.umb.eshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import sk.umb.eshop.order.service.OrderService;

@SpringBootApplication
public class EshopApplication{
	public static void main(String[] args) {
		SpringApplication.run(EshopApplication.class, args);
	}
}
