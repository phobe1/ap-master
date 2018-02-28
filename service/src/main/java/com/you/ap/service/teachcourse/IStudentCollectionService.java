package com.you.ap.service.teachcourse;

import com.you.ap.domain.vo.ApiResponse;

import java.util.List;

public interface IStudentCollectionService {

    ApiResponse collect(int studentId, int teachCoueseId);

    ApiResponse deleteById(int studentId,int teacherId);

    ApiResponse getCollectionListByStudent(int studentId, int index, int pageSize);

    boolean checkHasCollect(int studentId,int teacherId);
}
