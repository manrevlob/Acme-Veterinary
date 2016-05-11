package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.MessageFolder;

@Component
@Transactional
public class MessageFolderToStringConverter implements Converter<MessageFolder, String> {

	public String convert(MessageFolder messageFolder) {
		Assert.notNull(messageFolder);
		String result;
		result = String.valueOf(messageFolder.getId());
		return result;

	}
}
