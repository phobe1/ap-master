package com.you.ap.service.teachcourse.imp;

import com.google.common.collect.Lists;

import com.you.ap.dao.course.ITeachCourseDAO;
import com.you.ap.domain.form.OnlineTCForm;
import com.you.ap.domain.schema.OnlineTCInfoDO;
import com.you.ap.service.book.imp.BookLocationServiceImp;
import com.you.ap.service.teachcourse.IStudentTeachCourseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentTeachCourseServiceImp implements IStudentTeachCourseService {

	private Logger logger= Logger.getLogger(BookLocationServiceImp.class);

	@Autowired private ITeachCourseDAO teachCourseDao;

	
	@Override
	public List<OnlineTCInfoDO> getOnlineTCListByCourse(OnlineTCForm onlineTCForm) {
		List<OnlineTCInfoDO> result = Lists.newArrayList();
		if(onlineTCForm.getCourseId() ==0){
			return result;
		}
		return teachCourseDao.getOnlineListBySubject(onlineTCForm);
	}
	



	
	
}
