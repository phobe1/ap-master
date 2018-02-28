package com.you.ap.domain.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.you.ap.domain.enums.message.MessagePushEnum;
import com.you.ap.domain.enums.user.UserTypeEnum;

import java.util.List;
import java.util.Set;

public class MessgaeModel {
    @JsonIgnore
    private TokenModel tokenModel;
    private String title;
    @JsonIgnore
    private MessagePushEnum messagePushEnum;
    private int key;
    private List<Object> value = Lists.newArrayList();
    @JsonIgnore
    private boolean notice =false;

    public MessgaeModel(){}

    public MessgaeModel(MessagePushEnum messagePushEnum){
        this.key=messagePushEnum.getKey();
        this.messagePushEnum = messagePushEnum;
        addData(messagePushEnum.getValue());
        this.title = messagePushEnum.getTitle();
    }

    public MessgaeModel(MessagePushEnum messagePushEnum,Object... data){
        this.key=messagePushEnum.getKey();
        addData(data);
        this.messagePushEnum = messagePushEnum;
        this.title = messagePushEnum.getTitle();
    }

    public MessgaeModel notice(){
        this.notice=true;
        return this;
    }

    public MessgaeModel addData(Object... data){
        if(this.value == null){
            this.value = Lists.newArrayList();
        }
        this.value.add(data);
        return this;
    }

    public MessgaeModel of(UserTypeEnum userTypeEnum,int userId){
        this.setTokenModel(new TokenModel(userTypeEnum,userId));
        return this;
    }

    public MessgaeModel(Object... data){
        this.key=0;
        addData(data);
    }

    public TokenModel getTokenModel() {
        return tokenModel;
    }

    public void setTokenModel(TokenModel tokenModel) {
        this.tokenModel = tokenModel;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }


    public List<Object> getValue() {
        return value;
    }

    public void setValue(List<Object> value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isNotice() {
        return notice;
    }

    public void setNotice(boolean notice) {
        this.notice = notice;
    }

    public MessagePushEnum getMessagePushEnum() {
        return messagePushEnum;
    }

    public void setMessagePushEnum(MessagePushEnum messagePushEnum) {
        this.messagePushEnum = messagePushEnum;
    }

    @Override
    public String toString() {
        try{
            return new ObjectMapper().writeValueAsString(this);
        }catch (Exception e){
            return super.toString();
        }
    }
}
