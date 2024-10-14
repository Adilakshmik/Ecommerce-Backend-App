package in.ashokit.service;

import java.util.List;

import com.razorpay.RazorpayException;

import in.ashokit.dto.PurchaseRequest;
import in.ashokit.dto.PurchaseResponse;
import in.ashokit.entity.Order;

public interface OrderService {
	
	public PurchaseResponse placeOrder(PurchaseRequest request)throws Exception;
	public String createRazorpay(PurchaseRequest purchaseRequest) throws RazorpayException ;
	public void updateOrderStatus( String string,String OrderTrackingNumber,String razorpayOrderId) throws Exception;
	
}
