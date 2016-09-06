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
import referencia.application.interfaces.IClienteApplication;
import referencia.domain.entities.Cliente;
import referencia.domain.enumeracoes.StatusClienteEnum;

@Path("/referencia")
@Slf4j
public class ClienteProvider {

    private IClienteApplication clienteSC;

    private RespostaHTTP respostaHTTP;

    
    public ClienteProvider() {
        // Default Constructor
    }
    
    @Inject
    public ClienteProvider(IClienteApplication clienteSC, RespostaHTTP respostaHTTP) {

        this.clienteSC = clienteSC;

        this.respostaHTTP = respostaHTTP;
    }

    /**
     * Lista os clientes cadastrados.
     * 
     * @return a lista dos clientes cadastrados
     */
    @POST
    @GET
    @Path("/v1.0/clientes")
    @Consumes({ WILDCARD })
    @Produces({ APPLICATION_ATOM_XML, APPLICATION_JSON + "; charset=UTF-8" })
    public Response listarClientes() {

        log.debug("listarClientes: {}");

        try {

            List<Cliente> clientes = clienteSC.listar();

            return respostaHTTP.construirResposta(clientes);

        } catch (Exception e) {

            return respostaHTTP.construirRespostaErro(e);
        }
    }

    /**
     * Busca os dados de um cliente pelo seu Id
     * 
     * @return a cliente cadastrada
     */
    @POST
    @GET
    @Path("/v1.0/cliente/{nId}")
    @Consumes({ WILDCARD })
    @Produces({ APPLICATION_ATOM_XML, APPLICATION_JSON + "; charset=UTF-8" })
    public Response buscarCliente(@PathParam(value = "nId") Integer id) {

        log.debug("buscarCliente: {}", id);

        try {

            Cliente cliente = clienteSC.buscar(id);

            return respostaHTTP.construirResposta(cliente);

        } catch (Exception e) {

            return respostaHTTP.construirRespostaErro(e);
        }
    }

    /**
     * Remove um cliente pelo seu Id
     * 
     * @return o status da operação
     */
    @DELETE
    @Path("/v1.0/cliente/{nId}")
    @Consumes({ WILDCARD })
    @Produces({ TEXT_PLAIN + "; charset=UTF-8" })
    public Response excluirCliente(@PathParam(value = "nId") Integer id) {

        log.debug("excluirCliente: {}", id);

        try {

            StatusClienteEnum status = clienteSC.excluir(id);

            return respostaHTTP.construirResposta(status.getFeedback());

        } catch (Exception e) {

            return respostaHTTP.construirRespostaErro(e);
        }
    }

    /**
     * Remove um cliente pelo seu Id
     * 
     * @return o status da operação
     */
    @POST
    @Path("/v1.0/cliente")
    @Consumes({ APPLICATION_ATOM_XML, APPLICATION_JSON + "; charset=UTF-8" })
    @Produces({ TEXT_PLAIN + "; charset=UTF-8" })
    public Response salvarCliente(Cliente cliente) {

        log.debug("salvarCliente: {}", cliente);

        try {

            StatusClienteEnum status = clienteSC.salvar(cliente);

            return respostaHTTP.construirResposta(status.getFeedback());

        } catch (Exception e) {

            return respostaHTTP.construirRespostaErro(e);
        }
    }
}
