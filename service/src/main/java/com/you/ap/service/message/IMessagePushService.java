package com.you.ap.service.message;

import com.you.ap.domain.enums.user.UserTypeEnum;
import com.you.ap.domain.model.MessgaeModel;

public interface IMessagePushService {

    void pushMessage(MessgaeModel data) throws Exception;

}
