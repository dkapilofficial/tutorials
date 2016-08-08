package org.baeldung.web.rest;

import org.baeldung.web.service.StudentService;
import org.baeldung.web.vo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestController
public class StudentDirectoryRestController {

	@Autowired
	private StudentService service;

	@RequestMapping(value = "/student/get", params = { "page", "size" }, method = RequestMethod.GET, produces = "application/json")
	public Page<Student> findPaginated(@RequestParam("page") int page, @RequestParam("size") int size){

		Page<Student> resultPage = service.findPaginated(page, size);

		return resultPage;
	}

}
