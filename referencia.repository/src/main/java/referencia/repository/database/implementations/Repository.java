package referencia.repository.database.implementations;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.TypedQuery;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import referencia.domain.interfaces.IRepository;
import referencia.repository.database.contexts.ReferenciaContext;
import referencia.repository.database.interfaces.IDatabaseFactory;
import referencia.repository.mapper.implementations.entities.core.BaseEntity;
import referencia.repository.mapper.interfaces.IMapper;

@Slf4j
public abstract class Repository<T extends BaseEntity<?>, U> implements IRepository<U> {

    private static final long serialVersionUID = 1L;

    private transient ReferenciaContext dataContext;

    private transient Class<T> clazz;

    private transient IMapper<T, U> mapper;

    @Getter
    private transient IDatabaseFactory databaseFactory;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Inject
    protected Repository(IDatabaseFactory databaseFactory, IMapper<T, U> mapper) {

        this.databaseFactory = databaseFactory;

        dataContext = databaseFactory.get();

        this.clazz = (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        this.mapper = mapper;
    }

    @Override
    public void add(U entityDomain) {

        T entity = mapper.domainToEntity(entityDomain);

        EntityManager em = dataContext.getEntityManager();

        em.persist(entity);
    }

    @Override
    public void update(U entityDomain) {

        T entity = mapper.domainToEntity(entityDomain);

        EntityManager em = dataContext.getEntityManager();

        em.merge(entity);
    }

    @Override
    public void delete(U entity) {

        EntityManager em = dataContext.getEntityManager();

        em.remove(entity);
    }

    @Override
    public void delete(Serializable id) {
        
        EntityManager em = dataContext.getEntityManager();

        T t = em.find(clazz, id);

        em.remove(t);
    }

    @Override
    public U getById(long id) {

        EntityManager em = dataContext.getEntityManager();

        return mapper.entityToDomain(em.find(clazz, id));
    }

    @Override
    public U getById(String id) {

        EntityManager em = dataContext.getEntityManager();

        return mapper.entityToDomain(em.find(clazz, id));
    }

    @Override
    public U get(String namedQuery, Map<String, Object> map) {

        return mapper.entityToDomain(construirQuery(namedQuery, map).getSingleResult());

    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<U> getAll() {

        EntityManager em = dataContext.getEntityManager();
        
        return em.createNamedQuery(clazz.getSimpleName() + ".listar").getResultList();
    }

    @Override
    public Collection<U> getMany(String namedQuery, Map<String, Object> map) {

        List<T> list = construirQuery(namedQuery, map).getResultList();

        Collection<U> collection = new ArrayList<>();

        for (T t : list) {

            collection.add(mapper.entityToDomain(t));
        }

        return collection;

    }

    /**
     * Cria um TypedQuery a partir da named associada ao entity, e carrega os
     * parametros do mapa.
     *
     * @param <T>
     *            Type da classe do entity.
     * @param clazz
     *            classe do entity
     * @param namedQuery
     *            Named query para contrução do TypedQuery
     * @param map
     *            Mapa com parametros para Named.
     * @return TypedQuery TypedQuery relacionada a Entity
     */
    protected TypedQuery<T> construirQuery(String namedQuery, Map<String, Object> map) {

        EntityManager em = dataContext.getEntityManager();
        
        TypedQuery<T> typed = em.createNamedQuery(namedQuery, clazz);

        if (typed == null) {

            log.error("__¢ query not found: {}", namedQuery);

            return null;
        }

        Set<Parameter<?>> paramSet = typed.getParameters();

        if (!paramSet.isEmpty() && map == null) {

            log.error("__¢ no room (map) for paramSet.size: {}", paramSet.size());

            return null;
        }

        for (Iterator<Parameter<?>> iter = paramSet.iterator(); iter.hasNext();) {

            construirParametros(map, typed, iter);
        }

        log.debug("__¢ typedQuery: {}", typed);

        return typed;
    }

    private void construirParametros(Map<String, Object> map, TypedQuery<T> typed, Iterator<Parameter<?>> iter) {

        Parameter<?> param = iter.next();

        String paramName = param.getName();

        log.debug("___¢ paramName: {}", paramName);

        if (!map.containsKey(paramName)) {

            log.error("__¢ parameter {} not found in Map.", paramName);

            return;
        }

        Object obj = map.get(paramName);

        if (!param.getParameterType().isAssignableFrom(obj.getClass())) {

            log.error("__¢ paramName '{}' is '{}' but i need '{}'", paramName, obj.getClass().getName(), param.getParameterType().getName());

            return;
        }

        typed.setParameter(paramName, map.get(paramName));
    }

}
