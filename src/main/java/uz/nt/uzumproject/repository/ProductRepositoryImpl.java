package uz.nt.uzumproject.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Page<Product> universalSearch(Map<String, String> map) {
        int size = 10, page = 0;

        if(map.containsKey("size")){
            size = Math.max(Integer.parseInt(map.get("size")),1);
        }
        if(map.containsKey("page")){
            page = Integer.parseInt(map.get("page"));
        }


        String sql = "select p from Product p where 1=1 ";
        String sqlCount = "select count(1) from Product p where 1=1 ";
        StringBuilder queryCondition = new StringBuilder();
        generateQueryCondition(queryCondition, map);


        Query query = entityManager.createQuery(sql + queryCondition);
        Query countQuery = entityManager.createQuery(sqlCount + queryCondition);

        setParam(query,map);
        setParam(countQuery,map);

        long count = (long) countQuery.getSingleResult();
        System.out.println(count);

        if(count > 0 && count / size <= page) {
            if (count % size == 0) page = (int) count / size - 1;
            else  page = (int) count / size;
        }
//        }else page = count / size;


        query.setMaxResults(size);
        query.setFirstResult(size * page);

        System.out.println(query.getResultList());
        List<Product> productList =  query.getResultList();

        System.out.println(productList);
        return new PageImpl<>(productList, PageRequest.of(page, size), count);

    }

    private void generateQueryCondition(StringBuilder queryCondition , Map<String , String> params){
        if(params.containsKey("name")){
            queryCondition.append(" AND upper(p.name) like :name ");
        }
        if(params.containsKey("amount")){
            queryCondition.append(" AND p.amount = :amount ");
        }
        if(params.containsKey("description")){
            queryCondition.append(" AND upper(p.description) like :description ");
        }
        if(params.containsKey("price")){
            queryCondition.append(" AND p.price = :name ");
        }
    }
    private void setParam(Query query , Map<String , String> params){
        if(params.containsKey("name")){
            query.setParameter("name","%"+params.get("name").toUpperCase()+"%");
        }
        if(params.containsKey("amount")){
            query.setParameter("amount",Integer.parseInt(params.get("amount")));
        }
        if(params.containsKey("description")){
            query.setParameter("description","%"+params.get("description").toUpperCase()+"%");
        }
        if(params.containsKey("price")){
            query.setParameter("price",Integer.parseInt(params.get("price")));
        }
    }
}
