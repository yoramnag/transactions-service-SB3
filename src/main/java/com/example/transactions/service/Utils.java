package com.example.transactions.service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
	
	private final static String Algorithm = "SHA-256";
	
	public static boolean luhnValidetor(String creditCardNumber) {
		return org.apache.commons.validator.routines.checkdigit.
				LuhnCheckDigit.LUHN_CHECK_DIGIT.isValid(creditCardNumber);
	}
	
    private static byte[] getSHA(String input) throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance(Algorithm);
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }
     
    private static String toHexString(byte[] hash)
    {
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length() < 64)
        {
            hexString.insert(0, '0');
        }
 
        return hexString.toString();
    }
    
    public static String  maskCreditCard(String creditCrard) {
    	try {
			return toHexString(getSHA(creditCrard));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
		
	}
    
    public static String mask(String creditCrard) {
    	creditCrard = creditCrard.replace("-", "");
    	int length = creditCrard.length() - creditCrard.length()/4;
        String s = creditCrard.substring(0, length);
        String res = s.replaceAll("[A-Za-z0-9]", "X") + creditCrard.substring(length);
        return res;
    	
    }

}
