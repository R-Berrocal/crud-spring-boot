package com.example.software.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.software.Models.Aplication;
import com.example.software.Models.Server;
import com.example.software.Repositories.AplicationRepository;
import com.example.software.Repositories.AplicationSpecs;
import com.example.software.Repositories.ServerRepository;

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
        if (aplicationUpdate.getVersion() != null) {
            aplication.setVersion(aplicationUpdate.getVersion());
        }
        if (aplicationUpdate.getServer() != null) {
            aplication.setServer(aplicationUpdate.getServer());
        }
        this.aplicationRepository.save(aplication);
        return ResponseEntity.status(HttpStatus.OK).body(aplication);
    }

    @RequestMapping(value = "/aplication/{id}", method = RequestMethod.GET)
    public ResponseEntity<Aplication> getAplication(@PathVariable int id) {
        Aplication aplication = this.aplicationRepository.findById(id).orElse(null);
        if (aplication == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(aplication);
    }

    @RequestMapping(value = "/aplication/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Aplication> deleteAplication(@PathVariable int id) {
        Aplication aplication = this.aplicationRepository.findById(id).orElse(null);
        if (aplication == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        this.aplicationRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(aplication);
    }

    @RequestMapping(value = "/aplicationByServerOs/{os}", method = RequestMethod.GET)
    public ResponseEntity<List<Aplication>> getAplicationByServerOs(@PathVariable String os) {
        List<Aplication> aplications = (List<Aplication>) this.aplicationRepository
                .findAll(AplicationSpecs.aplicationByServerOs(os));
        return ResponseEntity.status(HttpStatus.OK).body(aplications);
    }

    @RequestMapping(value = "/aplicationByName/{name}", method = RequestMethod.GET)
    public ResponseEntity<Aplication> getAplicationName(@PathVariable String name) {
        Aplication aplication = this.aplicationRepository
                .findOne(AplicationSpecs.aplicationByName(name)).orElse(null);

        if (aplication == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(aplication);
    }




    @RequestMapping(value = "/aplications", method = RequestMethod.GET)
    public ResponseEntity<List<Aplication>>GetAplicationByNameAndByServerSO(
        
    @RequestParam(value = "name", required = false) String name,
    @RequestParam(value = "so", required = false) String so){

        Specification <Aplication> aplicationSpecs = Specification.where(null);

        if(name != null){
            aplicationSpecs = AplicationSpecs.aplicationByName(name);
        }
        if(so != null){
            aplicationSpecs = AplicationSpecs.aplicationByServerOs(so);
        }
        List<Aplication> aplications = (List<Aplication>) this.aplicationRepository.findAll(aplicationSpecs);
        if (name != null && so != null) {
            aplications = aplications.stream()
                    .filter(application -> application.getName().equalsIgnoreCase(name)
                            && application.getServer().getOs().equalsIgnoreCase(so))
                    .collect(Collectors.toList());
        }
        return ResponseEntity.status(HttpStatus.OK).body(aplications);
    }

}
