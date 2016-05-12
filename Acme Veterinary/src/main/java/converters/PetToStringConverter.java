package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Pet;

@Component
@Transactional
public class PetToStringConverter implements Converter<Pet, String> {

	@Override
	public String convert(Pet pet) {
		Assert.notNull(pet);
		String result;
		result = String.valueOf(pet.getId());
		return result;
	}

}