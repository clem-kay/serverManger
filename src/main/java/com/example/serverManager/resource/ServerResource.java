package com.example.serverManager.resource;


import com.example.serverManager.model.Response;
import com.example.serverManager.model.Server;
import com.example.serverManager.service.impl.ServerServiceImpl;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


import static com.example.serverManager.enumeration.Status.SERVER_UP;
import static java.time.LocalDateTime.now;
import static java.util.Map.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/server")
@AllArgsConstructor
public class ServerResource {

    private final ServerServiceImpl serverService;

    @GetMapping("/list")
    public ResponseEntity<Response> getServers() {
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(of("servers", serverService.list(30)))
                .message("Servers Retrived")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {
        Server server = serverService.ping(ipAddress);
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(of("server", server))
                .message(server.getStatus() == SERVER_UP ? "Ping Sucess" : "Ping fail")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

    @PostMapping("/save")
    public ResponseEntity<Response> pingServer(@RequestBody @Valid Server server) {

        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(of("server", serverService.create(server)))
                .message("server created")
                .status(CREATED)
                .statusCode(CREATED.value())
                .build()
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getServer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(of("server", serverService.get(id)))
                .message("server retrieved")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteServer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(of("deleted", serverService.delete(id)))
                .message("server deleted")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

    @GetMapping(path = "/image/{fileName}", produces = IMAGE_PNG_VALUE)
    public byte[] getServerimage(@PathVariable("fileName") String fileName) throws IOException {
      return Files.readAllBytes(Paths.get(System.getProperty("user.home")+"/Downloads/images/"+fileName));
    }


}
