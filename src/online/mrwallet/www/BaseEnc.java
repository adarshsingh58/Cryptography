package online.mrwallet.www;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import org.apache.commons.codec.binary.Base64;

public class BaseEnc {

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter Input to Encode:");
		String input=sc.nextLine();
		try {
			System.out.println("Encoded value: "+encodeInBase64(input));
			System.out.println("Decoded Value: "+decodeInBase64(encodeInBase64(input)));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
	
	public static String encodeInBase64(String input) throws UnsupportedEncodingException {
		byte[] encodedBytes = Base64.encodeBase64(input.getBytes("UTF-8"));
		return new String(encodedBytes);
	}
	
	public static String decodeInBase64(String input) throws UnsupportedEncodingException {
		byte[] encodedBytes = Base64.decodeBase64(input.getBytes("UTF-8"));
		return new String(encodedBytes);
	}
	
	
	
}
