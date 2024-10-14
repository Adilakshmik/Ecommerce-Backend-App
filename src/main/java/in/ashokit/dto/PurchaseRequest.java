package in.ashokit.dto;

import java.util.Set;

import in.ashokit.entity.Address;
import in.ashokit.entity.Customer;
import in.ashokit.entity.Order;
import in.ashokit.entity.OrderItem;

public class PurchaseRequest {
	
	private Customer customer;
	private Order order;
	private Address ShippingAddress;
	private Set<OrderItem> orderItems;
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	
	public Address getShippingAddress() {
		return ShippingAddress;
	}
	public void setShippingAddress(Address shippingAddress) {
		ShippingAddress = shippingAddress;
	}
	public Set<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(Set<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	
	
	
	
	

}
