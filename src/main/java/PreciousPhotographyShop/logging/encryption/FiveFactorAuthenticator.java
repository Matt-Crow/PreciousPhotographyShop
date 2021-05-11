package PreciousPhotographyShop.logging.encryption;

import java.util.Base64;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Matt
 */
public class FiveFactorAuthenticator {
    private final EncryptionKeys keys;
    
    public FiveFactorAuthenticator(EncryptionKeys props){
        this.keys = props;
    }
    
    public final String[] getFiveFactorAuthentication(){
        String[] fiveFactors = new String[5];
        
        // first factor is just the IV
        fiveFactors[0] = Base64.getEncoder().encodeToString(keys.getIv().getIV());
        
        byte[] theOther4 = keys.getKey().getEncoded();
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
    
    public final EncryptionKeys getEncryptionKeysFromFiveFactors(String[] ffa){
        // used later
        byte[] decodedIv = Base64.getDecoder().decode(ffa[0]);
        IvParameterSpec iv = new IvParameterSpec(decodedIv);
        
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
        
        return new EncryptionKeys(
            "AES", 
            16, 
            new SecretKeySpec(allOredTogether, "AES"), 
            iv
        );
    }
    
    public static void main(String[] args) throws Exception{
        EncryptionKeys newKeys = new EncryptionKeyProvider("AES", 16).newKeys();
        Encrypter enc = new Encrypter(newKeys);
        FiveFactorAuthenticator ffa = new FiveFactorAuthenticator(newKeys);
        
        String[] split = ffa.getFiveFactorAuthentication();
        for(int i = 0; i < split.length; i++){
            System.out.printf("%d: %s\n", i, split[i]);
        }
        
        String encrypted = enc.encrypt("Hello world!");
        
        newKeys = ffa.getEncryptionKeysFromFiveFactors(split);
        enc = new Encrypter(newKeys);
        String decrypted = enc.decrypt(encrypted);
        
        System.out.println(decrypted);
    }
}
