package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.History;

@Component
@Transactional
public class HistoryToStringConverter implements Converter<History, String> {

	@Override
	public String convert(History history) {
		Assert.notNull(history);
		String result;
		result = String.valueOf(history.getId());
		return result;
	}

}