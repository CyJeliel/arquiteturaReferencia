package referencia.domain.entities;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Data
@XmlRootElement(name = "produto")
@EqualsAndHashCode(of = "id")
public class Produto {

    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private BigDecimal preco;

    @Getter
    @Setter
    private int estoque;

    @Getter
    @Setter
    private String descricao;

    @Getter
    @Setter
    private Categoria categoria;
}
