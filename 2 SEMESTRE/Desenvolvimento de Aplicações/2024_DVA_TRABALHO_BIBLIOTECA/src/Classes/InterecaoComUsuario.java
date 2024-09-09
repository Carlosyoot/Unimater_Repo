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
            3. Adicionar novo membro à biblioteca
            4. Verificar lista de membros
            5. Veriricar livros por autor
            6. Verificar Livros pedentes de devolução
            7. Realizar Emprestimo (Realiza empréstimos de Livros)
            8. Realizar Devolução (Realiza a devolução de Livros)
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
        String disponivel = "Disponível";

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
            List<Livro> livrosDoAutor = autorSelecioando.getLivros(); // Supondo que você tenha um método getLivros() na classe Autor
            if (livrosDoAutor.isEmpty()) {
                System.out.println("Este autor não possui livros cadastrados.");
                return;
            }

            System.out.println("Segue abaixo os livros do autor: "+autorSelecioando.getNome());
            for (int i = 0; i < livrosDoAutor.size(); i++) {
                Livro livro = livrosDoAutor.get(i);
                System.out.println((i + 1) + ". " + livro.toString() +"\n"); // Supondo que você tenha um método getTitulo() na classe Livro
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

            System.out.printf("Membro %s, adiccionando aos membros da biblioteca com sucesso!", nomeEstudante);

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

            System.out.printf("Membro %s, adicionado aos membros da bilbioteca com sucesso!", nomeProfessor);

            listaDeMembros.add(novoProfessor);
        }else {
            System.out.println("Opção inválida tente novamente!");
        }
        return listaDeMembros;
    }

    public void printarListaDeMembros(List<Membro> listaDeMembros){
        for (Membro membro : listaDeMembros){
            System.out.println(membro.toString());
        }
    }

    public void emprestarLivros(List<Autor> listaDeAutores, List<Membro> listaDeMembros){
        int membroEscolhido = 0;
        int livroEscolhido = 0;

        if (listaDeMembros.isEmpty() ){
            System.out.println("Adicione um membro à biblioteca primeiro!");
            return;
        }

        if (listaDeAutores.isEmpty()){
            System.out.println("Adicione um autor e um livro à biblioteca primeiro!");
            return;

        }
        System.out.println("Informe a qual dos mesmbros você deseja empretar: ");
        for (int i = 0; i < listaDeMembros.size(); i++) {
            System.out.println((i + 1)+". "+listaDeMembros.get(i).getNome());

        }
        membroEscolhido = leitor.nextInt()-1;

        Membro membro = listaDeMembros.get(membroEscolhido);

        /////////////////////////////////////////////////////////////
        System.out.println("Informe o autor do livro que deseja emprestar: ");
        for (int i = 0; i < listaDeAutores.size(); i++) {
            System.out.println((i + 1) + ". " + listaDeAutores.get(i).getNome());
        }
        int autorEscolhido = leitor.nextInt() - 1; // Ajustar o índice para zero-based
        leitor.nextLine(); // Consumir o restante da linha

        if (autorEscolhido < 0 || autorEscolhido >= listaDeAutores.size()) {
            System.out.println("Autor inválido!");
            return;
        }

        Autor autor = listaDeAutores.get(autorEscolhido);

        List<Livro> livrosDoAutor = autor.getLivros(); // Supondo que você tenha um método getLivros() na classe Autor
        if (livrosDoAutor.isEmpty()) {
            System.out.println("Este autor não possui livros cadastrados.");
            return;
        }

        System.out.println("Informe o livro que deseja emprestar: ");
        for (int i = 0; i < livrosDoAutor.size(); i++) {
            Livro livro = livrosDoAutor.get(i);
            System.out.println((i + 1) + ". " + livro.toString()); // Supondo que você tenha um método getTitulo() na classe Livro
        }
        livroEscolhido = leitor.nextInt() - 1; // Ajustar o índice para zero-based
        leitor.nextLine(); // Consumir o restante da linha

        if (livroEscolhido < 0 || livroEscolhido >= livrosDoAutor.size()) {
            System.out.println("Livro inválido!");
            return;
        }

        Livro livro = livrosDoAutor.get(livroEscolhido);

        if (livro.verificarDisponibilidade().equals("Indisponível")){
            System.out.println("Livro indisponível para empréstimo!");
        }else {
            membro.adicionarLivro(livro); // Supondo que você tenha um método adicionarLivro(Livro livro) na classe Membro
            livro.emprestarLivro();
            System.out.println("Livro emprestado com sucesso!");
        }

        // Adicionar o livro à lista de empréstimos do membro

    }
}
