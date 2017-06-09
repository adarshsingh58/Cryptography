package online.mrwallet.www;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encryption {

	/**
	 * ECB cannot use IV
	 */
	public final static String ECB = "AES/ECB/PKCS5Padding";

	/**
	 * CBC uses IV and thus is more secure
	 */
	public final static String CBC = "AES/CBC/PKCS5Padding";

	public static void main(String[] args)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter Input Text:");
		String input = scanner.nextLine();
		System.out.println("Enter 128 bit key:");//128bit means 16byte key ie.e length of key shud be 16
		String key = scanner.next();

		String encryptedData1 = encrptyNonIV(input, key);
		System.out.println("EBC output:" + encryptedData1);

		String encryptedData2 = encrptyIV(input, key);
		System.out.println("CBC with IV output:" + encryptedData2);

	}

	/**
	 * Here we are using CBC encryption algo which means it need an IV parameter
	 * as a first key. This IV input para must be random and thus is created by
	 * SecureRandom and IvParameterSpec
	 * 
	 * @param input
	 * @param key
	 * @return
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws UnsupportedEncodingException
	 */
	private static String encrptyIV(String input, String key)
			throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {

		byte[] iv = new byte[16];
		SecureRandom random = new SecureRandom();
		random.nextBytes(iv);
		IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");

		Cipher cipher = Cipher.getInstance(CBC);

		cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);

		byte[] outputByte = cipher.doFinal(input.getBytes("UTF-8"));
		String output = outputByte.toString();

		return output;
	}

	/**
	 * Here we are using EBC encryption algo which means only key is required
	 * and no IV is needed here
	 * 
	 * @param input
	 * @param key
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws UnsupportedEncodingException
	 */
	private static String encrptyNonIV(String input, String key)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, UnsupportedEncodingException {

		// method 1 to get key
		SecretKey keySpec = new SecretKeySpec(key.getBytes(), "AES");
		// OR
		// method 2 to get key . This key will be random and not specified by
		// user
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(128);
		SecretKey secretKey = keyGenerator.generateKey();

		// method 3 to get key. here we r usng sha1 hash output of input key as
		// final key
		getKey(key);

		Cipher cipher = Cipher.getInstance(ECB);

		cipher.init(Cipher.ENCRYPT_MODE, secretKey);

		byte[] outputByte = cipher.doFinal(input.getBytes("UTF-8"));

		String output = outputByte.toString();

		return output;

	}

	/**
	 * Here the method 1 of generating is used but before that the input key is
	 * itself hashed by SHA1 hashing and then converted to secret key by method
	 * 1 and returned
	 * 
	 * @param myKey
	 */
	public static void getKey(String myKey) {

		MessageDigest sha = null;
		try {
			byte[] key = myKey.getBytes("UTF-8");
			System.out.println(key.length);
			sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16); // use only first 128 bit
			System.out.println(key.length);
			System.out.println(new String(key, "UTF-8"));
			SecretKey secretKey = new SecretKeySpec(key, "AES");

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}
}
