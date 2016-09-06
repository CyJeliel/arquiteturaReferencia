package referencia.api.m.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_ATOM_XML;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static javax.ws.rs.core.MediaType.WILDCARD;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import lombok.extern.slf4j.Slf4j;
import referencia.api.m.rest.response.RespostaHTTP;
import referencia.application.interfaces.ICategoriaApplication;
import referencia.domain.entities.Categoria;
import referencia.domain.entities.Produto;
import referencia.domain.enumeracoes.StatusCategoriaEnum;

@Path("/referencia")
@Slf4j
public class CategoriaProvider {

    private ICategoriaApplication categoriaSC;

    private RespostaHTTP respostaHTTP;
    
    public CategoriaProvider() {
        // Default Constructor
    }

    @Inject
    public CategoriaProvider(ICategoriaApplication categoriaSC, RespostaHTTP respostaHTTP) {

        this.categoriaSC = categoriaSC;

        this.respostaHTTP = respostaHTTP;
    }

    /**
     * Lista as categorias cadastradas.
     * 
     * @return a lista dos categorias cadastrados
     */
    @POST
    @GET
    @Path("/v1.0/categorias")
    @Consumes({ WILDCARD })
    @Produces({ APPLICATION_ATOM_XML, APPLICATION_JSON + "; charset=UTF-8" })
    public Response listarCategorias() {

        log.debug("listarCategorias: {}");

        try {

            List<Categoria> categorias = categoriaSC.listar();

            return respostaHTTP.construirResposta(categorias);

        } catch (Exception e) {

            return respostaHTTP.construirRespostaErro(e);
        }
    }

    /**
     * Busca os dados de uma categoria pelo seu Id
     * 
     * @return a categoria cadastrada
     */
    @POST
    @GET
    @Path("/v1.0/categoria/{nId}")
    @Consumes({ WILDCARD })
    @Produces({ APPLICATION_ATOM_XML, APPLICATION_JSON + "; charset=UTF-8" })
    public Response buscarCategoria(@PathParam(value = "nId") Integer id) {

        log.debug("buscarCategoria: {}", id);

        try {

            Categoria categoria = categoriaSC.buscar(id);

            return respostaHTTP.construirResposta(categoria);

        } catch (Exception e) {

            return respostaHTTP.construirRespostaErro(e);
        }
    }

    /**
     * Busca os dados de uma categoria pelo produto informado na requisicao
     * 
     * @return a categoria cadastrada
     */
    @POST
    @GET
    @Path("/v1.0/categoria/")
    @Consumes({ WILDCARD })
    @Produces({ APPLICATION_ATOM_XML, APPLICATION_JSON + "; charset=UTF-8" })
    public Response buscarCategoria(Produto produto) {

        log.debug("buscarCategoria: {}", produto);

        try {

            Categoria categoria = categoriaSC.buscarPor(produto);

            return respostaHTTP.construirResposta(categoria);

        } catch (Exception e) {

            return respostaHTTP.construirRespostaErro(e);
        }
    }

    /**
     * Remove uma categoria pelo seu Id
     * 
     * @return o status da operação
     */
    @DELETE
    @Path("/v1.0/categoria/{nId}")
    @Consumes({ WILDCARD })
    @Produces({ TEXT_PLAIN + "; charset=UTF-8" })
    public Response excluirCategoria(@PathParam(value = "nId") Integer id) {

        log.debug("excluirCategoria: {}", id);

        try {

            StatusCategoriaEnum status = categoriaSC.excluir(id);

            return respostaHTTP.construirResposta(status.getFeedback());

        } catch (Exception e) {

            return respostaHTTP.construirRespostaErro(e);
        }
    }

    /**
     * Remove uma categoria pelo seu Id
     * 
     * @return o status da operação
     */
    @POST
    @Path("/v1.0/categoria")
    @Consumes({ APPLICATION_ATOM_XML, APPLICATION_JSON + "; charset=UTF-8" })
    @Produces({ TEXT_PLAIN + "; charset=UTF-8" })
    public Response salvarCategoria(Categoria categoria) {

        log.debug("salvarCategoria: {}", categoria);

        try {

            StatusCategoriaEnum status = categoriaSC.salvar(categoria);

            return respostaHTTP.construirResposta(status.getFeedback());

        } catch (Exception e) {

            return respostaHTTP.construirRespostaErro(e);
        }
    }
}
