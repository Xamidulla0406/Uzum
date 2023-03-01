package uz.nt.uzumproject.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.nt.uzumproject.model.Product;

import java.util.List;
import java.util.Map;

@Repository
public class ProductRepositoryImpl {

    @Autowired
    private EntityManager entityManager;

    public List<Product> universalSearch(Map<String, String> params) {
        String sqlQuery = "select p from Product p where 1=1";
        StringBuilder queryCondition = new StringBuilder();

        generateQueryCondition(queryCondition, params);

        Query query = entityManager.createQuery(sqlQuery + queryCondition);

        setParameters(query, params);

        return query.getResultList();
    }

    public void generateQueryCondition(StringBuilder queryCondition, Map<String, String> params) {
        if (params.containsKey("name")) {
            queryCondition.append(" and p.name like :name ");
        }
        if (params.containsKey("price")) {
            queryCondition.append(" and p.price like :price ");
        }
        if (params.containsKey("amount")) {
            queryCondition.append(" and p.amount like :amount ");
        }
    }

    public void setParameters(Query query, Map<String, String> params) {
        if (params.containsKey("name")) {
            query.setParameter("name", "%" + params.get("name") + "%");
        }
        if (params.containsKey("price")) {
            query.setParameter("price", Integer.parseInt(params.get("price")));
        }
        if (params.containsKey("amount")) {
            query.setParameter("amount", Integer.parseInt(params.get("amount")));
        }
    }
}
