package com.you.ap.service.teachcourse;


import com.you.ap.domain.form.OnlineTCForm;
import com.you.ap.domain.schema.OnlineTCInfoDO;

import java.util.List;

public interface IStudentTeachCourseService {

	List<OnlineTCInfoDO> getOnlineTCListByCourse(OnlineTCForm onlineTCForm);


}
