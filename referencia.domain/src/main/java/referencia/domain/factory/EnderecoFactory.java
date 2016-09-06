package referencia.domain.factory;

import referencia.domain.entities.Endereco;
import referencia.domain.entities.EnderecoPadrao;

public class EnderecoFactory {

    private EnderecoFactory() {

    }

    public static Endereco getEndereco() {

        Endereco endereco = new EnderecoPadrao();

        endereco.setBairro("Chácara Agrindus");

        endereco.setCep(6763040);

        endereco.setCidade("Taboão da Serra");

        endereco.setDescricao("AV Aprígio Bezerra da Silva");

        endereco.setEstado("São Paulo");

        endereco.setUf("SP");

        return endereco;
    }

    public static Endereco getEnderecoComComplemento() {

        Endereco endereco = getEndereco();

        endereco.setNumero("1335");

        endereco.setComplemento("ap 44 Bloco B");

        return endereco;
    }
}
