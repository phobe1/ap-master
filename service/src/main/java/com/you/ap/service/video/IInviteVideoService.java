package com.you.ap.service.video;


import com.you.ap.domain.model.TokenModel;
import com.you.ap.domain.vo.ApiResponse;

public interface IInviteVideoService {

	ApiResponse getHandleInvitation(TokenModel tokenModel);
	
	ApiResponse inviteVideo(int studentId, int tcId,String stuZoomId);

	ApiResponse closeInvitationByStudent(int studentId);

	ApiResponse successInvite(int studentId);

	ApiResponse closeInvitationByTeacher(int studentId);

	ApiResponse aggreVideo(int teacherId,String key);

	ApiResponse checkVideoConn(int teacherId);
	
}
