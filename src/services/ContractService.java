package services;

import java.util.Calendar;
import java.util.Date;

import entities.Contracts;
import entities.Installment;

public class ContractService {

	private OnlinePaymentService onlinePaymentService;
	
	
	public ContractService (OnlinePaymentService onlinePaymentService) {
		this.onlinePaymentService = onlinePaymentService;
	}
	
	public void processContract(Contracts contract, int month) {
		double basicQuota = contract.getTotalValue() / month;
		for(int i=1; i<month; i++) {
			double updatedQuota = basicQuota + onlinePaymentService.interest(basicQuota, i);
			double fullQuota = updatedQuota + onlinePaymentService.paymentFee(updatedQuota);
			Date date = addMonths(contract.getDate(), i);
			contract.getInstallments().add(new Installment(date, fullQuota));
		   }
		}
		
		private Date addMonths(Date date, int n) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.MONTH, n);
			return cal.getTime();
	}
}
