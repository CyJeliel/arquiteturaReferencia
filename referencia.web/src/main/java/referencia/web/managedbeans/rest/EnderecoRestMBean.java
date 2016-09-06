package referencia.web.managedbeans.rest;

import static javax.ws.rs.core.HttpHeaders.ACCEPT;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.extern.slf4j.Slf4j;
import prodespcep.dominio.cep.dto.CepProcDTO;
import referencia.application.implementations.excecoes.BCException;
import referencia.application.implementations.transformers.EnderecoTransformer;
import referencia.domain.entities.Endereco;

@Slf4j
@ManagedBean(name = "enderecoRestMBean")
@SessionScoped
public class EnderecoRestMBean extends RestMBean {

    private static final String UTF_8 = "UTF-8";

    private static final String URL_CEP = "prodesp-cep.api/api/cep/consulta/";

    private EnderecoTransformer enderecoTransformer;

    @Inject
    @PostConstruct
    public void inicializar(EnderecoTransformer enderecoTransformer) {

        this.enderecoTransformer = enderecoTransformer;
    }

    public Endereco buscar(String cepDigitado) throws BCException {

        String cep = cepDigitado.replace("-", "");

        Endereco endereco = null;

        if (isNotEmpty(cep) && cep.length() == 8) {

            try {

                List<CepProcDTO> cepsDTO = null;

                URL url = new URL(getServidor() + URL_CEP + cep);

                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                con.setRequestMethod("GET");

                con.setRequestProperty(ACCEPT, APPLICATION_JSON);

                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String output;

                Type type = new TypeToken<List<CepProcDTO>>() {
                }.getType();

                while ((output = br.readLine()) != null) {

                    Gson gson = new Gson();

                    cepsDTO = gson.fromJson(URLDecoder.decode(output, UTF_8), type);
                }

                if (isNotEmpty(cepsDTO) && cepsDTO.size() == 1) {

                    endereco = enderecoTransformer.paraEndereco(cepsDTO.get(0));
                }

            } catch (IOException e) {

                log.error("Erro ao buscar o CEP via REST", e);

                throw new BCException(e);
            }
        }
        
        return endereco;
    }

    @Override
    protected String getServidor() throws IOException {

        Properties properties = propertiesLoaderMBean.loadProperties();

        return properties.getProperty("servidorCEP");
    }
}
