package referencia.web.managedbeans.rest;

import static javax.ws.rs.core.HttpHeaders.ACCEPT;
import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static referencia.domain.enumeracoes.StatusClienteEnum.ERRO;
import static referencia.domain.enumeracoes.StatusClienteEnum.ERRO_EXCLUSAO;
import static referencia.domain.enumeracoes.StatusClienteEnum.EXCLUIDO;
import static referencia.domain.enumeracoes.StatusClienteEnum.INVALIDO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.extern.slf4j.Slf4j;
import referencia.application.implementations.excecoes.BCException;
import referencia.domain.entities.Cliente;
import referencia.domain.enumeracoes.StatusClienteEnum;

@Slf4j
@ManagedBean(name = "clienteRestMBean")
@SessionScoped
public class ClienteRestMBean extends RestMBean {

    private static final String UTF_8 = "UTF-8";

    private static final String URL_CLIENTE = ".api/api/referencia/v1.0/cliente/";

    public List<Cliente> listarCliente() throws BCException {

        List<Cliente> clientes = null;

        try {

            URL url = new URL(getServidor() + ".api/api/referencia/v1.0/clientes/");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");

            con.setRequestProperty(ACCEPT, APPLICATION_JSON);

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String output;

            Type type = new TypeToken<List<Cliente>>() {
            }.getType();

            while ((output = br.readLine()) != null) {

                Gson gson = new Gson();

                clientes = gson.fromJson(URLDecoder.decode(output, UTF_8), type);
            }

        } catch (IOException e) {

            log.error("Erro ao buscar os clientes via REST", e);

            clientes = new ArrayList<>();
        }

        return clientes;
    }

    public StatusClienteEnum salvar(Cliente cliente) {

        StatusClienteEnum status = null;

        if (cliente.getEndereco() == null) {

            return INVALIDO;
        }

        cliente.atualizaEndereco();

        try {

            URL url = new URL(getServidor() + URL_CLIENTE);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");

            con.setRequestProperty(ACCEPT, TEXT_PLAIN);

            con.setRequestProperty(CONTENT_TYPE, APPLICATION_JSON);

            con.setDoOutput(true);

            Gson gson = new Gson();

            String json = gson.toJson(cliente);

            con.getOutputStream().write(json.getBytes());

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String statusOperacao;

            while ((statusOperacao = br.readLine()) != null) {

                status = StatusClienteEnum.buscar(statusOperacao);
            }

        } catch (IOException e) {

            log.error("Erro ao salvar a cliente via REST", e);

            status = ERRO;
        }

        return status;
    }

    public StatusClienteEnum excluir(Integer id) {

        StatusClienteEnum status = EXCLUIDO;

        try {

            URL url = new URL(getServidor() + URL_CLIENTE + id);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("DELETE");

            con.setRequestProperty(ACCEPT, TEXT_PLAIN + "; charset=UTF-8");

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String statusOperacao;

            while ((statusOperacao = br.readLine()) != null) {

                status = StatusClienteEnum.buscar(statusOperacao);
            }

        } catch (IOException e) {

            log.error("Erro ao buscar as clientes via REST", e);

            status = ERRO_EXCLUSAO;
        }

        return status;
    }

    public Cliente buscar(Integer id) {

        Cliente cliente = null;

        try {

            URL url = new URL(getServidor() + URL_CLIENTE + id);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");

            con.setRequestProperty(ACCEPT, APPLICATION_JSON);

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String output;

            Type type = new TypeToken<Cliente>() {
            }.getType();

            while ((output = br.readLine()) != null) {

                Gson gson = new Gson();

                cliente = gson.fromJson(URLDecoder.decode(output, UTF_8), type);
            }

        } catch (IOException e) {

            log.error("Erro ao buscar as clientes via REST", e);

            cliente = new Cliente();
        }

        return cliente;
    }

}
