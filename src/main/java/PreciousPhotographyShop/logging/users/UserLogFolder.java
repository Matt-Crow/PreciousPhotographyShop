package PreciousPhotographyShop.logging.users;

import PreciousPhotographyShop.logging.AbstractLogFolder;
import PreciousPhotographyShop.users.UserEntity;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import org.springframework.stereotype.Component;

/**
 *
 * @author Matt
 */
@Component
public class UserLogFolder extends AbstractLogFolder<UserLog> {
    
    public final UserLog getOrCreateLogForUser(UserEntity user) throws IOException{
        File asFile = this.getOrCreateFile(getFileNameForUser(user));
        return new UserLog(asFile.getAbsolutePath(), user);
    }
    
    private String getFileNameForUser(UserEntity user){
        return String.format("%s.txt", user.getId());
    }

    @Override
    protected String getLogSubfolderName() {
        return "users";
    }

    @Override
    public UserLog getLogByName(String name) throws IOException {
        String path = Paths.get(this.getFolder().getAbsolutePath(), name).toString();
        return new UserLog(path, null);
    }
}
