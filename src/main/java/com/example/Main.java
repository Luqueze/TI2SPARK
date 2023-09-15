package com.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Connection conexao = DriverManager.getConnection("jdbc:postgresql://localhost/ti2", "postgres", "8815");
            ProdutoDAO produtoDAO = new ProdutoDAO(conexao);

            Scanner scanner = new Scanner(System.in);
            int opcao;

            do {
                System.out.println("Menu:");
                System.out.println("1. Listar Produtos");
                System.out.println("2. Inserir Produto");
                System.out.println("3. Excluir Produto");
                System.out.println("4. Atualizar Produto");
                System.out.println("5. Sair");
                System.out.print("Escolha uma opção: ");
                opcao = scanner.nextInt();
                scanner.nextLine(); // Limpar o buffer

                switch (opcao) {
                    case 1:
                        List<Produto> produtos = produtoDAO.listarProdutos();
                        for (Produto produto : produtos) {
                            System.out.println(produto.getId() + " - " + produto.getNome() + " - " + produto.getPreco());
                        }
                        break;
                    case 2:
                        System.out.print("Nome do Produto: ");
                        String nomeProduto = scanner.nextLine();
                        System.out.print("Preço do Produto: ");
                        double precoProduto = scanner.nextDouble();
                        
                        Produto novoProduto = new Produto();
                        novoProduto.setNome(nomeProduto);
                        novoProduto.setPreco(precoProduto);
                        
                        produtoDAO.inserirProduto(novoProduto);
                        System.out.println("Produto inserido com sucesso!");
                        break;
                    case 3:
                        System.out.print("ID do Produto a ser excluído: ");
                        int idProdutoExcluir = scanner.nextInt();
                        
                        produtoDAO.excluirProduto(idProdutoExcluir);
                        System.out.println("Produto excluído com sucesso!");
                        break;
                    case 4:
                        System.out.print("ID do Produto a ser atualizado: ");
                        int idProdutoAtualizar = scanner.nextInt();
                        scanner.nextLine(); // Limpar o buffer
                        
                        System.out.print("Novo nome do Produto: ");
                        String novoNomeProduto = scanner.nextLine();
                        System.out.print("Novo preço do Produto: ");
                        double novoPrecoProduto = scanner.nextDouble();
                        
                        Produto produtoAtualizado = new Produto(idProdutoAtualizar, novoNomeProduto, novoPrecoProduto);
                        produtoDAO.atualizarProduto(produtoAtualizado);
                        System.out.println("Produto atualizado com sucesso!");
                        break;
                    case 5:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida. Escolha novamente.");
                        break;
                }

            } while (opcao != 5);

            conexao.close();
            scanner.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao conectar ao banco de dados.");
        }
    }
}
