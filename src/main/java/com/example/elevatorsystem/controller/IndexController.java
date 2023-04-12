package com.example.elevatorsystem.controller;

import com.example.elevatorsystem.enginev1.ElevatorsController;
import com.example.elevatorsystem.model.SimpleElevatorForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class IndexController {


    @GetMapping("/")
    public String index(Model model){

        return "index";
    }

    @GetMapping("/simple-elevator")
    public String simpleElevator(Model model){
        model.addAttribute("form", new SimpleElevatorForm(3,15,40,60));
        return "simple-elevator";
    }
    @PostMapping("/simple-elevator")
    public String simpleElevatorRun(Model model,
                                    @Valid @ModelAttribute("form") SimpleElevatorForm simpleElevatorForm,
                                    BindingResult bindingResult){


        ElevatorsController elevator = new ElevatorsController();
        if (bindingResult.hasErrors()){
            return "simple-elevator";
        }else {
            model.addAttribute("numberOfSteps", simpleElevatorForm.getDesiredSteps());
            model.addAttribute("summary", elevator.run2(simpleElevatorForm.getNumbersOfElevators(),
                    simpleElevatorForm.getGlobalFloors(),
                    simpleElevatorForm.getNumberOFRandomUsers(),
                    simpleElevatorForm.getDesiredSteps()));
            return "simple-elevator-run";
        }

    }
}
