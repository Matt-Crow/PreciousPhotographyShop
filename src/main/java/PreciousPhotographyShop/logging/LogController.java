package PreciousPhotographyShop.logging;

import PreciousPhotographyShop.logging.encryption.Encrypter;
import PreciousPhotographyShop.logging.encryption.EncryptionKeys;
import PreciousPhotographyShop.logging.encryption.EncryptionProvider;
import PreciousPhotographyShop.logging.encryption.FiveFactorAuthenticator;
import PreciousPhotographyShop.logging.logs.WebsiteLog;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;
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
    
    @Resource
    private EncryptionKeys currentUserEncKeys;
    
    private final WebsiteLog websiteLog;
    
    public LogController(WebsiteLog websiteLog){
        this.websiteLog = websiteLog;
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
        String[] fiveFactorsOfAuthentication = EncryptionProvider.getFiveFactorsOfAuthentication();
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
        currentUserEncKeys = FiveFactorAuthenticator.getEncryptionKeysFromFiveFactors(ffa);
        
        model.addAttribute("title", "Website Logs");
        model.addAttribute("logs", websiteLog.getAllWebsiteLogsNames());
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
            contents = Arrays.stream(websiteLog.getText().split("\n")).map((line)->{
                String dec = null;
                try {
                    // need to decrypt line by line, as the \n is NOT encrypted in the log file
                    dec = enc.decrypt(line);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return dec;
            }).filter((dec)->dec != null).collect(Collectors.joining("<br/>"));
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        model.addAttribute("text", contents);
        return "logging/view";
    }
}
