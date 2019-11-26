package com.pulawskk.bettingsite.controllers;

import com.pulawskk.bettingsite.models.Selection;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/settler")
@SessionAttributes("selections")
public class BetPlacementController {

    @PostMapping("/bet")
    public String displayOddValue(@ModelAttribute Selection s, Model model) {
        List<Selection> selections = (List<Selection>) model.getAttribute("selections");
        if(selections == null) {
            selections = new ArrayList<>();
        }
        selections.add(s);
        model.addAttribute("selections", selections);
        selections.forEach(System.out::println);
        System.out.println(selections.size());
        return "redirect:/events/football";
    }
}
