package in.ashokit.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.ashokit.dto.PurchaseRequest;
import in.ashokit.dto.PurchaseResponse;
import in.ashokit.entity.Order;
import in.ashokit.repo.OrderRestRepository;
import in.ashokit.service.OrderService;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderRestRepository orderRepository;
	
	@PostMapping("/purchase")
	public PurchaseResponse placeOrder(@RequestBody PurchaseRequest purchaseRequest) throws Exception{
		System.out.println(purchaseRequest);
		System.out.println(purchaseRequest.getOrder());
		return orderService.placeOrder(purchaseRequest);
	}
	
	@GetMapping("/search/findByCustomerEmail")
	public ResponseEntity<Page<Order>> findByCustomerEmail(@RequestParam("email") String email, Pageable pageable) {
	    Page<Order> orders = orderRepository.findByCustomerEmail(email, pageable);
	    
	    if (orders.isEmpty()) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok(orders);
	}

	@PostMapping("/payment-success")
	public ResponseEntity<Map<String, String>> handlePaymentSuccess(@RequestBody Map<String, String> requestData) throws Exception {
	    String razorpayPaymentId = requestData.get("razorpayPaymentId");
	    String razorpayOrderId = requestData.get("razorpayOrderId");
	    if (razorpayPaymentId != null) {
	        orderService.updateOrderStatus("CONFIRMED", razorpayPaymentId,razorpayOrderId);

	        // Return a JSON response
	        Map<String, String> response = new HashMap<>();
	        response.put("message", "Order status updated to CONFIRMED");
	        return ResponseEntity.ok(response);
	    } else {
	    	orderService.updateOrderStatus("FAILED", razorpayPaymentId,razorpayOrderId);
	        Map<String, String> errorResponse = new HashMap<>();
	        errorResponse.put("error", "Invalid razorpayPaymentId");
	        return ResponseEntity.badRequest().body(errorResponse);
	    }
	}



}
