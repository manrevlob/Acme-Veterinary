package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Veterinary;

@Component
@Transactional
public class VeterinaryToStringConverter implements
		Converter<Veterinary, String> {

	public String convert(Veterinary veterinary) {
		Assert.notNull(veterinary);
		String result;
		result = String.valueOf(veterinary.getId());
		return result;

	}
}
