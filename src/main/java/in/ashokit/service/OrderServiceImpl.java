package in.ashokit.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import in.ashokit.dto.PurchaseRequest;
import in.ashokit.dto.PurchaseResponse;
import in.ashokit.entity.Customer;
import in.ashokit.entity.Order;
import in.ashokit.entity.OrderItem;
import in.ashokit.repo.CustomerRestRepository;
import in.ashokit.repo.OrderRestRepository;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private CustomerRestRepository customerRepository;
	
	@Autowired
	private OrderRestRepository orderRepo;
	
	//@Value("${razorpay.key.id}")
  private String keyId = "rzp_test_oXc1iudPXI8hB9";

 // @Value("${razorpay.key.secret}")
  private String keySecret = "UyfHrE55FqYJM3cNz6H3eMRg";
  
  String orderTrackingNumber;

	@Override
	public PurchaseResponse placeOrder(PurchaseRequest purchaseRequest) throws Exception {
		Order order = purchaseRequest.getOrder();
		if(order==null) {
			System.out.println("order is null");
		}
		
		String orderTrackingNumber = UUID.randomUUID().toString();
		order.setOrderTrackingNumber(orderTrackingNumber);
		order.setStatus("PENDING");
		order.setDateCreated(purchaseRequest.getOrder().getDateCreated());
		 
		Set<OrderItem> orderItems = purchaseRequest.getOrderItems();
		orderItems.forEach(item -> order.add(item));
		
		order.setShippingAddress(purchaseRequest.getShippingAddress());
	
		Customer customer = purchaseRequest.getCustomer();
		Customer customerFromDb = customerRepository.findByEmail(customer.getEmail());
		if(customerFromDb!=null) {
			customer=customerFromDb;
		}
		customer.add(order);
		
		 String razorpayOrderId = createRazorpay(purchaseRequest);
		 System.out.println("razorpayOrderId :"+razorpayOrderId);
		 System.out.println("orderTrackingNumber:"+orderTrackingNumber);
		 order.setRazorpayOrderId(razorpayOrderId);
		 Customer saveCustomer = customerRepository.save(customer);
			
		return new PurchaseResponse(orderTrackingNumber, razorpayOrderId);
	}
	
	
	
	public String createRazorpay(PurchaseRequest purchaseRequest) throws RazorpayException {
		
		JSONObject orderReq=new JSONObject();
		orderReq.put("amount",purchaseRequest.getOrder().getTotalPrice() * 100 );
		orderReq.put("currency", "INR");
		orderReq.put("receipt",purchaseRequest.getCustomer().getEmail() );
		
		RazorpayClient client=new RazorpayClient(keyId, keySecret);
		com.razorpay.Order razorPayOrder = client.Orders.create(orderReq);
		
		return razorPayOrder.get("id");
	}
	
	
	public void updateOrderStatus(String status, String razorpayPaymentId,String razorpayOrderId) throws Exception {
	    // Find the order using the razorpayPaymentId
		
		
	    Order order = orderRepo.findByRazorpayOrderId(razorpayOrderId);
	   
	    if (order != null) {
	        // Set the status and Razorpay payment ID
	    	order.setRazorpayPaymentId(razorpayPaymentId);
	    	
	    	 order.setStatus(status);
	        // Save the updated order
	        orderRepo.save(order);
	    } else {
	        // Handle the case when no order is found
	        throw new Exception("Order not found for Razorpay ID: " + razorpayPaymentId);
	    }
	}


	
	
}
