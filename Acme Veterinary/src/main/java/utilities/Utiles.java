package utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.util.Assert;

public class Utiles {

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
