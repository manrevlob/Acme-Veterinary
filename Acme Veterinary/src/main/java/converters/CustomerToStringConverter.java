package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Customer;

@Component
@Transactional
public class CustomerToStringConverter implements Converter<Customer, String> {

	public String convert(Customer customer) {
		Assert.notNull(customer);
		String result;
		result = String.valueOf(customer.getId());
		return result;

	}
}
