package PreciousPhotographyShop.start;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Matt
 */

@RestController
public class TestController {
    @RequestMapping("/")
	public String index() {
		return "If you can get this far, the project works!";
	}
}
