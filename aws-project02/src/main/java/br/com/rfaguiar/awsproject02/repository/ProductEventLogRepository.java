package br.com.rfaguiar.awsproject02.repository;

import br.com.rfaguiar.awsproject02.model.ProductEventKey;
import br.com.rfaguiar.awsproject02.model.ProductEventLog;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface ProductEventLogRepository extends CrudRepository<ProductEventLog, ProductEventKey> {

    List<ProductEventLog> findAllByPk(String code);
    List<ProductEventLog> findAllByPkAndSkStartingWith(String code, String eventType);

}
