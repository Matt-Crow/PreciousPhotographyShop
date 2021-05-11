package PreciousPhotographyShop.logging.encryption;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

/**
 *
 * @author Matt
 */
@Component
@SessionScope
public class EncryptionKeys {
    private final SecretKey key;
    private final IvParameterSpec iv;
    
    public EncryptionKeys(SecretKey key, IvParameterSpec iv){
        this.key = key;
        this.iv = iv;
    }
    
    public final SecretKey getKey(){
        return key;
    }
    
    public final IvParameterSpec getIv(){
        return iv;
    }
}
