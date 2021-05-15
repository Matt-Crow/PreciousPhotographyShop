package PreciousPhotographyShop.logging.encryption;

import org.springframework.stereotype.Service;

/**
 *
 * @author Matt
 */
@Service
public class EncryptionProvider {
    
    public final Encrypter createDefaultEncrypter() throws Exception{
        EncryptionKeys props = getKeys();
        Encrypter enc = new Encrypter(props);
        return enc;
    }
    
    private EncryptionKeys getKeys() throws Exception{
        // Default to 16 byte AES encryption keys
        EncryptionPropertyLoader propLoader = new EncryptionPropertyLoader("AES", 16);
        EncryptionProperties props;
        if(!propLoader.exists()){
            props = propLoader.createAndStore();
        } else {
            props = propLoader.load();
        }
        EncryptionKeys keys = new EncryptionKeys(
            "AES",
            16,
            props.getKey(),
            props.getIv()
        );
        return keys;
    }

    public final String[] getFiveFactorsOfAuthentication() throws Exception {
        EncryptionKeys keys = getKeys();
        FiveFactorAuthenticator ffa = new FiveFactorAuthenticator(keys);
        return ffa.getFiveFactorAuthentication();
    }
}
