package uz.nt.uzumproject.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import uz.nt.uzumproject.model.Product;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl {

    private final EntityManager entityManager;

    public Page<Product> universalSearch(Map<String, String> params){
        int page = 0, size = 10;

        if (params.containsKey("page")){
            page = Integer.parseInt(params.get("page"));
        }
        if (params.containsKey("size")){
            size = Math.max(Integer.parseInt(params.get("size")), 1);
        }


        String sqlQuery = "select p from Product p where 1 = 1 ";
        String countQuery = "select count(1) from Product p where 1 = 1 ";

        StringBuilder queryCondition = new StringBuilder();
        generateQueryCondition(queryCondition, params);

        Query query = entityManager.createQuery(sqlQuery + queryCondition);
        Query countQuery1 = entityManager.createQuery(countQuery + queryCondition);


        setParameters(query, params);
        setParameters(countQuery1, params);

        long count = (long) countQuery1.getSingleResult();

        if (count / size <= page){
            if (count % size == 0){
                page = (int) ((count / size) - 1);
            }
            else page = (int) (count / size);
        }
        query.setFirstResult(page * size);

        query.setMaxResults(size);

        List<Product> prod = query.getResultList();
        return new PageImpl<>(prod, PageRequest.of(page, size), count);
    }

    private void generateQueryCondition(StringBuilder queryCondition, Map<String, String> params){
        
        if (params.containsKey("name")){
            queryCondition.append(" AND upper(p.name) like :name ");
        }

        if (params.containsKey("amount")){
            queryCondition.append(" AND p.amount = :amount ");
        }

        if (params.containsKey("price")){
            queryCondition.append(" AND p.price = :price ");
        }

    }

    private void setParameters(Query query, Map<String, String> params){
        if (params.containsKey("name")){
            query.setParameter("name", "%" + params.get("name").toUpperCase() + "%");
        }

        if (params.containsKey("price")){
            query.setParameter("price",  Integer.parseInt(params.get("price")));
        }

        if (params.containsKey("amount")){
            query.setParameter("amount", Integer.parseInt(params.get("amount")));
        }
    }

}
