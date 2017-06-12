import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.masasdani.paypal.Application;
import com.paypal.api.payments.Address;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.CreditCard;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.FundingInstrument;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;



import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("deprecation")
@RunWith(SpringRunner.class)
@SpringApplicationConfiguration(Application.class)
public class TestCredicard {
	
	@Autowired
	private APIContext apiContext;
	
	@Test
	public void test_compra_crediCard() {
		
		// Set address info
		Address billingAddress = new Address();
		billingAddress.setCity("Johnstown");
		billingAddress.setCountryCode("US");
		billingAddress.setLine1("52 N Main ST");
		billingAddress.setPostalCode("43210");
		billingAddress.setState("OH");

		// Credit card info
		CreditCard creditCard = new CreditCard();
		creditCard.setBillingAddress(billingAddress);
		creditCard.setExpireMonth(11);
		creditCard.setExpireYear(2018);
		creditCard.setFirstName("Joe");
		creditCard.setLastName("Shopper");
		creditCard.setNumber("4669424246660779");
		creditCard.setType("visa");

		// Payment details
		Details details = new Details();
		details.setShipping("1");
		details.setSubtotal("5");
		details.setTax("1");
		details.setFee("free");
	

		// Total amount
		Amount amount = new Amount();
		amount.setCurrency("USD");
		amount.setTotal("7");
		amount.setDetails(details);

		// Transaction details
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setDescription("This is the payment transaction description.");
		

		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction);

		// Set funding instrument
		FundingInstrument fundingInstrument = new FundingInstrument();
		fundingInstrument.setCreditCard(creditCard);

		List<FundingInstrument> fundingInstrumentList = new ArrayList<FundingInstrument>();
		fundingInstrumentList.add(fundingInstrument);

		// Set payer details
		Payer payer = new Payer();
		payer.setFundingInstruments(fundingInstrumentList);
		payer.setPaymentMethod("credit_card");
		
		
		
		try {
			Payment payment = new Payment();
			payment.setPayer(payer);
			payment.setIntent("sale");
			payment.setTransactions(transactions);
			
			Payment createdPayment = payment.create(apiContext);
			System.out.println(createdPayment);
			  System.out.println("Created payment with id = " + createdPayment.getId());
			} catch (PayPalRESTException e) {
			  System.err.println(e.getDetails());
			}
		
	}

}
