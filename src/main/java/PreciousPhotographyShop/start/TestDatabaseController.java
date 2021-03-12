package PreciousPhotographyShop.start;

import PreciousPhotographyShop.databaseRepositories.TestRepository;
import PreciousPhotographyShop.model.TestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Matt
 */

@Controller
@RequestMapping(path="/testdb") // append /testdb to all paths this lists
public class TestDatabaseController {
    @Autowired // Spring auto-generates this
    private TestRepository testRepository;
    
    @PostMapping(path="/create") // Post, not get
    public @ResponseBody String createRecord(@RequestParam String message){
        // @ResponseBody means "I'm not returning the name of a view"
        TestEntity te = new TestEntity();
        te.setMessage(message);
        testRepository.save(te);
        return testRepository.toString();
    }
    
    @GetMapping(path="/list")
    public @ResponseBody Iterable<TestEntity> getAll(){
        return testRepository.findAll();
    }
}
