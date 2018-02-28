package com.you.ap.service.user.imp;

import com.you.ap.common.helper.CommonUtil;
import com.you.ap.common.helper.MD5Util;
import com.you.ap.common.helper.SendMessage;
import com.you.ap.dao.user.ITeacherDAO;
import com.you.ap.domain.enums.user.UserOnlineStatusEnum;
import com.you.ap.domain.enums.user.UserTypeEnum;
import com.you.ap.domain.form.user.UserLoginForm;
import com.you.ap.domain.model.TokenModel;
import com.you.ap.domain.pojo.Teacher;
import com.you.ap.domain.schema.UserOnlineInfoDO;
import com.you.ap.domain.vo.ApiResponse;
import com.you.ap.domain.vo.user.TeacherVO;
import com.you.ap.service.user.ITeacherLoginService;
import com.you.ap.service.user.IUserOnlineInfoService;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@Service
public class TeacherLoginServiceImpl implements ITeacherLoginService {

	@Autowired
	private ITeacherDAO teacherDao;
	@Autowired
	private IUserOnlineInfoService infoService;

	private static final String VALIDATE_PHONE_CODE = "VALIDATE_PHONE_CODE";
	private static final String VALIDATE_PHONE = "VALIDATE_PHONE";
	private static final String SEND_CODE_TIME = "SEND_CODE_TIME";


	@Override
	public ApiResponse register(String phone, String code,String password, HttpServletRequest request){
		HttpSession session = request.getSession();
		String validCode = (String) session.getAttribute(VALIDATE_PHONE_CODE);
		String validphone = (String) session.getAttribute(VALIDATE_PHONE);

		if(validCode==null||validphone==null){
			return ApiResponse.buildFailure("手机号或者验证码为空");
		}

		if(!"".equals(phone)&&validCode.equals(code)&&validphone.equals(phone)){
			Teacher teacher=new Teacher();
			teacher.setPhone(phone);
			teacher.setPassword(MD5Util.getStringMD5String(password));

			boolean isSucessful = addTeacher(teacher);
			if(isSucessful){
				return ApiResponse.buildSuccess(teacher,"注册成功");
			}
		}
		return ApiResponse.buildFailure("注册失败");
	}


	@Override
	public ApiResponse loginNomal( String phone, String password, double lat, double lng) {
		if ( StringUtil.isBlank(phone)) {

			return ApiResponse.buildFailure("手机号为空");
		}

		if(teacherDao.existPhone(phone)<1){
			return ApiResponse.buildFailure("手机号尚未注册");
		}

		if (StringUtil.isBlank(password)) {
			return ApiResponse.buildFailure("密码为空");
		}
		Teacher teacher = new Teacher();
		//teacher.setLoginname(loginname);
		if (!StringUtil.isBlank(phone)) {
			teacher.setPhone(phone);
		}
		teacher.setPassword(MD5Util.getStringMD5String(password));
		teacher = login(teacher);

		if (teacher != null) {

			UserOnlineInfoDO userOnlineInfoDO=infoService.getByUser(teacher.getId(),UserTypeEnum.Teacher);

			if (userOnlineInfoDO!=null&&userOnlineInfoDO.getStatus()== UserOnlineStatusEnum.VIDEO.key){
				return ApiResponse.buildFailure("当前用户正在视频中");
			}
			return ApiResponse.buildSuccess(createToken(teacher.getId(), lat, lng),"sucessful login");
		} else {
			return ApiResponse.buildFailure("账号密码不匹配");
		}
	}

	@Override
	public ApiResponse loginSentCode(String phone, HttpServletRequest request) throws IOException {
		String code=String .valueOf((int)((Math.random()*9+1)*100000));
		HttpSession session = request.getSession();
		session.setAttribute(VALIDATE_PHONE, phone);
		session.setAttribute(VALIDATE_PHONE_CODE, code.toString());
		session.setAttribute(SEND_CODE_TIME, new Date().getTime());
		return ApiResponse.buildSuccess(SendMessage.sentMessage(phone, code));
	}

	@Override
	public ApiResponse loginPhone(String phone,String code,double lat, double lng,HttpServletRequest request){
		HttpSession session = request.getSession();
		String validCode = (String) session.getAttribute(VALIDATE_PHONE_CODE);
		String validphone = (String) session.getAttribute(VALIDATE_PHONE);
		if(!"".equals(phone)&&validCode.equals(code)&&validphone.equals(phone)){
			Teacher teacher = teacherDao.loginByPhone(phone);
			if (infoService.getByUser(teacher.getId(),UserTypeEnum.Teacher).getStatus()== UserOnlineStatusEnum.VIDEO.key){
				return ApiResponse.buildFailure("当前用户正在视频中");
			}
			return ApiResponse.buildSuccess(createToken(teacher.getId(), lat, lng),"sucessful login");
		}
		return ApiResponse.buildFailure("code do not match phone");
	}

	@Override
	public boolean nameIsUsed(String loginname) {

		return teacherDao.existName(loginname) > 0;
	}

	@Override
	public boolean updateTeacher(Teacher teacher) {
		if (teacher == null) {
			return false;
		}
		return teacherDao.update(teacher) > 0;
	}


	@Override
	public Teacher loginByWechat(String openid) {
		return teacherDao.loginByWechat(openid);
	}

	@Override
	public boolean phoneIsUsed(String phone) {

		return teacherDao.existPhone(phone) > 0;
	}

	@Override
	public boolean wechatIsUsed(String openid) {

		return teacherDao.existWechat(openid) > 0;
	}

	@Override
	public int getIdbyname(String loginname) {


		return teacherDao.getIdbyname(loginname);
	}


	@Override
	public ApiResponse feedback(String feedback, int teacherId) {
		return ApiResponse.buildSuccess(teacherDao.addfeedback(teacherId,feedback));
	}

	@Override
	public ApiResponse getTeacher(int teacherId) {
		Teacher teacher=null;
		teacher=teacherDao.getTeacher(teacherId);
		if(teacher!=null){
			return ApiResponse.buildSuccess(new TeacherVO(teacher));
		}
		return ApiResponse.buildFailure();
	}


	private TokenModel createToken(int teacherId,double lat,double lng){
		TokenModel tokenModel = CommonUtil.createToken(teacherId, UserTypeEnum.Teacher);
		infoService.addWithLogin(tokenModel,new UserLoginForm());
		return tokenModel;
	}

	public Teacher login(Teacher teacher) {
		if (teacher == null) {
			return null;
		} else if (!"".equals(teacher.getPhone())) {
			return teacherDao.logininbyPhone(teacher.getPhone(), teacher.getPassword());
		} else {
			return teacherDao.loginin(teacher.getLoginname(), teacher.getPassword());
		}
	}

	public boolean addTeacher(Teacher teacher) {
		if (teacher == null) {
			return false;
		}

		if (teacherDao.insert(teacher) == 0) {
			return false;
		}
		return true;
	}

}
