package polytech.covidalert.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import polytech.covidalert.models.*;

import java.util.List;

@RestController
@RequestMapping("/covidalert/api/do-test")
public class DoTestController {
    @Autowired
    private DoTestRepository doTestRepositoryRepository;

    @GetMapping
    public List<DoTest> listDoTest() {
        return doTestRepositoryRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public DoTest get(@PathVariable Long id) {
        if (! doTestRepositoryRepository.findById(id).isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "DoTest with ID " +id+ " not found.");
        }
        return doTestRepositoryRepository.getOne(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DoTest create(@RequestBody final DoTest doTest) {
        return doTestRepositoryRepository.saveAndFlush(doTest);
    }
}