package com.you.ap.service.order;


import com.you.ap.domain.form.OrderTeacherScoreForm;
import com.you.ap.domain.schema.InvitationTeachDO;
import com.you.ap.domain.schema.order.VideoOrderDO;
import com.you.ap.domain.vo.ApiResponse;

public interface IVideoOrderService {

	VideoOrderDO createVideoOrder(InvitationTeachDO invitationTeachDO);
	
	ApiResponse markScoreForVideoOrder(int studentId, OrderTeacherScoreForm form);

	VideoOrderDO getById(int id);
	



}
