package PreciousPhotographyShop.logging.encryption;

/**
 *
 * @author Matt
 */
public class EncryptionProvider {
    public static final Encrypter createDefaultEncrypter() throws Exception{
        EncryptionProperties props = getProperties();
        Encrypter enc = new Encrypter(props);
        return enc;
    }
    
    private static EncryptionProperties getProperties() throws Exception{
        // Default to 16 byte AES encryption keys
        EncryptionPropertyLoader propLoader = new EncryptionPropertyLoader("AES", 16);
        EncryptionProperties props = null;
        if(!propLoader.exists()){
            props = propLoader.createAndStore();
        } else {
            props = propLoader.load();
        }
        return props;
    }

    public static String[] getFiveFactorsOfAuthentication() throws Exception {
        EncryptionProperties props = getProperties();
        FiveFactorAuthenticator ffa = new FiveFactorAuthenticator(props);
        return ffa.getFiveFactorAuthentication();
    }
}
