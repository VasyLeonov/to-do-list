package main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import main.model.Affair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class AffairController {

    @Autowired
    private AffairRepository affairRepository;

    @PostMapping(path = "/affairs/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public int add(@RequestBody Affair affair) {
        Affair newAffair = affairRepository.save(affair);
        return newAffair.getId();
    }

    @GetMapping("/affairs/")
    public List<Affair> list() {
        Iterable<Affair> iterable = affairRepository.findAll();
        List<Affair> affairList = new ArrayList<>();
        for (Affair affair : iterable) {
            affairList.add(affair);
        }
        return affairList;
    }

    @GetMapping("/affairs/{id}")
    public ResponseEntity<Affair> getAffair(@PathVariable int id) {
        return getOptionalAffair(id)
                .map(affair -> new ResponseEntity<>(affair, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(null));
    }
    @PutMapping(path = "/affairs/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Affair> update(@RequestBody Affair newAffair, @PathVariable int id) {
        if (getOptionalAffair(id).isPresent()) {
            Affair affair = getOptionalAffair(id).get();
            affair.setName(newAffair.getName());
            affair.setTitle(newAffair.getTitle());
            affair.setAuthor(newAffair.getAuthor());
            affairRepository.save(affair);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @DeleteMapping("/affairs/{id}")
    public void delete(@PathVariable int id) {
        if (getOptionalAffair(id).isPresent()) {
            affairRepository.deleteById(id);
        }
    }

    public Optional<Affair> getOptionalAffair(int id) {
        return affairRepository.findById(id);
    }
}
