package in.ashokit.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

import in.ashokit.entity.Order;

@RepositoryRestResource
@CrossOrigin("http://localhost:4200")
public interface OrderRestRepository extends JpaRepository<Order,Integer>{
	public Page<Order> findByCustomerEmail(@Param("email") String email, Pageable pageable);
	
	Order findByRazorpayOrderId(@Param("razorpayOrderId") String razorpayOrderId);
	 
	 Order findByRazorpayPaymentId(@Param("razorpayPaymentId") String razorpayPaymentId);
	 
	 

	
}
