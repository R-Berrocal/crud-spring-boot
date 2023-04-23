package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Models.Aplication;
import com.example.demo.Models.Server;
import com.example.demo.Repositories.AplicationRepository;
import com.example.demo.Repositories.ServerRepository;

@RestController
@RequestMapping(path = "/api")
public class AplicationController {
    @Autowired
    private AplicationRepository aplicationRepository;

    @Autowired
    private ServerRepository serverRepository;

    @RequestMapping(value = "/aplication", method = RequestMethod.POST)
    public ResponseEntity<Aplication> createApplication(@RequestBody Aplication application) {
        Server server = serverRepository.findById(application.getServer().getId()).orElse(null);

        if (server == null) {
            throw new Error("server with id: " + application.getServer().getId() + " not found");
        }
        application.setServer(server);
        Aplication newApplication = aplicationRepository.save(application);
        return ResponseEntity.status(HttpStatus.CREATED).body(newApplication);
    }

    @RequestMapping(value = "/aplication", method = RequestMethod.GET)
    public ResponseEntity<List<Aplication>> getAplication() {
        List<Aplication> aplications = (List<Aplication>) this.aplicationRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(aplications);
    }

    @RequestMapping(value = "/aplication/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Aplication> updateAplication(@PathVariable int id, @RequestBody Aplication aplicationUpdate) {
        Aplication aplication = this.aplicationRepository.findById(id).orElse(null);
        if (aplication == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (aplicationUpdate.getName() != null) {
            aplication.setName(aplicationUpdate.getName());
        }
        if (aplicationUpdate.getServer() != null) {
            aplication.setServer(aplicationUpdate.getServer());
        }
        this.aplicationRepository.save(aplication);
        return ResponseEntity.status(HttpStatus.OK).body(aplication);
    }

    @RequestMapping(value = "/aplication/{id}", method = RequestMethod.GET)
    public ResponseEntity<Aplication> getAplication(@PathVariable int id){
        Aplication aplication = this.aplicationRepository.findById(id).orElse(null);
        if(aplication == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(aplication);
    }

    @RequestMapping(value = "/aplication/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Aplication> deleteAplication(@PathVariable int id){
        Aplication aplication = this.aplicationRepository.findById(id).orElse(null);
        if(aplication == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        this.aplicationRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(aplication);
    }
}
