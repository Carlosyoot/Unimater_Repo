package Classes;
import java.util.List;
import java.util.Random;
import Construtores.Carro;
import Construtores.Defeito;
import Construtores.OrdemDeServico;

public class Servicos {

    final String SUCESS_ADD = "Ordem adicionada com sucesso!";
    final String SEE_THE_LIST = "Obeserve abaixo a lista de ordens: ";



    public OrdemDeServico gerarOrdemDeServico(Carro carro, List<Defeito> defeitos) {
        Random random = new Random();
        int defeitoSelecionado = random.nextInt(0, defeitos.size());
        OrdemDeServico novaOrdem = new OrdemDeServico(carro, defeitos.get(defeitoSelecionado));
        return novaOrdem;
    }


    public List<OrdemDeServico> adicionarOrdemLista(List<OrdemDeServico> listaDeOrndes, OrdemDeServico ordem) {
        listaDeOrndes.add(ordem);
        System.out.println(SUCESS_ADD);
        return listaDeOrndes;
    }


    public List<OrdemDeServico> resolverOrdem(List<OrdemDeServico> listaDeServicos, OrdemDeServico ordem){
        listaDeServicos.remove(ordem);
        return listaDeServicos;
    }

}
