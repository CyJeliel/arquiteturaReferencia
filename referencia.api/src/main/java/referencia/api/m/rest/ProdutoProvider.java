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
import referencia.application.interfaces.IProdutoApplication;
import referencia.domain.entities.Produto;
import referencia.domain.enumeracoes.StatusProdutoEnum;

@Path("/referencia")
@Slf4j
public class ProdutoProvider {

    private IProdutoApplication produtoSC;

    private RespostaHTTP respostaHTTP;
    
    public ProdutoProvider() {
        // Default Constructor
    }

    @Inject
    public ProdutoProvider(IProdutoApplication produtoSC, RespostaHTTP respostaHTTP) {

        this.produtoSC = produtoSC;

        this.respostaHTTP = respostaHTTP;
    }

    /**
     * Lista os produtos cadastrados.
     * 
     * @return a lista dos produtos cadastrados
     */
    @POST
    @GET
    @Path("/v1.0/produtos")
    @Consumes({ WILDCARD })
    @Produces({ APPLICATION_ATOM_XML, APPLICATION_JSON + "; charset=UTF-8" })
    public Response listarProdutos() {

        log.debug("listarProdutos: {}");

        try {

            List<Produto> produtos = produtoSC.listar();

            return respostaHTTP.construirResposta(produtos);

        } catch (Exception e) {

            return respostaHTTP.construirRespostaErro(e);
        }
    }

    /**
     * Busca os dados de um produto pelo seu Id
     * 
     * @return o produto cadastrado
     */
    @POST
    @GET
    @Path("/v1.0/produto/{nId}")
    @Consumes({ WILDCARD })
    @Produces({ APPLICATION_ATOM_XML, APPLICATION_JSON + "; charset=UTF-8" })
    public Response buscarProduto(@PathParam(value = "nId") Integer id) {

        log.debug("buscarProduto: {}", id);

        try {

            Produto produto = produtoSC.buscar(id);

            return respostaHTTP.construirResposta(produto);

        } catch (Exception e) {

            return respostaHTTP.construirRespostaErro(e);
        }
    }

    /**
     * Remove um produto pelo seu Id
     * 
     * @return o status da operação
     */
    @DELETE
    @Path("/v1.0/produto/{nId}")
    @Consumes({ WILDCARD })
    @Produces({ TEXT_PLAIN + "; charset=UTF-8" })
    public Response excluirProduto(@PathParam(value = "nId") Integer id) {

        log.debug("excluirProduto: {}", id);

        try {

            StatusProdutoEnum status = produtoSC.excluir(id);

            return respostaHTTP.construirResposta(status.getFeedback());

        } catch (Exception e) {

            return respostaHTTP.construirRespostaErro(e);
        }
    }

    /**
     * Remove um produto pelo seu Id
     * 
     * @return o status da operação
     */
    @POST
    @Path("/v1.0/produto")
    @Consumes({ APPLICATION_ATOM_XML, APPLICATION_JSON + "; charset=UTF-8" })
    @Produces({ TEXT_PLAIN + "; charset=UTF-8" })
    public Response salvarProduto(Produto produto) {

        log.debug("salvarProduto: {}", produto);

        try {

            StatusProdutoEnum status = produtoSC.salvar(produto);

            return respostaHTTP.construirResposta(status.getFeedback());

        } catch (Exception e) {

            return respostaHTTP.construirRespostaErro(e);
        }
    }
}
