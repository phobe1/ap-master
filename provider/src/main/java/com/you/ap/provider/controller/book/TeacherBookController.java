package com.you.ap.provider.controller.book;

import com.you.ap.domain.form.book.BookForm;
import com.you.ap.domain.vo.ApiResponse;
import com.you.ap.service.book.IBookService;
import com.you.ap.service.book.ITeacherBookService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/book/teacher")
public class TeacherBookController {

	@Autowired private IBookService bookService;
	
	@Autowired
	private ITeacherBookService iTeacherBookService;


	@RequestMapping(value = "/init/onemonthbookdaylist", method = RequestMethod.GET)
	@ResponseBody
	public Object initBooktime(
			@RequestAttribute(value = "teacherId") int teacherId
	) {
		bookService.autoAddBookDay(teacherId);
		return ApiResponse.buildSuccess("init success");
	}


	@RequestMapping(value = "/month/list.json", method = RequestMethod.GET)
	@ResponseBody
	public Object getBookDayList(
			@RequestAttribute(value = "teacherId") int teacherId
	) {
		return iTeacherBookService
				.getBookDayListByTeacherId(teacherId);
	}

	@RequestMapping(value = "/day/{bookDayId}/list.json", method = RequestMethod.GET)
	@ResponseBody
	public Object getBookDayDetailList(
			@RequestAttribute(value = "teacherId") int teacherId,
			@PathVariable(value = "bookDayId") int bookDayId
	) {
		return iTeacherBookService.getBookDayDetailList(bookDayId,teacherId);
	}



	@RequestMapping(value = "/publish/times", method = RequestMethod.POST)
	@ResponseBody
	public Object updateBookDayStatus(
			@RequestAttribute(value = "teacherId") Integer teacherId,
			BookForm bookForm) {
		if (bookForm==null || ! bookForm.validPublishHourBoxsParam()){
			return ApiResponse.buildFailure("参数不合法");
		}
		return iTeacherBookService
				.publishBookTimes(bookForm,teacherId);
	}


	
	

}
