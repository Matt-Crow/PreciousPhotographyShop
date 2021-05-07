package PreciousPhotographyShop.logging.encryption;

/**
 *
 * @author Matt
 */
public class EncryptionProvider {
    public static final Encrypter createDefaulEncrypter() throws Exception{
        // Default to 16 byte AES encryption keys
        EncryptionPropertyLoader propLoader = new EncryptionPropertyLoader("AES", 16);
        EncryptionProperties props = null;
        if(!propLoader.exists()){
            props = propLoader.createAndStore();
        } else {
            props = propLoader.load();
        }
        Encrypter enc = new Encrypter(props);
        return enc;
    }
}
