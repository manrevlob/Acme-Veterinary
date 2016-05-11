package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.VeterinaryRepository;
import domain.Veterinary;

@Component
@Transactional
public class StringToVeterinaryConverter implements
		Converter<String, Veterinary> {

	@Autowired
	VeterinaryRepository veterinaryRepository;

	@Override
	public Veterinary convert(String text) {
		Veterinary result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = veterinaryRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;

	}

}
