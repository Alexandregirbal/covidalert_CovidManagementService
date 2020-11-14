package polytech.covidalert.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import polytech.covidalert.exception.FormNotCompletedException;
import polytech.covidalert.exception.ResourceAlreadyExistsException;
import polytech.covidalert.exception.ResourceNotFoundException;
import polytech.covidalert.models.Covid;
import polytech.covidalert.models.CovidRepository;

import java.util.List;
import java.util.Optional;

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
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Covid with ID " +id+ " not found.");
        }
        return covidRepository.getOne(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Covid create(@RequestBody final Covid covid) {
        if (! covidRepository.findById(covid.getCovid_id()).isPresent()){
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Covid with ID " +covid.getCovid_id()+ " not found.");
        }
        return covidRepository.saveAndFlush(covid);
    }

    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        // FAIRE UNE REQUETE VERS CHAQUE SERVICE POUR SUPPRIMERS les JOINs
        covidRepository.deleteById(id);
    }

    @RequestMapping(value="{id}",method = RequestMethod.PUT)
    public Covid update(@PathVariable Long id, @RequestBody Covid covid) {
        // TODO: Ajouter ici une validation si tous
        // les champs ont ete passes
        if (id !=null && covid.getDetail()!=null &&
                covid.getForme()!=null) {
            // Sinon, retourner une erreur 400 (Bad Payload)
            Optional<Covid> existingCovid = covidRepository.findById(id);
            if (existingCovid.isPresent()){
                Covid covidBD = existingCovid.get();
                BeanUtils.copyProperties(covid, covidBD, "covid_id");
                return covidRepository.saveAndFlush(covidBD);
            }

            throw new ResourceAlreadyExistsException(HttpStatus.NOT_FOUND, "Covid with ID " +covid.getCovid_id()+ " not found.");
        }
        else{
            throw new FormNotCompletedException(HttpStatus.NOT_FOUND, "Form not fully completed");
        }
    }
}