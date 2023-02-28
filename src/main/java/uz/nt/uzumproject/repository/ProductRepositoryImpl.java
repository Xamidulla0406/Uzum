package uz.nt.uzumproject.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.nt.uzumproject.model.Product;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl {
    private final EntityManager entityManager;
    public List<Product> universalSearch(Map<String,String> params){

        String sqlQuery = "select p from Product p where 1=1 ";
        StringBuilder queryCondition = new StringBuilder();
        generateQueryCondition(queryCondition , params);

        Query query = entityManager.createQuery(sqlQuery + queryCondition);
        setParam(query,params);

        return query.getResultList();
    }

    private void generateQueryCondition(StringBuilder queryCondition , Map<String , String> params){
        if(params.containsKey("name")){
            queryCondition.append(" AND p.name like :name ");
        }
        if(params.containsKey("amount")){
            queryCondition.append(" AND p.amount = :amount ");
        }
        if(params.containsKey("description")){
            queryCondition.append(" AND p.description like :description ");
        }
        if(params.containsKey("price")){
            queryCondition.append(" AND p.price = :name ");
        }
    }
    private void setParam(Query query , Map<String , String> params){
        if(params.containsKey("name")){
            query.setParameter("name","%"+params.get("name")+"%");
        }
        if(params.containsKey("amount")){
            query.setParameter("amount",Integer.parseInt(params.get("amount")));
        }
        if(params.containsKey("description")){
            query.setParameter("description","%"+params.get("description")+"%");
        }
        if(params.containsKey("price")){
            query.setParameter("price",Integer.parseInt(params.get("price")));
        }
    }
}
