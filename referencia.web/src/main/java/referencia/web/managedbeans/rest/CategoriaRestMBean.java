package referencia.web.managedbeans.rest;

import static javax.ws.rs.core.HttpHeaders.ACCEPT;
import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static referencia.domain.enumeracoes.StatusCategoriaEnum.ERRO;
import static referencia.domain.enumeracoes.StatusCategoriaEnum.ERRO_EXCLUSAO;
import static referencia.domain.enumeracoes.StatusCategoriaEnum.EXCLUIDA;

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
import referencia.domain.entities.Categoria;
import referencia.domain.entities.Produto;
import referencia.domain.enumeracoes.StatusCategoriaEnum;

@Slf4j
@ManagedBean(name = "categoriaRestMBean")
@SessionScoped
public class CategoriaRestMBean extends RestMBean {

    private static final String UTF_8 = "UTF-8";

    private static final String URL_CATEGORIA = ".api/api/referencia/v1.0/categoria/";

    public List<Categoria> listarCategoria() throws BCException {

        List<Categoria> categorias = null;

        try {

            URL url = new URL(getServidor() + ".api/api/referencia/v1.0/categorias/");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");

            con.setRequestProperty(ACCEPT, APPLICATION_JSON);

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String output;

            Type type = new TypeToken<List<Categoria>>() {
            }.getType();

            while ((output = br.readLine()) != null) {

                Gson gson = new Gson();

                categorias = gson.fromJson(URLDecoder.decode(output, UTF_8), type);
            }

        } catch (IOException e) {

            log.error("Erro ao buscar os categorias via REST", e);

            categorias = new ArrayList<>();
        }

        return categorias;
    }

    public Categoria buscarPor(Produto produto) throws BCException {

        Categoria categoria = null;

        try {

            URL url = new URL(getServidor() + URL_CATEGORIA);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");

            con.setRequestProperty(ACCEPT, APPLICATION_JSON);

            con.setDoOutput(true);

            Gson gson = new Gson();

            String json = gson.toJson(produto);

            con.getOutputStream().write(json.getBytes());

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String output;

            Type type = new TypeToken<Categoria>() {
            }.getType();

            while ((output = br.readLine()) != null) {

                categoria = gson.fromJson(URLDecoder.decode(output, UTF_8), type);
            }

        } catch (IOException e) {

            log.error("Erro ao buscar a categoria via REST", e);

            categoria = new Categoria();
        }

        return categoria;
    }

    public StatusCategoriaEnum salvar(Categoria categoria) {

        StatusCategoriaEnum status = null;

        try {

            URL url = new URL(getServidor() + URL_CATEGORIA);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");

            con.setRequestProperty(ACCEPT, TEXT_PLAIN);

            con.setRequestProperty(CONTENT_TYPE, APPLICATION_JSON);

            con.setDoOutput(true);

            Gson gson = new Gson();

            String json = gson.toJson(categoria);

            con.getOutputStream().write(json.getBytes());

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String statusOperacao;

            while ((statusOperacao = br.readLine()) != null) {

                status = StatusCategoriaEnum.buscar(statusOperacao);
            }

        } catch (IOException e) {

            log.error("Erro ao salvar a categoria via REST", e);

            status = ERRO;
        }

        return status;
    }

    public StatusCategoriaEnum excluir(Integer id) {

        StatusCategoriaEnum status = EXCLUIDA;

        try {

            URL url = new URL(getServidor() + URL_CATEGORIA + id);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("DELETE");

            con.setRequestProperty(ACCEPT, TEXT_PLAIN + "; charset=UTF-8");

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String statusOperacao;

            while ((statusOperacao = br.readLine()) != null) {

                status = StatusCategoriaEnum.buscar(statusOperacao);
            }

        } catch (IOException e) {

            log.error("Erro ao buscar as categorias via REST", e);

            status = ERRO_EXCLUSAO;
        }

        return status;
    }

    public Categoria buscar(Integer id) {

        Categoria categoria = null;

        try {

            URL url = new URL(getServidor() + URL_CATEGORIA + id);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");

            con.setRequestProperty(ACCEPT, APPLICATION_JSON);

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String output;

            Type type = new TypeToken<Categoria>() {
            }.getType();

            while ((output = br.readLine()) != null) {

                Gson gson = new Gson();

                categoria = gson.fromJson(URLDecoder.decode(output, UTF_8), type);
            }

        } catch (IOException e) {

            log.error("Erro ao buscar as categorias via REST", e);

            categoria = new Categoria();
        }

        return categoria;
    }

}
