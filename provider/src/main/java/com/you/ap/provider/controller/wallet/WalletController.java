package com.you.ap.provider.controller.wallet;


import com.you.ap.domain.enums.user.UserTypeEnum;
import com.you.ap.domain.vo.ApiResponse;
import com.you.ap.domain.vo.wallet.WalletVO;
import com.you.ap.service.wallet.IWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/wallet")
public class WalletController {
    @Autowired private  IWalletService walletService;

    @RequestMapping(value = "/student/my.json", method = RequestMethod.GET)
    @ResponseBody
    public Object getStudentWallet(
            @RequestAttribute("studentId") int studentId
    ){
        return ApiResponse.buildSuccess(new WalletVO(walletService.getCoin(studentId, UserTypeEnum.Student.key)));
    }

    @RequestMapping(value = "/teacher/my.json", method = RequestMethod.GET)
    @ResponseBody
    public Object getTeacherWallet(
            @RequestAttribute("teacherId") int teacherId
    ){
        return ApiResponse.buildSuccess(new WalletVO(walletService.getCoin(teacherId, UserTypeEnum.Teacher.key)));
    }
}
