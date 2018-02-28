package com.you.ap.provider.controller.teachcourse;

import com.you.ap.domain.form.OnlineTCForm;
import com.you.ap.domain.schema.OnlineTCInfoDO;
import com.you.ap.domain.vo.ApiResponse;
import com.you.ap.service.teachcourse.IStudentCollectionService;
import com.you.ap.service.teachcourse.IStudentTeachCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/teachcourse/student")
public class StudentTeachCourseController {
	
	@Autowired private IStudentTeachCourseService studentTeachCourseService;

	@Autowired private IStudentCollectionService studentCollectionService;
	
	@RequestMapping(value="/search/list.json",method = RequestMethod.GET)
	@ResponseBody
	public ApiResponse searchOnlineTeachers(
			@RequestAttribute("studentId") int studentId,
			@RequestParam(value="courseId",required=true) int courseId,
			@RequestParam(value="orderType",required=false,defaultValue="2") int orderType,
			@RequestParam(value="index",required=false,defaultValue="0") int index,
			@RequestParam(value="limit",required=false,defaultValue="10") int limit){
		List<OnlineTCInfoDO> list = studentTeachCourseService.getOnlineTCListByCourse(
				new OnlineTCForm(studentId,courseId,orderType,index,limit));
	    return ApiResponse.buildSuccess(list);
	}
	
	@RequestMapping(value="/collect/{teacherId}",method = RequestMethod.GET)
	@ResponseBody
	public ApiResponse saveTeachCourse(
			@RequestAttribute(value="studentId",required=true)  Integer studentId,
			@PathVariable(value="teacherId") int teacherId){
		return studentCollectionService.collect(studentId, teacherId);
	}
	
	@RequestMapping(value="/collect/delete/{teacherId}",method = RequestMethod.GET)
	@ResponseBody
	public ApiResponse deleteSaveTeachCourseById(
			@RequestAttribute(value= "studentId")  int studentId,
			@PathVariable int teacherId
			){
		return studentCollectionService.deleteById(studentId,teacherId);
	}
	
	@RequestMapping(value="/save/list.json",method = RequestMethod.GET)
	@ResponseBody
	public ApiResponse saveList(
			@RequestAttribute(value= "studentId")  int studentId,
			@RequestParam(value="index",required=false,defaultValue="0") int index,
			@RequestParam(value="limit",required=false,defaultValue="10") int limit){
		return studentCollectionService.getCollectionListByStudent(studentId, index, limit);
	}
	
	

}
