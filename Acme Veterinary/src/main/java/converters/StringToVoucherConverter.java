package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.VoucherRepository;
import domain.Voucher;

@Component
@Transactional
public class StringToVoucherConverter implements Converter<String, Voucher> {

	@Autowired
	VoucherRepository voucherRepository;

	@Override
	public Voucher convert(String text) {
		Voucher result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = voucherRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;

	}

}
