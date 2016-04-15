package com.example;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class IndexController {

	@RequestMapping(method=RequestMethod.GET)
	public ResourceSupport index() {
		ResourceSupport index = new ResourceSupport();
		index.add(linkTo(MyRestController.class).withRel("notes"));
		index.add(linkTo(MyRestController.class).withRel("tags"));
		return index;
	}

}