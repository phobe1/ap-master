package com.you.ap.provider.controller.teachcourse;

import com.you.ap.domain.enums.course.TeachCourseStatusEnum;
import com.you.ap.domain.vo.ApiResponse;
import com.you.ap.service.teachcourse.ITeacherTeachCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/teachcourse/teacher")
public class TeacherTeachCourseController {
	
	@Autowired private ITeacherTeachCourseService teacherTeachCourseService;

	@RequestMapping(value="/apply.do",method = RequestMethod.POST)
	@ResponseBody
	public ApiResponse applyToTeach(
			@RequestAttribute("teacherId") int teacherId,
			@RequestParam("courseId") int teachCourseId){
		return ApiResponse.buildSuccess(teacherTeachCourseService.applyTeachCourse(teacherId, teachCourseId));
	}
	
	@RequestMapping(value="/all.json",method = RequestMethod.GET)
	@ResponseBody
	public ApiResponse getTCListByTeacher(
			@RequestAttribute("teacherId") int teacherId){
	    return teacherTeachCourseService.getMyTeachCourseList(teacherId);
	}


	
	@RequestMapping(value="/prepared.do",method = RequestMethod.GET)
	@ResponseBody
	public ApiResponse preparedToTeach(
			@RequestAttribute("teacherId") int teacherId,
			@RequestParam("teachCourseIds") String teachCourseIds){
		return teacherTeachCourseService.updateStatus(teacherId, teachCourseIds, TeachCourseStatusEnum.NORMAL);
	}
	
	@RequestMapping(value="/close.do",method = RequestMethod.GET)
	@ResponseBody
	public ApiResponse closeToTeach(
			@RequestAttribute("teacherId") int teacherId,
			@RequestParam("teachCourseIds") String teachCourseIds){
		return  teacherTeachCourseService.updateStatus(teacherId, teachCourseIds,TeachCourseStatusEnum.CLOSE);
	}

	@RequestMapping(value="/delete/{id}",method = RequestMethod.GET)
	@ResponseBody
	public ApiResponse deleteTeachCourse(
			@RequestAttribute("teacherId") int teacherId,
			@PathVariable("id") int id){
		return  teacherTeachCourseService.deleteTeachCourse(id, teacherId);
	}

}
