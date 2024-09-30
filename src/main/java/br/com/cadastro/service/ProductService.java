package br.com.cadastro.service;

import br.com.cadastro.model.Product;
import br.com.cadastro.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public void initial(Scanner scanner) {
        while (true) {
            System.out.println("Escolha uma opção: ");
            System.out.println("1. Cadastrar Produto");
            System.out.println("2. Listar Produtos");
            System.out.println("3. Listar informações completa dos produtos");
            System.out.println("0. Sair");
            int option = 0;
            try {
                option = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException ex) {
                System.out.println("Entrada inválida! Informe apenas os NÚMEROS solicitados!");
                scanner.nextLine();
                continue;
            }
            switch (option) {
                case 0:
                    System.out.println("Finalizando aplicação");
                    return;
                case 1:
                    registerProduct(scanner);
                    break;
                case 2:
                    productList(scanner);
                    break;
                case 3:
                    allProducts();
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    public void productList(Scanner scanner) {
        List<Object[]> products = productRepository.findAllProductsOrderedByValue();
        System.out.println("\n------------------------Lista de produtos------------------------------------------------");
        for (Object[] product : products) {
            String name = (String) product[0];
            double value = (double) product[1];
            System.out.println("Nome: " + name + ", Valor: " + value);
        }
        System.out.println("------------------------------------------------------------------------------------------\n");

        System.out.println("Deseja salvar outro produto? (s/n)");
        String resp = scanner.nextLine().trim().toUpperCase();
        if (resp.equals("S")) {
            registerProduct(scanner);
        } else if (!resp.equals("N")) {
            System.out.println("Informe apenas letras! S para continuar e N para sair.");
        }
    }

    public void allProducts() {
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            System.out.println(product);
            System.out.println();
        }
    }

    public void registerProduct(Scanner scanner) {
        Product product = new Product();
        while (true) {
            System.out.print("Informe o nome do produto: ");
            product.setName(scanner.nextLine());

            System.out.print("Informe a descrição do produto: ");
            product.setDescription(scanner.nextLine());

            System.out.print("Informe o valor do produto: ");
            while (true) {
                try {
                    product.setValue(scanner.nextDouble());
                    scanner.nextLine();
                    break;
                } catch (InputMismatchException ex) {
                    System.out.println("Por favor, insira um valor numérico válido para o produto. EX: 3,5");
                    scanner.nextLine();
                }
            }

            System.out.println("Produto disponível para venda: (s/n)");
            String resp = scanner.nextLine().trim().toUpperCase();
            if (resp.equals("S")) {
                product.setAvailable(true);
            } else if (resp.equals("N")) {
                product.setAvailable(false);
            } else {
                System.out.println("Informe apenas letras! S para continuar e N para sair.");
                continue;
            }

            productRepository.save(product);
            System.out.println("\nProduto salvo com sucesso!");
            productList(scanner);
            break;
        }
    }
}
