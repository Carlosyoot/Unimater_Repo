package Classes;

import Construtores.*;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class InterecaoComUsuario {

    private final Scanner leitor;
    private final String OPCOES = """
            
            1. Criar Autor (Adiciona novo autor ao catálogo da biblioteca)
            2. Criar Livro (Vincula livro lançamento ao autor)
            3. Realizar Emprestimo (Realiza empréstimos de Livros)
            4. Realizar Devolução (Realiza a devolução de Livros)
            5. Verificar Livros pedentes de devolução
            6. Verificar Livros disponíveis para empréstimo
            7. Adicionar novo membro à biblioteca
            0. Sair
            
            """;

    public InterecaoComUsuario(Scanner leitor) {

        this.leitor = leitor;
    }

    Serviços adicionarServiços = new Serviços();

    public void opcoesDeEscolha() {
        System.out.println(OPCOES);
    }


    private Autor criarNovoAutor() {
        String nome = "";
        String nacionalidade = "";
        int anoNascimento = 0;
        leitor.nextLine();

        System.out.println("Informe o nome do autor: ");
        nome = leitor.nextLine();

        System.out.println("Informe a nacionalidade: ");
        nacionalidade = leitor.nextLine();

        System.out.println("Informe o ano de nascimento: ");
        anoNascimento = leitor.nextInt();

        return new Autor(nome, nacionalidade, anoNascimento);
    }

    public List<Autor> criarNovoAutor(List<Autor> listaDeAutores) {
        Autor autor = this.criarNovoAutor();
        adicionarServiços.adicionarAutorLista(listaDeAutores, autor);
        System.out.println("Autor adicionado à lista com sucesso!");
        return listaDeAutores;
    }

    public void criarNovoLivro(List<Autor> listaDeAutores) {
        Random gerarIsbn = new Random();

        String tituloDoLivro = "";
        int autorEscolhido = 0;
        String nomeDoAutor = "";
        int anoDeLancameto = 0;
        int isbn = 0;
        boolean disponivel = true;

        if (listaDeAutores.isEmpty()) {
            System.out.println("Não há autores disponíveis na biblioteca no momento. Crie um novo autor para continuar.");
            return;
        } else {
            System.out.println("Autores disponíveis: ");

            for (int i = 0; i < listaDeAutores.size(); i++) {
                System.out.println((i + 1) + ". " + listaDeAutores.get(i).getNome());
            }
        }

        System.out.println("Digite o autor ao qual você deseja adicionar ao livro");
        autorEscolhido = leitor.nextInt();
        Autor autorSelecionado = listaDeAutores.get(autorEscolhido - 1);
        nomeDoAutor = autorSelecionado.getNome();
        leitor.nextLine();

        System.out.println("Informe o Título do Livro: ");
        tituloDoLivro = leitor.nextLine();

        System.out.println("Informe o ano de lançamento do livro: ");
        anoDeLancameto = leitor.nextInt();

        isbn = gerarIsbn.nextInt(100000);


         Livro livro = new Livro(nomeDoAutor, tituloDoLivro, anoDeLancameto, disponivel, isbn);
         autorSelecionado.adicionarLivro(livro);

        System.out.println("Livro criado e adicionado com sucesso!");
    };



    public void verificarLivrosPorAutorOuTodosOsLivros(List<Autor> listaDeAutores){
        for (int i = 0; i < listaDeAutores.size(); i++) {
            System.out.println((i + 1) + ". " + listaDeAutores.get(i).getNome());
        }
        System.out.println("Digite o autor ao qual você deseja visuliazar os livros lançados: ");

        int escolha = leitor.nextInt();

        if (escolha < 1 || escolha > listaDeAutores.size()) {
            System.out.println("Opção informada inválida tente novamente!");
        } else {
            Autor autorSelecioando = listaDeAutores.get(escolha -1);
            System.out.println("Segue abaixo os livros do autor: "+autorSelecioando.getNome());
            for (Livro livro: autorSelecioando.getLivros()){
                System.out.println(livro.toString());
            }
        }
    }

    public List<Membro> criarNovoMembro(List<Membro> listaDeMembros){
        Random geradorDeId = new Random();
        int tipoMembro = 0;
        System.out.println("""
                Vamos cadastrar um novo membro, informe uma das opções abaixo: 
                1. Estudante
                2. Professor
                """);
        tipoMembro = leitor.nextInt();

        leitor.nextLine();

        if (tipoMembro == 1){
            String nomeEstudante = "";
            int idEstudante = 0;
            String curos = "";

            System.out.println("Informe o nome do estudante: ");
            nomeEstudante = leitor.nextLine();

            idEstudante = geradorDeId.nextInt(10000);

            System.out.println("Informe o curso do mesmo: ");
            curos = leitor.nextLine();

            Estudante estudante = new Estudante(nomeEstudante, idEstudante, curos);

            listaDeMembros.add(estudante);
        } else if (tipoMembro == 2) {
            String nomeProfessor = "";
            int idProfessor = 0;
            String departamento = "";
            String materia = "";

            System.out.println("Informe o nome do professor: ");
            nomeProfessor = leitor.nextLine();

            idProfessor = geradorDeId.nextInt(10000);

            System.out.println("Informe o departamento do mesmo: ");
            departamento = leitor.nextLine();

            System.out.println("Informe a matéria da sua aula: ");
            materia = leitor.nextLine();

            Professor novoProfessor = new Professor(nomeProfessor, idProfessor, departamento, materia);

            listaDeMembros.add(novoProfessor);
        }else {
            System.out.println("Opção inválida tente novamente!");
        }
        return listaDeMembros;
    }
}
