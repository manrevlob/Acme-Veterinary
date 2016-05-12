package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.PetType;

@Component
@Transactional
public class PetTypeToStringConverter implements Converter<PetType, String> {

	@Override
	public String convert(PetType petType) {
		Assert.notNull(petType);
		String result;
		result = String.valueOf(petType.getId());
		return result;
	}

}