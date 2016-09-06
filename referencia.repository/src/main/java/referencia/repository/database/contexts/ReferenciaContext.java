package referencia.repository.database.contexts;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReferenciaContext {

    @PersistenceContext(unitName = "br.gov.sp.prodesp.namb")
    private EntityManager entityManager;

    public void commit() {

        entityManager.getTransaction().commit();
    }

    public void setEntityManager(EntityManager em) {

        log.debug("entityManager({})", em);

        entityManager = em;
    }

    public EntityManager getEntityManager() {

        log.debug("entityManager({})", entityManager);

        return entityManager;
    }
}