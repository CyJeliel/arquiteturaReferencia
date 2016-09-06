package referencia.api.m.rest.response;

import static javax.ws.rs.core.Response.Status.ACCEPTED;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

import java.io.Serializable;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RespostaHTTP implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String ERRO_DE_PROCESSAMENTO = "Erro de processamento: ";

    private static final String EXCECAO = "Excecao: {}";

    public <T> Response construirResposta(List<T> objetos) {

        GenericEntity<List<T>> entity = new GenericEntity<List<T>>(objetos) {
        };

        return Response.status(ACCEPTED).entity(entity).build();
    }

    public <T> Response construirResposta(T objeto) {

        return Response.status(ACCEPTED).entity(objeto).build();
    }

    public Response construirRespostaErro(Exception e) {

        log.error(EXCECAO, e);

        return Response.status(BAD_REQUEST).entity(ERRO_DE_PROCESSAMENTO + e.getMessage()).build();
    }

}
