package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Item;

@Component
@Transactional
public class ItemToStringConverter implements Converter<Item, String> {

	@Override
	public String convert(Item item) {
		Assert.notNull(item);
		String result;
		result = String.valueOf(item.getId());
		return result;
	}

}