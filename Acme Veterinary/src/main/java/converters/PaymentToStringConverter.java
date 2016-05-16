package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Payment;

@Component
@Transactional
public class PaymentToStringConverter implements Converter<Payment, String> {

	@Override
	public String convert(Payment payment) {
		Assert.notNull(payment);
		String result;
		result = String.valueOf(payment.getId());
		return result;
	}

}