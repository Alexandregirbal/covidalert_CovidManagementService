package polytech.covidalert.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import polytech.covidalert.models.Covid;
import polytech.covidalert.models.CovidRepository;
import polytech.covidalert.models.HaveCovid;
import polytech.covidalert.models.HaveCovidRepository;

import java.util.List;

@RestController
@RequestMapping("/covidalert/api/have-covid")
public class HaveCovidController {
    @Autowired
    private HaveCovidRepository haveCovidRepository;

    @GetMapping
    public List<HaveCovid> listHaveCovid() {
        return haveCovidRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public HaveCovid get(@PathVariable Long id) {
        if (! haveCovidRepository.findById(id).isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "HaveCovid with ID " +id+ " not found.");
        }
        return haveCovidRepository.getOne(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HaveCovid create(@RequestBody final HaveCovid haveCovid) {
        return haveCovidRepository.saveAndFlush(haveCovid);
    }
}