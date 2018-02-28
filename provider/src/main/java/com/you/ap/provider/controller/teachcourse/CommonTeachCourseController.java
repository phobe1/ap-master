package com.you.ap.provider.controller.teachcourse;

import com.you.ap.domain.vo.ApiResponse;
import com.you.ap.service.teachcourse.ICourseService;
import com.you.ap.service.teachcourse.ITeachCourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/teachcourse/common")
public class CommonTeachCourseController {

	Logger logger = LoggerFactory.getLogger(CommonTeachCourseController.class);

	public CommonTeachCourseController(){
		
	}
	
	@Autowired private ITeachCourseService tService;
	@Autowired private ICourseService courseService;


	@RequestMapping(value="/allcourses.json",method = RequestMethod.GET)
	@ResponseBody
	public ApiResponse searchAllCourses(){
		return courseService.getAllCourseList();
	}


	
	@RequestMapping(value="/getTeachCourse/{teachCourseId}",method = RequestMethod.GET)
	@ResponseBody
	public ApiResponse getTCById(
			@PathVariable int teachCourseId
			){
		return tService.getDetailVOById(teachCourseId);
	}	
	
	
	
	
	

}
