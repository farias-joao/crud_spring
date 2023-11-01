package df.senac.br.spring_mongodb.controller;
import df.senac.br.spring_mongodb.model.Autor;
import df.senac.br.spring_mongodb.respositorio.AutorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class AutorController {

    @Autowired
    AutorRepositorio autorRepositorio;

    @GetMapping("/autor")
    public ResponseEntity<List<Autor>> getAllAutor(@RequestParam(required = false) String nome) {
        List<Autor> autores;
        try {
            autores = new ArrayList<>();
            if (nome == null) {
                autorRepositorio.findAll().forEach(autores::add);
            } else {
                autorRepositorio.findByNome(nome).forEach(autores::add);
            }

            if (autores.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(autores, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/autor/{id}")
    public ResponseEntity<Autor> getAutorById(@PathVariable("id") String id) {
        Optional<Autor> AutorData = autorRepositorio.findById(id);

        if (AutorData.isPresent()) {
            return new ResponseEntity<>(AutorData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/autor")
    public ResponseEntity<Autor> createAutor(@RequestBody Autor Autor) {
        try {
            Autor _Autor = autorRepositorio.save(new Autor(Autor.getNome(), Autor.getSobrenome(), Autor.getEmail(),
                    Autor.getTelefone(), Autor.getAtivo()));

            return new ResponseEntity<>(_Autor, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/autor/{id}")
    public ResponseEntity<Autor> updateAutor(@PathVariable("id") String id, @RequestBody Autor Autor) {
        Optional<Autor> AutorData = autorRepositorio.findById(id);

        if (AutorData.isPresent()) {
            Autor _Autor = AutorData.get();
            _Autor.setNome(Autor.getNome());
            _Autor.setSobrenome(Autor.getSobrenome());
            _Autor.setEmail(Autor.getEmail());
            _Autor.setTelefone(Autor.getTelefone());
            _Autor.setAtivo(Autor.getAtivo());
            return new ResponseEntity<>(autorRepositorio.save(_Autor), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/autor/{id}")
    public ResponseEntity<HttpStatus> deleteAutor(@PathVariable("id") String id) {
        try {
            autorRepositorio.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/autor")
    public ResponseEntity<HttpStatus> deleteAllAutor() {
        try {
            autorRepositorio.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/autor/ativo")
    public ResponseEntity<List<Autor>> findByAtivo() {
        try {
            List<Autor> Autores = autorRepositorio.findByAtivo(true);
            if (Autores.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(Autores, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
