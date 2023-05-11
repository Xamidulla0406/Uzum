package uz.nt.uzumproject.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import uz.nt.uzumproject.model.Product;

import java.util.List;
import java.util.Map;

@Repository
public class ProductRepositoryImpl {
    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private EntityManager entityManager;

    public Page<Product> universalSearch(Map<String, String> params){
        int page = 0, size = 10;
        if(params.containsKey("page")){
            page = Integer.parseInt(params.get("page"));
        }
        if (params.containsKey("size")){
            size = Math.max(Integer.parseInt(params.get("size")), 1);
        }
        String sqlQuery = "select p from Product p where 1=1";
        String sqlCountQuery = "select count(1) from Product p where 1=1";

        StringBuilder queryCondition = new StringBuilder();

        generateQueryCondition(params, queryCondition);
        Query query = entityManager.createQuery(sqlQuery + queryCondition);
        Query countQuery = entityManager.createQuery(sqlCountQuery + queryCondition);

        setParams(query, params);
        setParams(countQuery, params);

        long count = (long) countQuery.getSingleResult();

        if(page >= count / size ){
            if (count % size == 0){
                page = (int) count / size - 1;
            }else {
                page = (int) count / size;
            }
        }
        query.setFirstResult(page * size);
        query.setMaxResults(size);

        List<Product> products = query.getResultList();

        return new PageImpl<>(products, PageRequest.of(page, size), count);
    }
    public void generateQueryCondition(Map<String, String> params, StringBuilder queryCondition){
        if (params.containsKey("name")){
            queryCondition.append(" AND p.name like :name");
        }
        if (params.containsKey("price")){
            queryCondition.append(" AND p.price = :price");
        }
        if (params.containsKey("price")){
            queryCondition.append(" AND p.price = :price");
        }
    }
    public void setParams(Query query, Map<String, String> params){
        if (params.containsKey("name")){
            query.setParameter("name","%" + params.get("name") + "%");
        }
        if (params.containsKey("price")){
            query.setParameter("price", Integer.parseInt(params.get("price")));
        }
        if (params.containsKey("amount")){
            query.setParameter("amount", Integer.parseInt(params.get("amount")));
        }
    }
}
