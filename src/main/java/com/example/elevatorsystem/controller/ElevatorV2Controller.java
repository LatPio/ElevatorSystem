package com.example.elevatorsystem.controller;

import com.example.elevatorsystem.enginev1.ElevatorUserWithStartPoint;
import com.example.elevatorsystem.enginev1.ElevatorsController;
import com.example.elevatorsystem.model.ElevatorUsersList;
import com.example.elevatorsystem.model.SimpleV2ElevatorForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("elevator")
public class ElevatorV2Controller {

    private ElevatorUsersList usersList;

    public ElevatorV2Controller(ElevatorUsersList usersList) {
        this.usersList = usersList;
    }

    @GetMapping()
    public String showFormUsers(Model model){
        model.addAttribute("usersList", usersList);
        model.addAttribute("elevatorUser", new ElevatorUserWithStartPoint(1,0,5,0));

        return "elevator";
    }
    @GetMapping("/deleteUsers")
    public String clearUsers(){
        usersList.clear();

        return "redirect:/elevator";
    }
    @PostMapping()
    public String addUsers(@ModelAttribute ElevatorUserWithStartPoint user){
        usersList.add(user);
        return "redirect:/elevator";
    }
    @GetMapping("/run")
    public String showForm(Model model){
        model.addAttribute("usersList", usersList);
        model.addAttribute("form", new SimpleV2ElevatorForm(1,10,usersList,10));

        return "elevator-build";
    }

    @PostMapping("/run")
    public String add(@Valid @ModelAttribute("form") SimpleV2ElevatorForm simpleV2ElevatorForm, BindingResult bindingResult, Model model){
        ElevatorsController elevator = new ElevatorsController();
//        if (bindingResult.hasErrors()){
//            return "redirect:/elevator/run";
//        } else {
//            model.addAttribute("numberOfSteps", simpleV2ElevatorForm.getDesiredSteps());
//            model.addAttribute("summary", elevator.run(simpleV2ElevatorForm.getNumbersOfElevators(),
//                    simpleV2ElevatorForm.getGlobalFloors(),
//                    simpleV2ElevatorForm.getUsers(),
//                    simpleV2ElevatorForm.getDesiredSteps()));
//            return "elevator-run";
//        }
        model.addAttribute("numberOfSteps", simpleV2ElevatorForm.getDesiredSteps());
        model.addAttribute("summary", elevator.run(simpleV2ElevatorForm.getNumbersOfElevators(),
                simpleV2ElevatorForm.getGlobalFloors(),
                usersList,
                simpleV2ElevatorForm.getDesiredSteps()));
        return "elevator-run";
    }
}
