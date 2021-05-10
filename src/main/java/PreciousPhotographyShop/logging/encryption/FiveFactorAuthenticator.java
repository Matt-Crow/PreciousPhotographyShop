package PreciousPhotographyShop.logging.encryption;

import java.util.Base64;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Matt
 */
public class FiveFactorAuthenticator {
    private final EncryptionProperties props;
    
    public FiveFactorAuthenticator(EncryptionProperties props){
        this.props = props;
    }
    
    public final String[] getFiveFactorAuthentication(){
        String[] fiveFactors = new String[5];
        
        // first factor is just the IV
        fiveFactors[0] = Base64.getEncoder().encodeToString(props.getIv().getIV());
        
        byte[] theOther4 = props.getKey().getEncoded();
        byte maskGetEvery4thByte = 17; // 16 + 1 = 0001 0001
        byte[] newFactor = new byte[theOther4.length];
        for(int factor = 1; factor < 5; factor++){
            for(int i = 0; i < newFactor.length; i++){
                newFactor[i] = (byte)(maskGetEvery4thByte & theOther4[i]);
            }
            fiveFactors[factor] = Base64.getEncoder().encodeToString(newFactor);
            maskGetEvery4thByte <<= 1;
        }
        
        return fiveFactors;
    }
    
    public final EncryptionProperties getEncryptionPropertiesFromFiveFactors(String[] ffa){
        EncryptionProperties props = new EncryptionProperties("AES");
        
        byte[] decodedIv = Base64.getDecoder().decode(ffa[0]);
        IvParameterSpec iv = new IvParameterSpec(decodedIv);
        props.setIv(iv);
        
        byte[][] splitKey = new byte[4][];
        for(int factor = 1; factor < 5; factor++){
            splitKey[factor - 1] = Base64.getDecoder().decode(ffa[factor]);
        }
        byte[] allOredTogether = new byte[splitKey[0].length]; // they are all the same length
        for(int byteFactor = 0; byteFactor < splitKey.length; byteFactor++){
            for(int i = 0; i < allOredTogether.length; i++){
                allOredTogether[i] |= splitKey[byteFactor][i];
            }
        }
        props.setKey(new SecretKeySpec(allOredTogether, "AES"));
        
        return props;
    }
    
    public static void main(String[] args) throws Exception{
        EncryptionProperties newProps = new EncryptionPropertyProvider("AES", 16).newEncryptionProperties();
        Encrypter enc = new Encrypter(newProps);
        FiveFactorAuthenticator ffa = new FiveFactorAuthenticator(newProps);
        
        String[] split = ffa.getFiveFactorAuthentication();
        for(int i = 0; i < split.length; i++){
            System.out.printf("%d: %s\n", i, split[i]);
        }
        
        String encrypted = enc.encrypt("Hello world!");
        
        newProps = ffa.getEncryptionPropertiesFromFiveFactors(split);
        enc = new Encrypter(newProps);
        String decrypted = enc.decrypt(encrypted);
        
        System.out.println(decrypted);
    }
}
