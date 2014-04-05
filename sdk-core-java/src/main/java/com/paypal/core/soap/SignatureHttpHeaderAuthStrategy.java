package com.paypal.core.soap;

import java.util.HashMap;
import java.util.Map;

import com.paypal.core.AbstractSignatureHttpHeaderAuthStrategy;
import com.paypal.core.Constants;
import com.paypal.core.credential.SignatureCredential;
import com.paypal.core.credential.TokenAuthorization;
import com.paypal.sdk.exceptions.OAuthException;
import com.paypal.sdk.util.OAuthSignature;

/**
 * <code>CertificateHttpHeaderAuthStrategy</code> is an implementation of
 * {@link AuthenticationStrategy} which acts on {@link SignatureCredential} and
 * retrieves them as HTTP headers
 * 
 */
public class SignatureHttpHeaderAuthStrategy extends
		AbstractSignatureHttpHeaderAuthStrategy {

	/**
	 * SignatureHttpHeaderAuthStrategy
	 * 
	 * @param endPointUrl
	 */
	public SignatureHttpHeaderAuthStrategy(String endPointUrl) {
		super(endPointUrl);
	}

	/**
	 * Processing for {@link TokenAuthorization} under
	 * {@link SignatureCredential}
	 * 
	 * @param sigCred
	 *            {@link SignatureCredential} instance
	 * @param tokenAuth
	 *            {@link TokenAuthorization} instance
	 * @return Map of HTTP headers
	 * @throws OAuthException
	 */
	protected Map<String, String> processTokenAuthorization(
			SignatureCredential sigCred, TokenAuthorization tokenAuth)
			throws OAuthException {
		Map<String, String> headers = new HashMap<String, String>();
		String authString = OAuthSignature.getFullAuthString(
				sigCred.getUserName(), sigCred.getPassword(),
				tokenAuth.getAccessToken(), tokenAuth.getTokenSecret(),
				OAuthSignature.HTTPMethod.POST, endPointUrl, null);
		headers.put(Constants.PAYPAL_AUTHORIZATION_MERCHANT_HEADER, authString);
		return headers;
	}

}
