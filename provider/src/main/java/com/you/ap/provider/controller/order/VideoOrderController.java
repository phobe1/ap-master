package com.you.ap.provider.controller.order;

import com.you.ap.domain.form.OrderTeacherScoreForm;
import com.you.ap.service.order.IVideoOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order/student")
public class VideoOrderController {

	@Autowired
	private IVideoOrderService teachOrderService;

	// 为订单评价
	@RequestMapping(value = "/markscore", method = RequestMethod.POST)
	@ResponseBody
	public Object commentTeacher(
			@RequestAttribute(value = "studentId", required = true) Integer studentId,
			OrderTeacherScoreForm form) {
		System.out.println(form);
		return teachOrderService.markScoreForVideoOrder(studentId,form);
	}

}
