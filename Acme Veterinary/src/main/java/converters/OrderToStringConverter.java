package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Order;

@Component
@Transactional
public class OrderToStringConverter implements Converter<Order, String> {

	@Override
	public String convert(Order order) {
		Assert.notNull(order);
		String result;
		result = String.valueOf(order.getId());
		return result;
	}

}