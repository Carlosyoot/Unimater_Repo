package dva;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class InventarioController implements Initializable {

    @FXML
    private Label labelCodigo;
    @FXML
    private Button btnEnviar;
    @FXML
    private ListView<String> listView;
    @FXML
    private TextField txtLabel;

    private List<Produto> listaAtualizada = new ArrayList<>();
    private ObservableList<String> listaObservavel = FXCollections.observableArrayList();
    private List<Produto> lista1 = new ArrayList<>();
    private List<Produto> lista2;
    private List<Produto> listaEstoqueInicial;
    private int contadorFinalizar = 0;
    private int contadorFechamento = 5;
    private Timeline timeline;
    private boolean atualizarLista1 = true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtLabel.setOnAction(event -> onKeyPress());
        listView.setItems(listaObservavel);
        btnEnviar.setOnAction(event -> finalizar());
        listaEstoqueInicial = new ArrayList<>(Estoque.estoqueAtual());
    }

    private void onKeyPress() {
        String textoDigitado = txtLabel.getText();
        Produto produto = buscarProdutoPorCodigo(textoDigitado, Estoque.estoqueAtual());

        if (produto == null) {
            listaObservavel.add("Produto inexistente");
        } else {
            atualizarOuAdicionarProduto(produto);
            if (atualizarLista1) {
                adicionarProdutoNaLista(produto, lista1);
            }
            atualizarListView();
            txtLabel.clear();
        }
    }

    private void adicionarProdutoNaLista(Produto novoProduto, List<Produto> lista) {
        boolean encontrado = false;
        for (Produto produto : lista) {
            if (produto.getCodBarras().equals(novoProduto.getCodBarras())) {
                produto.setSaldo(produto.getSaldo() + 1);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            Produto produtoParaAdicionar = new Produto(novoProduto.getCodBarras(), novoProduto.getDescricao(), 1);
            lista.add(produtoParaAdicionar);
        }
    }

    private void atualizarOuAdicionarProduto(Produto novoProduto) {
        boolean encontrado = false;
        for (Produto produto : listaAtualizada) {
            if (produto.getCodBarras().equals(novoProduto.getCodBarras())) {
                produto.setSaldo(produto.getSaldo() + 1);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            novoProduto.setSaldo(1);
            listaAtualizada.add(novoProduto);
        }
    }

    private void atualizarListView() {
        listaObservavel.clear();
        for (Produto produto : listaAtualizada) {
            listaObservavel.add(produto.getDescricao() + " - " + produto.getSaldo());
        }
    }

    private Produto buscarProdutoPorCodigo(String codigo, List<Produto> produtos) {
        for (Produto produto : produtos) {
            if (produto.getCodBarras().equals(codigo)) {
                return produto;
            }
        }
        return null;
    }

    private void finalizar() {
        contadorFinalizar++;

        if (contadorFinalizar == 1) {
            lista2 = new ArrayList<>(listaAtualizada);
            atualizarLista1 = false;
            listaAtualizada.clear();

            if (!listasIguais(lista1, listaEstoqueInicial)) {
                listaObservavel.clear();
                listaObservavel.add("Listas diferentes, continue usando a lista 2");
            } else {
                iniciarContadorFechamento();
            }
        } else if (contadorFinalizar == 2) {
            List<Produto> lista3 = new ArrayList<>(listaAtualizada);
            listaAtualizada.clear();

            if (!listasIguais(lista2, lista3) && !listasIguais(lista1, lista3)) {
                listaObservavel.clear();
                listaObservavel.add("Listas diferentes, lista 3 será a nova principal");
                lista1 = new ArrayList<>(lista3);
            } else {
                iniciarContadorFechamento();
            }
        } else if (contadorFinalizar == 3) {
            List<Produto> lista3 = new ArrayList<>(listaAtualizada);
            listaAtualizada.clear();

            if (listasIguais(lista1, lista3) || listasIguais(lista2, lista3)) {
                listaObservavel.clear();
                listaObservavel.add("Listas iguais, fechando...");
            } else {
                listaObservavel.clear();
                listaObservavel.add("Listas diferentes, fechando...");
            }
            iniciarContadorFechamento();
        }
    }

    private boolean listasIguais(List<Produto> listaA, List<Produto> listaB) {
        if (listaA.size() != listaB.size()) return false;
        for (Produto produtoA : listaA) {
            boolean encontrado = false;
            for (Produto produtoB : listaB) {
                if (produtoA.getCodBarras().equals(produtoB.getCodBarras()) &&
                        produtoA.getSaldo() == produtoB.getSaldo()) {
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) return false;
        }
        return true;
    }

    private void iniciarContadorFechamento() {
        contadorFechamento = 5;
        listaObservavel.clear();
        listaObservavel.add("Fechando em " + contadorFechamento + " segundos...");

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            contadorFechamento--;
            listaObservavel.set(0, "Fechando em " + contadorFechamento + " segundos...");

            if (contadorFechamento <= 0) {
                timeline.stop();
                fecharPrograma();
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void fecharPrograma() {
        ((Stage) btnEnviar.getScene().getWindow()).close();
    }
}
