package com.hav.hav_imobiliaria.controller;

import com.hav.hav_imobiliaria.model.entity.Property;
import com.hav.hav_imobiliaria.model.entity.User;
import com.hav.hav_imobiliaria.service.PropertyService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/property")
@AllArgsConstructor
public class PropertyController {



}
