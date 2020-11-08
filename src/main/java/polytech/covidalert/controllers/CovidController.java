package polytech.covidalert.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import polytech.covidalert.models.Covid;
import polytech.covidalert.models.CovidRepository;

import java.util.List;

@RestController
@RequestMapping("/covidalert/api/covid")
public class CovidController {
    @Autowired
    private CovidRepository covidRepository;

    @GetMapping
    public List<Covid> listCovid() {
        return covidRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Covid get(@PathVariable Long id) {
        if (! covidRepository.findById(id).isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Covid with ID " +id+ " not found.");
        }
        return covidRepository.getOne(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Covid create(@RequestBody final Covid covid) {
        return covidRepository.saveAndFlush(covid);
    }
}