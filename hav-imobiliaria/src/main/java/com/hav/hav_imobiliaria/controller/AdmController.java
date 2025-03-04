package com.hav.hav_imobiliaria.controller;

import com.hav.hav_imobiliaria.service.AdmService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/adm")
@AllArgsConstructor
public class AdmController {

    private AdmService service;


}
