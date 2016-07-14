
/**
 * @author Prashant Dutta
 *
 */
package com.baledung.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/test")
public class TestController{

	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView getTestData()
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("welcome");
		mv.getModel().put("data", "Welcome home man");
		
		return mv;
	}
}