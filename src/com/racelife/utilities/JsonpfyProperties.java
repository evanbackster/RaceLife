package com.racelife.utilities;

import javax.servlet.http.HttpServletRequest;

public class JsonpfyProperties {

	public static boolean OPEN = true;

	public static final String ENTITY_KIND = "kind";

	public static final String ENTITY_ID = "ID";

	public static final String ENTITY_KEY_KIND = "KeyKind";

	public static boolean authenticate(HttpServletRequest request) {
		
		//&authToken=c52283e23432375d61bfd26531757ae7

		String passkey = "jsonpfykey";

		String authToken = URLUtilities.decode(request
				.getParameter("authToken"));

		String tokenDecrypt = EncryptText.decrypt(authToken);

		if (passkey.equals(tokenDecrypt)) {

			return true;
		}

		return false;
	}

}
