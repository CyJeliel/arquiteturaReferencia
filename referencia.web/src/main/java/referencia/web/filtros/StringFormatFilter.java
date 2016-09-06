package referencia.web.filtros;

import static javax.faces.event.PhaseId.ANY_PHASE;
import static org.owasp.html.Sanitizers.FORMATTING;
import static org.owasp.html.Sanitizers.LINKS;

import java.io.IOException;

import javax.faces.component.UIViewRoot;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.owasp.html.PolicyFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringFormatFilter implements PhaseListener {

    private static final long serialVersionUID = 1L;

    @Override
    public void afterPhase(PhaseEvent event) {

        // Nada a fazer

    }

    @Override
    public void beforePhase(PhaseEvent event) {

        UIViewRoot view = event.getFacesContext().getViewRoot();

        if (view != null) {

            String viewState = view.getViewId();

            PolicyFactory policy = FORMATTING.and(LINKS);

            String safeHTML = policy.sanitize(viewState);

            if (!viewState.equals(safeHTML)) {

                try {

                    event.getFacesContext().getExternalContext().redirect("/public/login.jsf");

                } catch (IOException e) {

                    log.error("Erro ao redirecionar", e);

                }
            }
        }

    }

    @Override
    public PhaseId getPhaseId() {

        return ANY_PHASE;
    }

}
