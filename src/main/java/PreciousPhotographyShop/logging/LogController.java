package PreciousPhotographyShop.logging;

import PreciousPhotographyShop.logging.encryption.EncryptionKeys;
import PreciousPhotographyShop.logging.encryption.EncryptionProvider;
import PreciousPhotographyShop.logging.logs.WebsiteLog;
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
    
    @Resource
    private EncryptionKeys currentUserEncKeys;
    
    private final WebsiteLog websiteLog;
    
    public LogController(WebsiteLog websiteLog){
        this.websiteLog = websiteLog;
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
        Model model
    ) throws IOException {
        // todo save five factors, keep forwarding to subsequent requests
        
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
            contents = websiteLog.getText();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        model.addAttribute("text", contents);
        return "logging/view";
    }
}
