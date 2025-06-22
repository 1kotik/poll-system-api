package by.kotik.pollservice.controller;

import by.kotik.pollservice.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/polls")
public class TagController {
    @Autowired
    private TagService tagService;
    
}
