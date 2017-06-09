package online.mrwallet.www;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

public class Hashing {

	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Input to Encrypt:");
		String input = sc.nextLine();
		
		/*Manual method to get hex format SHA1 encryption of input */
		System.out.println(Hex.encodeHex(getSHA1(input)));
		
		/*Apache's Codec Library used to get the same hex format SHA1 encryption of input */
		System.out.println(DigestUtils.sha1Hex(input.getBytes()));
	}

	public static byte[] getSHA1(String input) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.update(input.getBytes());
		byte byteData[] = md.digest();
		return byteData;
	}
	
	//SHA1 Hashing is a one way process and tehre is no way to unhash the output back to input 

}
