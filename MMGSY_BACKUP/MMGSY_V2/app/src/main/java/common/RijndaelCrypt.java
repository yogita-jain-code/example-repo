package common;


/*
 *out algo:  11 rounds if the key/block size is 192 bits
 * 
 * "The AES selection was always going to be a compromise, balancing various factors such as overall security, performance, and efficiency. As such, it was unlikely that the selection of any one algorithm would receive unanimous praise from all quarters. Rijndael's selection has been criticized by some because the algorithm does not appear to be as secure as some of the other choices.

This criticism is valid theoretically, but does not mean that data secured using this algorithm is going to be unacceptably vulnerable to attack. Although Rijndael may not have been the most secure algorithm from an academic viewpoint, defenders claim that it is more than likely secure enough for all applications in the real world and can be enhanced by simply adding more rounds. Attacks on the algorithm have succeeded only in an extremely limited environment and, while interesting from a mathematical viewpoint, appear to have little consequence in the real world."
 *
 * Link : http://searchsecurity.techtarget.com/definition/Rijndael
 * */

import android.util.Log;
import android.util.Base64;
import android.util.Xml.Encoding;
 
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
 
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
 
public class RijndaelCrypt {
 
    public static final String TAG = "YourAppName";
 
    private static String TRANSFORMATION = "AES/CBC/PKCS7Padding";
    private static String ALGORITHM = "AES";
    private static String DIGEST = "MD5";
     
    private static Cipher _cipher;
    private static SecretKey _password;
    private static IvParameterSpec _IVParamSpec;
     
    //16-byte private key
    private static byte[] IV = "ThisIsUrPassword".getBytes();
     
     
    /**
     Constructor
     @password Public key
      
    */
    
    public RijndaelCrypt() {
    	
    }
    public RijndaelCrypt(String password) {
 
        try {
             
            //Encode digest
            MessageDigest digest;           
            digest = MessageDigest.getInstance(DIGEST);            
            _password = new SecretKeySpec(digest.digest(password.getBytes()), ALGORITHM);
             
            //Initialize objects
            _cipher = Cipher.getInstance(TRANSFORMATION);
            _IVParamSpec = new IvParameterSpec(IV);
             
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "No such algorithm " + ALGORITHM, e);
        } catch (NoSuchPaddingException e) {
            Log.e(TAG, "No such padding PKCS7", e);
        }              
    }
 
    /**
    Encryptor.
 
    @text String to be encrypted
    @return Base64 encrypted text
 
    */
    public String encrypt(byte[] text) {
         
        byte[] encryptedData;
         
        try {
             
            _cipher.init(Cipher.ENCRYPT_MODE, _password, _IVParamSpec);
            encryptedData = _cipher.doFinal(text);
             
        } catch (InvalidKeyException e) {
            Log.e(TAG, "Invalid key  (invalid encoding, wrong length, uninitialized, etc).", e);
            return null;
        } catch (InvalidAlgorithmParameterException e) {
            Log.e(TAG, "Invalid or inappropriate algorithm parameters for " + ALGORITHM, e);
            return null;
        } catch (IllegalBlockSizeException e) {
            Log.e(TAG, "The length of data provided to a block cipher is incorrect", e);
            return null;
        } catch (BadPaddingException e) {
            Log.e(TAG, "The input data but the data is not padded properly.", e);
            return null;
        }               
         
        return Base64.encodeToString(encryptedData,Base64.DEFAULT);
         
    }
     
    /**
    Decryptor.
 
    @text Base64 string to be decrypted
    @return decrypted text
 
    */   
    public String decrypt(String text) {
 
        try {
            _cipher.init(Cipher.DECRYPT_MODE, _password, _IVParamSpec);
             
            byte[] decodedValue = Base64.decode(text.getBytes(), Base64.DEFAULT);
            byte[] decryptedVal = _cipher.doFinal(decodedValue);
            return new String(decryptedVal);            
             
             
        } catch (InvalidKeyException e) {
            Log.e(TAG, "Invalid key  (invalid encoding, wrong length, uninitialized, etc).", e);
            return null;
        } catch (InvalidAlgorithmParameterException e) {
            Log.e(TAG, "Invalid or inappropriate algorithm parameters for " + ALGORITHM, e);
            return null;
        } catch (IllegalBlockSizeException e) {
            Log.e(TAG, "The length of data provided to a block cipher is incorrect", e);
            return null;
        } catch (BadPaddingException e) {
            Log.e(TAG, "The input data but the data is not padded properly.", e);
            return null;
        }               
         
    }
    
    public static String getMD5(String password){
		
		 MessageDigest m = null;
		// String salt = "^%V{T%&]";	 // Please do not change this salt
	        try {
	                m = MessageDigest.getInstance("MD5");
	        } catch (NoSuchAlgorithmException e) {
	                e.printStackTrace();
	        }
	        String p;
	      
	        p = password;
	       
	        m.update(p.getBytes(),0,p.length());
	        String hash = new BigInteger(1, m.digest()).toString(16);
	        
	        hash = ("00000000000000000000000000000000"+hash).substring(hash.length());
	        
	        Log.d("Hash Using MD 5 = ", hash);
		
	        return hash.toLowerCase();
	} // getMD5 ends here
     
}