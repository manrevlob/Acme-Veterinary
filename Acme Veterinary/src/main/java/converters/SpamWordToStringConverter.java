package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.SpamWord;

@Component
@Transactional
public class SpamWordToStringConverter implements
		Converter<SpamWord, String> {

	@Override
	public String convert(SpamWord spamWord) {
		Assert.notNull(spamWord);
		String result;
		result = String.valueOf(spamWord.getId());
		return result;
	}

}