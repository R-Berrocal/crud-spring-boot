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

import com.example.demo.Models.Server;
import com.example.demo.Repositories.ServerRepository;

@RestController
@RequestMapping(path = "/api")
public class ServerController {
    @Autowired
    private ServerRepository serverRepository;

    @RequestMapping(value = "/server", method = RequestMethod.POST)
    public ResponseEntity<Server> createServer(@RequestBody Server server) {
        Server serverCreate = this.serverRepository.save(server);
        return ResponseEntity.status(HttpStatus.CREATED).body(serverCreate);
    }

    @RequestMapping(value = "/server", method = RequestMethod.GET)
    public ResponseEntity<List<Server>> getServers() {
        List<Server> servers = (List<Server>) this.serverRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(servers);
    }

    @RequestMapping(value = "/server/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Server> updateServer(@PathVariable int id, @RequestBody Server serverUpdate) {
        Server server = this.serverRepository.findById(id).orElse(null);
        if (server == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if( serverUpdate.getDisk() != null) {
            server.setDisk(serverUpdate.getDisk());
        }
        if( serverUpdate.getIp()  != null) {
            server.setIp(serverUpdate.getIp());
        }
        if( serverUpdate.getLocation() != null) {
            server.setLocation(serverUpdate.getLocation());
        }
        if( serverUpdate.getMemory() != null) {
            server.setMemory(serverUpdate.getMemory());
        }
        if( serverUpdate.getName() != null) {
            server.setName(serverUpdate.getName());
        }
        if( serverUpdate.getProcessor() != null) {
            server.setProcessor(serverUpdate.getProcessor());
        }
        this.serverRepository.save(server);
        return ResponseEntity.status(HttpStatus.OK).body(server);
    }

    @RequestMapping(value = "/server/{id}", method = RequestMethod.GET)
    public ResponseEntity<Server> getServer(@PathVariable int id){
        Server server = this.serverRepository.findById(id).orElse(null);
        if(server == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(server);
    }

    @RequestMapping(value = "/server/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Server> deleteServer(@PathVariable int id){
        Server server = this.serverRepository.findById(id).orElse(null);
        if(server == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        this.serverRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(server);
    }
}

