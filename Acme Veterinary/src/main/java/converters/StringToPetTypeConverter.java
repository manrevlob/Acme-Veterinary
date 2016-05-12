package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.PetTypeRepository;
import domain.PetType;

@Component
@Transactional
public class StringToPetTypeConverter implements Converter<String, PetType> {

	@Autowired
	PetTypeRepository petTypeRepository;

	@Override
	public PetType convert(String text) {
		PetType result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = petTypeRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;

	}

}
