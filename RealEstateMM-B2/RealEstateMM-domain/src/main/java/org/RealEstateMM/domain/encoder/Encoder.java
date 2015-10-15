package org.RealEstateMM.domain.encoder;

public interface Encoder {

	String encode(String stringToEncode);

	String decode(String confirmationCode);

}
