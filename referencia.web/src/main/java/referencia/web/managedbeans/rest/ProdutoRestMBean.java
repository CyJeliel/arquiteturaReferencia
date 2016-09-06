package referencia.web.managedbeans.rest;

import static javax.ws.rs.core.HttpHeaders.ACCEPT;
import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static referencia.domain.enumeracoes.StatusProdutoEnum.ERRO;
import static referencia.domain.enumeracoes.StatusProdutoEnum.ERRO_EXCLUSAO;

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
import referencia.domain.entities.Produto;
import referencia.domain.enumeracoes.StatusProdutoEnum;

@Slf4j
@ManagedBean(name = "produtoRestMBean")
@SessionScoped
public class ProdutoRestMBean extends RestMBean {

    private static final String ERRO_BUSCA = "Erro ao buscar os produtos via REST";

    private static final String URL_PRODUTO = ".api/api/referencia/v1.0/produto/";

    public List<Produto> listarProduto() throws BCException {

        List<Produto> produtos = null;

        try {

            URL url = new URL(getServidor() + ".api/api/referencia/v1.0/produtos/");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");

            con.setRequestProperty(ACCEPT, APPLICATION_JSON);

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String output;

            Type type = new TypeToken<List<Produto>>() {
            }.getType();

            while ((output = br.readLine()) != null) {

                Gson gson = new Gson();

                produtos = gson.fromJson(URLDecoder.decode(output, "UTF-8"), type);
            }

        } catch (IOException e) {

            log.error(ERRO_BUSCA, e);

            produtos = new ArrayList<>();
        }

        return produtos;
    }

    public StatusProdutoEnum salvar(Produto produto) {

        StatusProdutoEnum status = null;

        try {

            URL url = new URL(getServidor() + URL_PRODUTO);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");

            con.setRequestProperty(ACCEPT, TEXT_PLAIN);

            con.setRequestProperty(CONTENT_TYPE, APPLICATION_JSON);

            con.setDoOutput(true);

            Gson gson = new Gson();

            String json = gson.toJson(produto);

            con.getOutputStream().write(json.getBytes());

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String statusOperacao;

            while ((statusOperacao = br.readLine()) != null) {

                status = StatusProdutoEnum.buscar(statusOperacao);
            }

        } catch (IOException e) {

            log.error("Erro ao salvar o produto via REST", e);

            status = ERRO;
        }

        return status;
    }

    public StatusProdutoEnum excluir(Integer id) {

        StatusProdutoEnum status = null;

        try {

            URL url = new URL(getServidor() + URL_PRODUTO + id);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("DELETE");

            con.setRequestProperty(ACCEPT, TEXT_PLAIN + "; charset=UTF-8");

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String statusOperacao;

            while ((statusOperacao = br.readLine()) != null) {

                status = StatusProdutoEnum.buscar(statusOperacao);
            }

        } catch (IOException e) {

            log.error(ERRO_BUSCA, e);

            status = ERRO_EXCLUSAO;
        }

        return status;
    }

    public Produto buscar(Integer id) throws BCException {

        Produto produto = null;

        try {

            URL url = new URL(getServidor() + URL_PRODUTO + id);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");

            con.setRequestProperty(ACCEPT, APPLICATION_JSON);

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String output;

            Type type = new TypeToken<Produto>() {
            }.getType();

            while ((output = br.readLine()) != null) {

                Gson gson = new Gson();

                produto = gson.fromJson(URLDecoder.decode(output, "UTF-8"), type);
            }

        } catch (IOException e) {

            log.error(ERRO_BUSCA, e);

            produto = new Produto();
        }

        return produto;
    }
}
