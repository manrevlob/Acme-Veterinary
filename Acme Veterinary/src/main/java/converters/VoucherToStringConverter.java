package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Voucher;

@Component
@Transactional
public class VoucherToStringConverter implements
		Converter<Voucher, String> {

	public String convert(Voucher voucher) {
		Assert.notNull(voucher);
		String result;
		result = String.valueOf(voucher.getId());
		return result;

	}
}
