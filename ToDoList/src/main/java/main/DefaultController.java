package main;

import main.model.Affair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DefaultController {

    @Autowired
    AffairRepository affairRepository;

    @RequestMapping("/")
    public String index(Model model) {
        Iterable<Affair> affairIterable = affairRepository.findAll();
        List<Affair> affairs = new ArrayList<>();
        for (Affair affair : affairIterable) {
            affairs.add(affair);
        }
        model.addAttribute("affairs", affairs);
        model.addAttribute("affairCount", affairs.size());
        return "index";
    }
}
