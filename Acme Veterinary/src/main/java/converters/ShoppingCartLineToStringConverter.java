package converters;



import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.ShoppingCartLine;

@Component
@Transactional
public class ShoppingCartLineToStringConverter implements
		Converter<ShoppingCartLine, String> {

	@Override
	public String convert(ShoppingCartLine shoppingCartLine) {
		String result;

		if (shoppingCartLine == null)
			result = null;
		else
			result = String.valueOf(shoppingCartLine.getId());

		return result;
	}

}
