package polytech.covidalert.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import polytech.covidalert.models.Test;
import polytech.covidalert.models.TestRepository;

import java.util.List;

@RestController
@RequestMapping("/covidalert/api/test")
public class TestController {
    @Autowired
    private TestRepository testRepository;

    @GetMapping
    public List<Test> listTest() {
        return testRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Test get(@PathVariable Long id) {
        if (! testRepository.findById(id).isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "test with ID " +id+ " not found.");
        }
        return testRepository.getOne(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Test create(@RequestBody final Test test) {
        return testRepository.saveAndFlush(test);
    }
}