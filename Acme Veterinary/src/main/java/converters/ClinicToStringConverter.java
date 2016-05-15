package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Clinic;

@Component
@Transactional
public class ClinicToStringConverter implements Converter<Clinic, String> {

	@Override
	public String convert(Clinic clinic) {
		Assert.notNull(clinic);
		String result;
		result = String.valueOf(clinic.getId());
		return result;
	}

}