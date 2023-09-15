import static spark.Spark.*;

import com.example.Produto;
import com.example.ProdutoDAO;

import java.util.List;

public class Spark {

    public static void main(String[] args) {
        staticFiles.location("/public"); // Pasta onde os arquivos HTML e recursos estáticos estão localizados

        // Inicialize a conexão com o banco de dados
        ProdutoDAO produtoDAO = initDatabaseConnection();

        // Rota para exibir o formulário HTML
        get("/", (req, res) -> {
            List<Produto> produtos = produtoDAO.listarProdutos();
            return new ModelAndView(produtos, "index.html");
        }, new MustacheTemplateEngine());

        // Rota para lidar com o envio do formulário
        post("/inserir", (req, res) -> {
            String nomeProduto = req.queryParams("nomeProduto");
            double precoProduto = Double.parseDouble(req.queryParams("precoProduto"));

            Produto novoProduto = new Produto();
            novoProduto.setNome(nomeProduto);
            novoProduto.setPreco(precoProduto);

            produtoDAO.inserirProduto(novoProduto);

            res.redirect("/");
            return null;
        });

        // Rota para excluir um produto
        get("/excluir/:id", (req, res) -> {
            int idProdutoExcluir = Integer.parseInt(req.params(":id"));
            produtoDAO.excluirProduto(idProdutoExcluir);

            res.redirect("/");
            return null;
        });

        // Rota para atualizar um produto
        post("/atualizar/:id", (req, res) -> {
            int idProdutoAtualizar = Integer.parseInt(req.params(":id"));
            String novoNomeProduto = req.queryParams("novoNomeProduto");
            double novoPrecoProduto = Double.parseDouble(req.queryParams("novoPrecoProduto"));

            Produto produtoAtualizado = new Produto(idProdutoAtualizar, novoNomeProduto, novoPrecoProduto);
            produtoDAO.atualizarProduto(produtoAtualizado);

            res.redirect("/");
            return null;
        });
    }

    private static ProdutoDAO initDatabaseConnection() {
        // Configurar e retornar uma instância de ProdutoDAO com a conexão JDBC
        // Certifique-se de que a conexão JDBC seja configurada corretamente
        // Retorne a instância do ProdutoDAO
    }
}
