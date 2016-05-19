package converters;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.ShoppingCartLineRepository;
import domain.ShoppingCartLine;

@Component
@Transactional
public class StringToShoppingCartLineConverter implements
		Converter<String, ShoppingCartLine> {
	@Autowired
	ShoppingCartLineRepository shoppingCartLineRepository;

	@Override
	public ShoppingCartLine convert(String text) {
		ShoppingCartLine result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = shoppingCartLineRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
