package PreciousPhotographyShop.logging;

import PreciousPhotographyShop.logging.encryption.EncryptionProvider;
import PreciousPhotographyShop.logging.logs.WebsiteLog;
import java.io.IOException;
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
    
    private final WebsiteLog websiteLog;
    
    public LogController(WebsiteLog websiteLog){
        this.websiteLog = websiteLog;
    }
    
    @GetMapping("request-access")
    public final String reqAccess(
        @RequestParam(name="code", required=false) String code,
        Model model
    ){
        if(code != null){
            model.addAttribute("code", code);
        }
        return "requestLogAccess";
    }
    
    @PostMapping("request-access")
    public final String processAccessRequest(
        @RequestParam("email") String email,
        Model model
    ) throws Exception {
        String[] fiveFactorsOfAuthentication = EncryptionProvider.getFiveFactorsOfAuthentication();
        // todo send email to multiple people with each piece of the key
        model.addAttribute("ffa", fiveFactorsOfAuthentication);
        return "viewFiveFactors";
    }
    
    @GetMapping("enter-factors")
    public final String enterFiveFactors(
    
    ){
        return "enterFiveFactors";
    }
    
    @PostMapping("submit-auth")
    public final String processFiveFactors(
        Model model
    ) throws IOException {
        // todo save five factors, keep forwarding to subsequent requests
        model.addAttribute("logs", websiteLog.getAllWebsiteLogsNames());
        return "listLogs";
    }
}
