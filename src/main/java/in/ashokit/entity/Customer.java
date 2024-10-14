package in.ashokit.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer customerId;
	private String name;
	private Long phno;
	private String email;
	
	@OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
	@JsonManagedReference 
	private Set<Order> orders=new HashSet<>();
	
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Set<Order> getOrder() {
		return orders;
	}
	public void setOrder(Set<Order> order) {
		this.orders = order;
	}
	
	
	
	


	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
//	public void add(Order orders) {
//		if(orders!=null) {
//			if(order==null) {
//				order.add(orders);
//			}
//			orders.setCustomer(this);
//		}
//	}
	
	public Long getPhno() {
		return phno;
	}
	public void setPhno(Long phno) {
		this.phno = phno;
	}
	public Set<Order> getOrders() {
		return orders;
	}
	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
	public void add(Order order) {
		if(order!=null) {
			if(orders==null) {
				orders=new HashSet();
				
			}
			orders.add(order);
			order.setCustomer(this);
			
		}
		
	}
	

}
