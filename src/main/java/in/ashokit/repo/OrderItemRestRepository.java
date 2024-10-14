package in.ashokit.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import in.ashokit.entity.OrderItem;

@RepositoryRestResource
@CrossOrigin("http://localhost:4200")
public interface OrderItemRestRepository extends JpaRepository<OrderItem,Long>{

}
