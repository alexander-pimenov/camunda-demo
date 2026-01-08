package ru.pim.camunda_demo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.pim.camunda_demo.service.DmnServiceImpl;

import java.time.LocalDate;

@RestController
@RequestMapping(value = "/dmn")
public class DmnController {

    private final DmnServiceImpl dmnService;

    @Autowired
    public DmnController(DmnServiceImpl dmnService) {
        this.dmnService = dmnService;
    }

    @RequestMapping(value = "/{birth}", method = RequestMethod.GET)
    @ResponseBody
    public Boolean validate(
            @PathVariable @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) LocalDate birth) {
        return dmnService.validateEfterlonFleksydelseAge(birth);
    }

}
