package utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import domain.CreditCard;

public class Utiles {

	// Comprobacion de si se ha dejado la tarjeta vacia

	public static boolean checkEmptyCreditCard(CreditCard creditCard) {
		Assert.notNull(creditCard);
		boolean result = false;

		if (StringUtils.isBlank(creditCard.getBrand())
				&& StringUtils.isBlank(creditCard.getName())
				&& StringUtils.isBlank(creditCard.getNumber())
				&& creditCard.getCvv() == null
				&& creditCard.getExpirationMonth() == null
				&& creditCard.getExpirationYear() == null) {
			result = true;
		}

		return result;
	}

	// Comprobacion de la validez de la credit card
	public static boolean checkCreditCard(CreditCard creditCard) {
		Assert.notNull(creditCard);
		boolean result = true;

		if (StringUtils.isBlank(creditCard.getBrand())
				|| StringUtils.isBlank(creditCard.getName())
				|| StringUtils.isBlank(creditCard.getNumber())
				|| creditCard.getCvv() == null
				|| creditCard.getExpirationMonth() == null
				|| creditCard.getExpirationYear() == null
				|| !isValidCardNumber(creditCard.getNumber())) {
			result = false;
		}

		return result;
	}

	public static boolean isValidCardNumber(String ccNumber) {
		try {
			ccNumber = ccNumber.replaceAll("\\D", "");
			char[] ccNumberArry = ccNumber.toCharArray();
			int checkSum = 0;
			for (int i = ccNumberArry.length - 1; i >= 0; i--) {
				char ccDigit = ccNumberArry[i];
				if ((ccNumberArry.length - i) % 2 == 0) {
					int doubleddDigit = Character.getNumericValue(ccDigit) * 2;
					checkSum += (doubleddDigit % 9 == 0 && doubleddDigit != 0) ? 9
							: doubleddDigit % 9;
				} else {
					checkSum += Character.getNumericValue(ccDigit);
				}
			}
			return (checkSum != 0 && checkSum % 10 == 0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/*
	 * Metodo que comprueba que la URL de la imagen añadida esté almacenada en
	 * Flickr o Tumblr
	 */
	public static Boolean checkURL(String url) {
		Assert.notNull(url);
		Boolean res = false;

		if (url.contains("www.flickr.com") || url.contains("www.tumblr.com")
				|| url.contains("flic.kr") || url.contains("tumblr.com")) {
			res = true;
		}
		return res;

	}

	/*
	 * Metodo que comprueba que la URL de la imagen añadida esté almacenada en
	 * Flickr o Tumblr, este servicio es para las imágenes de los Servicios ya
	 * que estos pueden tener más de una imagen y debe comprobar todas las URLs
	 * de la Collction de imagenes
	 */
	public static Boolean checkURLs(Collection<String> urls) {
		Boolean res = true;
		if (urls != null && urls.size() != 0) {
			for (String url : urls) {
				if (url.contains("www.flickr.com")
						|| url.contains("www.tumblr.com")
						|| url.contains("flic.kr")
						|| url.contains("tumblr.com")) {

					res = res && true;
				} else {
					res = res && false;
				}
			}
		}
		return res;

	}

	// Split pictures from string
	public static Collection<String> splitPictures(String pictures) {
		Collection<String> result = new ArrayList<String>();
		if (!pictures.isEmpty()) {
			result = Arrays.asList(pictures.split(","));
		}
		return result;
	}

}
