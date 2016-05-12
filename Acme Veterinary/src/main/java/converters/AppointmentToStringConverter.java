package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Appointment;

@Component
@Transactional
public class AppointmentToStringConverter implements
		Converter<Appointment, String> {

	@Override
	public String convert(Appointment appointment) {
		Assert.notNull(appointment);
		String result;
		result = String.valueOf(appointment.getId());
		return result;
	}

}