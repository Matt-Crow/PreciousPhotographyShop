package PreciousPhotographyShop.logging;

import PreciousPhotographyShop.logging.encryption.Encrypter;
import PreciousPhotographyShop.logging.encryption.EncryptionKeys;
import PreciousPhotographyShop.logging.encryption.EncryptionProvider;
import PreciousPhotographyShop.logging.encryption.FiveFactorAuthenticator;
import PreciousPhotographyShop.logging.website.WebsiteLogFolder;
import PreciousPhotographyShop.temp.LoginService;
import PreciousPhotographyShop.users.UserEntity;
import java.io.IOException;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Matt
 */
@Controller
@RequestMapping("log")
public class LogController {
    private final EncryptionProvider encryptionProvider;
    private final LoginService loginService;
    private final LogService logService;
    private final WebsiteLogFolder websiteLogFolder;
    
    @Resource // EncryptionKeys is annotate to be stored in the session
    private EncryptionKeys currentUserEncKeys;
    
    public LogController(EncryptionProvider encryptionProvider, LoginService loginService, LogService logService, WebsiteLogFolder websiteLogFolder){
        this.encryptionProvider = encryptionProvider;
        this.loginService = loginService;
        this.logService = logService;
        this.websiteLogFolder = websiteLogFolder;
    }
    
    @GetMapping({"/", "/index"})
    public final String index(){
        return "logging/main";
    }
    
    @GetMapping("request-access")
    public final String requestAccess(){
        return "logging/requestLogAccess";
    }
    
    @PostMapping("request-access")
    public final String processAccessRequest(
        @RequestParam("email0") String email,
        @RequestParam("email1") String witnessEmail1,
        @RequestParam("email2") String witnessEmail2,
        @RequestParam("email3") String witnessEmail3,
        @RequestParam("email4") String witnessEmail4,
        Model model
    ) throws Exception {
        String[] fiveFactorsOfAuthentication = encryptionProvider.getFiveFactorsOfAuthentication();
        // todo send email to multiple people with each piece of the key
        model.addAttribute("ffa", fiveFactorsOfAuthentication);
        return "logging/viewFiveFactors"; // change to a confirmation screen after email
    }
    
    @GetMapping("enter-factors")
    public final String enterFiveFactors(){
        return "logging/enterFiveFactors";
    }
    
    @PostMapping("submit-auth")
    public final String processFiveFactors(
        @RequestParam("initKey") String initKey,
        @RequestParam("factor1") String factor1,
        @RequestParam("factor2") String factor2,
        @RequestParam("factor3") String factor3,
        @RequestParam("factor4") String factor4,
        Model model
    ) throws IOException {
        String[] ffa = new String[]{
            initKey.trim(), // form data may have white space
            factor1.trim(),
            factor2.trim(),
            factor3.trim(),
            factor4.trim()
        };
        // saves in the session
        currentUserEncKeys = FiveFactorAuthenticator.getEncryptionKeysFromFiveFactors(ffa);
        
        return "redirect:./list-website";
    }
    
    @GetMapping("list-website")
    public final String listWebsiteLogs(Model model) throws IOException{
        model.addAttribute("title", "Website Logs");
        model.addAttribute("logs", websiteLogFolder.getAllWebsiteLogNames());
        return "logging/listLogs";
    }
    
    @GetMapping("website")
    public final String viewWebsiteLog(
        @RequestParam("name") String name,
        Model model
    ){
        model.addAttribute("title", String.format("Website Log - %s", name));
        String contents = "failed to load log contents";
        try {
            if(currentUserEncKeys == null){
                throw new IllegalStateException("Cannot access website log before providing encryption keys");
            }
            Encrypter enc = new Encrypter(currentUserEncKeys);
            contents = websiteLogFolder.getLogByName(name).decryptContentsUsing(enc);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        model.addAttribute("text", contents);
        return "logging/view";
    }
    
    @GetMapping("personal")
    public final String viewCurrentUsersLog(Model model){
        UserEntity currentUser = loginService.getLoggedInUser();
        if(currentUser == null){
            throw new IllegalStateException("Cannot view personal log with no logged-in user");
        }
        String contents = String.format("Failed to get log for user %s", currentUser.getId());
        try {
            contents = this.logService.getLogForUser(currentUser).getText();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        model.addAttribute("title", String.format("User Log - %s", currentUser.getId()));
        model.addAttribute("text", contents);
        return "logging/view";
    }
}
