package in.ashokit.repo;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import in.ashokit.entity.Product;

@RepositoryRestResource
@CrossOrigin("http://localhost:4200")
public interface ProductRestRepository extends JpaRepository<Product,Integer>{
	
	 
	
	Page<Product> findByCategoryCategoryId(@Param("categoryId") Integer categoryId, Pageable pageable);

	Page<Product> findByNameContaining(@Param("name") String name, Pageable pageable);
}
