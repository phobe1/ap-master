package com.you.ap.provider.controller.book;

import com.you.ap.domain.form.book.BookForm;
import com.you.ap.domain.vo.ApiResponse;
import com.you.ap.service.book.IBookLocationService;
import com.you.ap.service.book.IBookService;
import com.you.ap.service.book.IStudentBookService;
import com.you.ap.service.teachcourse.ITeachCourseService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/book/student")
public class StudentBookController {

	@Autowired
	private IStudentBookService iStudentBookService;

	@RequestMapping(value = "/teacher/{teacherId}/bookinfo.json", method = RequestMethod.GET)
	@ResponseBody
	public Object getTeacherCourses(
			@RequestAttribute("studentId") int studentId,
			@PathVariable(value = "teacherId") int teacherId) {
		return iStudentBookService.getTeacherBookInfoByTeacherId(teacherId);
	}



	// 返回某月该老师课程的所有预约日期列表--纬度 天。
	@RequestMapping(value = "/month/list.json", method = RequestMethod.GET)
	@ResponseBody
	public Object getBookDayList(
			@RequestAttribute("studentId") int studentId,
			@RequestParam(value = "teacherId") int teacherId,
			@RequestParam("locationId") int locationId
			) {
		return iStudentBookService
				.getBookDayListByTeacherId(teacherId,locationId);
	}

	// 返回某天该老师课程的所有详细预约时间列表--纬度 time。
	@RequestMapping(value = "/day/{bookDayId}/list.json", method = RequestMethod.GET)
	@ResponseBody
	public Object getBookTimeList(
			@RequestAttribute("studentId") int studentId,
			@PathVariable(value = "bookDayId") int bookDayId,
			@RequestParam("teacherId") int teacherId
	) {
		return iStudentBookService.getBookDayDetailList(bookDayId,teacherId);
	}

	// 预约时间
	@RequestMapping(value = "/day/{bookDayId}/book.do", method = RequestMethod.POST)
	@ResponseBody
	public Object book(
			@RequestAttribute(value = "studentId") Integer studentId,
			BookForm bookForm) {
		if (bookForm==null || !bookForm.validBookParam()){
		    return ApiResponse.buildFailure("lost param");
		}
		return iStudentBookService
				.book(bookForm, studentId);
	}

//	// 查看我的预约
//	@RequestMapping(value = "/booklist.json", method = RequestMethod.GET)
//	@ResponseBody
//	public Object studentBookList(
//			@RequestAttribute(value = "studentId", required = true) Integer studentId) {
//		return iBookingService.getStudentBookingList(studentId);
//	}
//
//	// 查看我的某一个预约
//	@RequestMapping(value = "/book/{id}", method = RequestMethod.GET)
//	@ResponseBody
//	public Object showBook(
//			@SessionAttribute(value = "studentId", required = true) Integer studentId,
//			@PathVariable("id") int id) {
//		return iBookingService.getBookTimeById(id);
//	}
//
//	// 取消book
//	@RequestMapping(value = "/cancel.do", method = RequestMethod.GET)
//	@ResponseBody
//	public Object cancelBookBeforePay(
//			@SessionAttribute(value = "studentId", required = true) Integer studentId,
//			@RequestParam(value = "bookId", required = true) int bookId) {
//		return iBookingService.cancelBook(studentId, bookId);
//	}

}
