package br.com.cadastro;

import br.com.cadastro.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private ProductService productService;

    public Application(ProductService productService) {
        this.productService = productService;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        productService.initial(scanner);
    }
}

