package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Administrator;

@Component
@Transactional
public class AdministratorToStringConverter implements Converter<Administrator, String> {

	public String convert(Administrator administrator) {
		Assert.notNull(administrator);
		String result;
		result = String.valueOf(administrator.getId());
		return result;

	}
}
