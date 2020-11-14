package polytech.covidalert.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import polytech.covidalert.exception.FormNotCompletedException;
import polytech.covidalert.exception.ResourceAlreadyExistsException;
import polytech.covidalert.models.HaveCovid;
import polytech.covidalert.models.HaveCovidRepository;

import java.util.List;
import java.util.Optional;

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

    @GetMapping
    @RequestMapping("/list/{userId}")
    public List<HaveCovid> getHaveCovidByUserId(@PathVariable Long id) {
        List<HaveCovid> haveCovidList = haveCovidRepository.findAllByUserId(id);
        if (haveCovidList.size() == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "l'user " +id+ " n'a jamais eu le covid");
        }
        return haveCovidList;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HaveCovid create(@RequestBody final HaveCovid haveCovid) {
        return haveCovidRepository.saveAndFlush(haveCovid);
    }

    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        haveCovidRepository.deleteById(id);
    }

    @RequestMapping(value="{id}",method = RequestMethod.PUT)
    public HaveCovid update(@PathVariable Long id, @RequestBody HaveCovid haveCovid) {
        // TODO: Ajouter ici une validation si tous
        // les champs ont ete passes
        if (id !=null && haveCovid.getCovid_date_begin()!=null &&
                haveCovid.getCovid_duration()>=0) {
            // Sinon, retourner une erreur 400 (Bad Payload)
            Optional<HaveCovid> existingHaveCovid = haveCovidRepository.findById(id);
            if (existingHaveCovid.isPresent()){
                HaveCovid haveCovidBD = existingHaveCovid.get();
                BeanUtils.copyProperties(haveCovid, haveCovidBD, "have_covid_id");
                return haveCovidRepository.saveAndFlush(haveCovidBD);
            }

            throw new ResourceAlreadyExistsException(HttpStatus.NOT_FOUND, "haveCovid with ID " + haveCovid.getHave_covid_id()+ " not found.");
        }
        else{
            throw new FormNotCompletedException(HttpStatus.NOT_FOUND, "Form not fully completed");
        }
    }
}