package referencia.domain.entities;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(of = "id")
@XmlRootElement
public class Categoria {

    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private String descricao;

}
