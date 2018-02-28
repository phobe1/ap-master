package com.you.ap.service.user.imp;

import com.you.ap.common.constant.Constant;
import com.you.ap.common.constant.ResponseConstant;
import com.you.ap.common.helper.CommonUtil;
import com.you.ap.common.helper.MD5Util;
import com.you.ap.common.helper.SendMessage;
import com.you.ap.dao.user.IStudentDAO;
import com.you.ap.domain.enums.user.UserOnlineStatusEnum;
import com.you.ap.domain.enums.user.UserTypeEnum;
import com.you.ap.domain.form.user.UserLoginForm;
import com.you.ap.domain.model.TokenModel;
import com.you.ap.domain.pojo.AppInfoDO;
import com.you.ap.domain.pojo.Student;
import com.you.ap.domain.schema.UserOnlineInfoDO;
import com.you.ap.domain.schema.user.StudentInfoDO;
import com.you.ap.domain.vo.ApiResponse;
import com.you.ap.domain.vo.user.StudentLoginVO;
import com.you.ap.service.user.IStudentLoginService;
import com.you.ap.service.user.IUserOnlineInfoService;
import com.you.ap.service.wallet.IWalletService;
import org.eclipse.jetty.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Service
public class StudentLoginServiceImpl implements IStudentLoginService {

	private Logger logger = LoggerFactory.getLogger(UserOnlineInfoServiceImp.class);


	@Autowired private IUserOnlineInfoService userOnlineInfoService;

	@Autowired
	private IStudentDAO studentDAO;

	@Autowired
	private IWalletService walletService;

	@Override
	public ApiResponse register(UserLoginForm userLoginForm) {
		try{
			userLoginForm.setPassword(MD5Util.getStringMD5String(userLoginForm.getPassword()));
			Student student=new Student(userLoginForm);
			studentDAO.insert(student);
			ApiResponse apiResponse=createToken(student,userLoginForm);
			TokenModel tokenModel= (TokenModel) apiResponse.getData();
			walletService.initwallet(tokenModel.getUserId(),tokenModel.getUserType().getKey());
			return apiResponse;
		}catch (Exception e){
			logger.error("register",e);
			return ApiResponse.buildFailure(e.getMessage());
		}
	}

	@Override
	public ApiResponse login(UserLoginForm userLoginForm) {
		try{
			Student student = null;

			if (userLoginForm.getPhone() != null) {
				if(studentDAO.existPhone(userLoginForm.getPhone())<1){
					return ApiResponse.buildFailure("手机号尚未注册");
				}
				student = studentDAO.logininbyPhone(userLoginForm.getPhone(), userLoginForm.getPassword());
			}

			else {
				student = studentDAO.loginin(userLoginForm.getLoginname(), userLoginForm.getPassword());
			}
			if (student == null){
				return ApiResponse.buildFailure("账号密码不匹配");
			}
			if (validVideoStatus(student.getId())==false){
				return ApiResponse.buildFailure("当前用户正在视频中");
			}
			TokenModel token=(TokenModel)(createToken(student,userLoginForm).getData());
			return ApiResponse.buildSuccess(new StudentLoginVO(token,studentDAO.getStudentInfo(token.getUserId())));
		}catch (Exception e){
			logger.error("login",e);
			return ApiResponse.buildFailure(e.getMessage());
		}

	}

	@Override
	public ApiResponse nameIsUsed(String loginname) {
		int count =  studentDAO.existName(loginname);
		if (count>0){
			return ApiResponse.buildFailure();
		}
		return ApiResponse.buildSuccess();
	}

	@Override
	public ApiResponse updateStudent(Student student) {

		return studentDAO.update(student) > 0 ? ApiResponse.buildSuccess() : ApiResponse.buildFailure();

	}


	@Override
	public ApiResponse loginByPhone(UserLoginForm userLoginForm) {
		try{
			Student student = studentDAO.getDOByPhone(userLoginForm.getPhone());
			if ( student == null){
				return register(userLoginForm);
			}
			if (validVideoStatus(student.getId())==false){
				return ApiResponse.buildFailure("当前用户正在视频中");
			}
			return createToken(student,userLoginForm);
		}catch (Exception e){
			logger.error("loginByPhone",e);
			return ApiResponse.buildFailure(e.getMessage());
		}
	}

	@Override
	public ApiResponse loginByWechat(UserLoginForm userLoginForm) {
		try{
			String openId= SendMessage.getOpenId(userLoginForm.getCode());
			if(!StringUtil.isBlank(openId)){
				return ApiResponse.buildFailure("wechat failed!!!");
			}
			Student student= studentDAO.loginByWechat(openId);
			if ( student == null){
				return register(userLoginForm);
			}
			if (validVideoStatus(student.getId())==false){
				return ApiResponse.buildFailure("当前用户正在视频中");
			}
			return createToken(student,userLoginForm);
		}catch (Exception e){
			logger.error("loginByWechat",e);
			return ApiResponse.buildFailure(e.getMessage());
		}
	}

	@Override
	public ApiResponse phoneIsUsed(String phone) {

		return studentDAO.existPhone(phone) > 0 ? ApiResponse.buildSuccess("电话号码已存在") : ApiResponse.buildSuccess("电话号码不存在");
	}

	@Override
	public ApiResponse wechatIsUsed(String openid) {

		return studentDAO.existWechat(openid) > 0?ApiResponse.buildFailure():ApiResponse.buildSuccess();

	}

	@Override
	public String changePassword(Integer id, String password, String newpassword) {

		int exits=studentDAO.existpassword(id, password);
		if(exits>0){
			studentDAO.updatePassword(id, newpassword);
			return "successfull!";
		}else{
			return "old password is wrong!";
		}

	}

	@Override
	public String bandPhone(Integer id, String phone) {

		int exits=studentDAO.updateBandPhone(id, phone);
		if(exits>0){
			return "successful";
		}
		return "failed";
	}

	@Override
	public ApiResponse validWechatPhone(String phone) {

		Student student=studentDAO.getDOByPhone(phone);

		if(student==null){
			return ApiResponse.buildFailure("phone not exist");
		}
		if(student.getOpenid()!=null&&!"".equals(student.getOpenid())){
			return ApiResponse.buildFailure("phone has openId");
		}else{
			return ApiResponse.buildFailure("phone regist,not openId");
		}
	}

	@Override
	public boolean changePwdByphone(String phone, String password) {
		int outcome=studentDAO.updatePasswordByphone(phone, password);
		return outcome>0;

	}

	private ApiResponse createToken(Student student,UserLoginForm userLoginForm){
		if ( null == student){
			return ApiResponse.buildFailure("student null");
		}
		TokenModel token = CommonUtil.createToken(student.getId(), UserTypeEnum.Student);
		userOnlineInfoService.addWithLogin(token,userLoginForm);
		return ApiResponse.buildSuccess(token);
	}

	@Override
	public boolean sendChangePasswordCode(String phone,HttpSession session) {
		if (phone == null){
			return false;
		}
		String code=String.valueOf((int)((Math.random()*9+1)*100000));
		session.setAttribute(Constant.VALIDATE_PHONE, phone);
		session.setAttribute(Constant.VALIDATE_PHONE_CODE, code.toString());
		session.setAttribute(Constant.SEND_CODE_TIME, new Date().getTime());
		logger.info("coding is "+code);
		return !SendMessage.sentMessage(phone, code).equals("服务器异常");
	}

	@Override
	public ApiResponse resetpassword(UserLoginForm userLoginForm,HttpSession session){

		ApiResponse checkResult = validMessageCode(session,userLoginForm);
		if(checkResult==null)
			return ApiResponse.buildSuccess(studentDAO.updatePasswordByphone(userLoginForm.getPhone(),MD5Util.getStringMD5String(userLoginForm.getPassword())));
		else return checkResult;
	}

	@Override
	public ApiResponse feedback(String feedback,int studentId){
		return ApiResponse.buildSuccess(studentDAO.addfeedback(studentId,feedback));
	}

	@Override
	public ApiResponse getStudentInfo(int studentId) {
		StudentInfoDO studentInfoDO=null;
		studentInfoDO=studentDAO.getStudentInfo(studentId);
		if(studentInfoDO!=null){
			return ApiResponse.buildSuccess(studentInfoDO);
		}
		return ApiResponse.buildFailure();
	}

	@Override
	public ApiResponse realnameIdentify(int studentId, String name,String college,int grade,String identifyUrl) {
		logger.info("real name loger is "+identifyUrl);
		return ApiResponse.buildSuccess(studentDAO.realnameIdentify(name,studentId,college,grade,identifyUrl));
	}

	@Override
	public ApiResponse appIntroduction(String version) {
		AppInfoDO appInfoDO=studentDAO.getAppInfo(version);
		if(appInfoDO!=null){
			return ApiResponse.buildSuccess(appInfoDO);
		}
		return ApiResponse.buildFailure();
	}

	@Override
	public boolean existPhone(String phone) {
		return studentDAO.existPhone(phone) > 0;
	}


	private ApiResponse validMessageCode(HttpSession session,UserLoginForm userLoginForm){
		if ( userLoginForm==null || !userLoginForm.validPhoneParam()){
			return ApiResponse.buildFailure(ResponseConstant.PHONEORCODEValid);
		}
		if(studentDAO.existPhone(userLoginForm.getPhone())==0){
			return  ApiResponse.buildFailure(ResponseConstant.PHONENOTEXIST);
		}
		String sessionCode = (String) session.getAttribute(Constant.VALIDATE_PHONE_CODE);
		String sessionPhone = (String) session.getAttribute(Constant.VALIDATE_PHONE);
		if ( userLoginForm.getPhone().equals(sessionPhone)&& userLoginForm.getCode().equals(sessionCode)){
			return null;
		}
		return ApiResponse.buildFailure(ResponseConstant.MESSAGECODEERROR);
	}

	private boolean validVideoStatus(int studentId){
		UserOnlineInfoDO userOnlineInfoDO = userOnlineInfoService.getByUser(studentId,UserTypeEnum.Student);
		if (userOnlineInfoDO == null || userOnlineInfoDO.getStatus()!=UserOnlineStatusEnum.VIDEO.key){
			return true;
		}
		return false;
	}


}
