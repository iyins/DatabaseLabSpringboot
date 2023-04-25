package com.example.lab4database.controllers;

import com.example.lab4database.services.DatabaseOperations;
import com.example.lab4database.services.QueryFunctionsOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/operations")
public class OperationController {
    @Autowired
    private QueryFunctionsOperations functionsOperations;

    @GetMapping("")
    public String operations(@RequestParam String actions, Model model) {
        System.out.println(actions);

        String result = null;
        if (actions != null || actions != "") {
            result = functionsOperations.getResultForActions(actions);
        }

        model.addAttribute("result", result);
        return "operations";
    }
}
