package referencia.web.managedbeans;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import referencia.application.implementations.excecoes.BCException;
import referencia.domain.entities.Endereco;
import referencia.web.managedbeans.rest.EnderecoRestMBean;

@ManagedBean(name = "enderecoMBean")
@ViewScoped
@Slf4j
public class EnderecoMBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManagedProperty(value = "#{enderecoRestMBean}")
    @Setter
    private transient EnderecoRestMBean enderecoRestMBean;

    @Getter
    @Setter
    private transient Endereco endereco;

    @Setter
    @Getter
    private String cepDigitado;

    @Getter
    private String feedback;

    public void onInformaCEP() {

        carregarEndereco();
    }

    /**
     * Limpa e esconde o formulário
     */
    public void reset() {

        cepDigitado = null;

        endereco = null;

        feedback = "";
    }
    
    public Endereco carregarEndereco() {

        try {

            endereco = enderecoRestMBean.buscar(cepDigitado);

            feedback = "";

        } catch (BCException e) {

            log.error("Erro ao aplicar endereço", e);

            endereco = null;

            feedback = "CEP não encontrado na base de dados dos Correios";
        }

        return endereco;
    }

    public void carregarComplemento() {

        String complemento = endereco.getComplemento();

        if (complemento != null) {

            int indiceEspaco = complemento.indexOf(" ");

            if (indiceEspaco >= 0) {

                endereco.setNumero(complemento.substring(0, indiceEspaco));

                endereco.setComplemento(complemento.substring(indiceEspaco + 1));

            } else if (isNotEmpty(complemento)) {

                endereco.setNumero(complemento);

                endereco.setComplemento("");

            } else {

                endereco.setNumero("");

                endereco.setComplemento("");
            }

        }
    }

    public void setBairro(String bairro) {

        if (endereco != null) {

            endereco.setBairro(bairro);
        }
    }

    public String getBairro() {

        if (endereco != null) {

            return endereco.getBairro();

        } else {

            return "";
        }
    }

    public void setCidade(String cidade) {

        if (endereco != null) {

            endereco.setCidade(cidade);
        }
    }

    public String getCidade() {

        if (endereco != null) {

            return endereco.getCidade();

        } else {

            return "";
        }
    }

    public void setEstado(String estado) {

        if (endereco != null) {

            endereco.setEstado(estado);
        }
    }

    public String getEstado() {

        if (endereco != null) {

            return endereco.getEstado();

        } else {

            return "";
        }
    }

    public void setUf(String uf) {

        if (endereco != null) {

            endereco.setUf(uf);
        }
    }

    public String getUf() {

        if (endereco != null) {

            return endereco.getUf();

        } else {

            return "";
        }
    }

    public void setDescricao(String descricao) {

        if (endereco != null) {

            endereco.setDescricao(descricao);
        }
    }

    public String getDescricao() {

        if (endereco != null) {

            return endereco.getDescricao();

        } else {

            return "";
        }
    }

    public void setNumero(String numero) {

        if (endereco != null) {

            endereco.setNumero(numero);
        }
    }

    public String getNumero() {

        if (endereco != null) {

            return endereco.getNumero();

        } else {

            return "";
        }
    }

    public void setComplemento(String complemento) {

        if (endereco != null) {

            endereco.setComplemento(complemento);
        }
    }

    public String getComplemento() {

        if (endereco != null) {

            return endereco.getComplemento();

        } else {

            return "";
        }
    }
}
