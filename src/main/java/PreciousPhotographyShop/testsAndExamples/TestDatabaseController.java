package PreciousPhotographyShop.testsAndExamples;

import PreciousPhotographyShop.testsAndExamples.TestRepository;
import PreciousPhotographyShop.testsAndExamples.TestEntity;
import java.util.HashSet;
import java.util.Set;
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
    
    @Autowired private ARepo aRepo;
    @Autowired private BRepo bRepo;
    
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
    
    @GetMapping(path="/map")
    public @ResponseBody Iterable<EntityA> testMapping(){
        aRepo.deleteAll();
        bRepo.deleteAll();
        
        EntityA[] as = new EntityA[2];
        EntityB[] bs = new EntityB[2];
        for(int i = 0; i < 2; i++){
            as[i] = new EntityA();
            as[i] = aRepo.save(as[i]);
            bs[i] = new EntityB();
            bs[i] = bRepo.save(bs[i]);
        }
        Set<String> ids = new HashSet<>();
        ids.add(as[0].getId());
        ids.add(as[1].getId());
        bs[0].setAIds(ids);
        bs[0] = bRepo.save(bs[0]);
        
        ids = new HashSet<>();
        ids.add(as[1].getId());
        bs[1].setAIds(ids);
        bs[1] = bRepo.save(bs[1]);
        
        aRepo.findAll().forEach(System.out::println);
        bRepo.findAll().forEach(System.out::println);
        
        return aRepo.findAll();
    }
    
    @GetMapping(path="/main")
    public @ResponseBody Iterable<TestEntity> test(){
        /*
        TestEntity te1;
        TestEntity te2;
        TestEntity te3;
        Set<String> s1;
        Set<String> s2;
        Set<String> s3;
        
        te1 = new TestEntity();
        te1.setMessage("Message 1");
        te1 = testRepository.save(te1);
        
        te2 = new TestEntity();
        te2.setMessage("Message 2");
        te2 = testRepository.save(te2);
        
        te3 = new TestEntity();
        te3.setMessage("Message 3");
        te3 = testRepository.save(te3);
        
        s1 = new HashSet<>();
        s1.add(te2.getId());
        te1.setRelatedIds(s1);
        te1 = testRepository.save(te1);
        
        s2 = new HashSet<>();
        s2.add(te1.getId());
        s2.add(te3.getId());
        te2.setRelatedIds(s2);
        te2 = testRepository.save(te2);
        
        s3 = new HashSet<>();
        s3.add(te1.getId());
        te3.setRelatedIds(s3);
        te3 = testRepository.save(te3);
        */
        System.out.println(testRepository.findAllByRelatedId("4028818b7885045d017885048b9a0000").size());
        
        return getAll();
    }
}
