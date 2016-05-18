package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Bulletin;

@Component
@Transactional
public class BulletinToStringConverter implements Converter<Bulletin, String> {

	@Override
	public String convert(Bulletin bulletin) {
		Assert.notNull(bulletin);
		String result;
		result = String.valueOf(bulletin.getId());
		return result;
	}

}