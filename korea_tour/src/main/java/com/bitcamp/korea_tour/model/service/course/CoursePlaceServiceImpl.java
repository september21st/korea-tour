package com.bitcamp.korea_tour.model.service.course;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bitcamp.korea_tour.model.CoursePlaceDto;
import com.bitcamp.korea_tour.model.mapper.CoursePlaceMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CoursePlaceServiceImpl implements CoursePlaceService{
	private final CoursePlaceMapper mapper;

	@Override
	public void insertCoursePlace(CoursePlaceDto dto) {
		// TODO Auto-generated method stub
		mapper.insertCoursePlace(dto);
	}

	@Override
	public void deleteCoursePlace(int coursePlaceNum) {
		// TODO Auto-generated method stub
		mapper.deleteCoursePlace(coursePlaceNum);
	}

	@Override
	public int getTotalCoursePlace(int courseNum) {
		// TODO Auto-generated method stub
		return mapper.getTotalCoursePlace(courseNum);
	}
	
	@Override
	public void updateCoursePlace(int coursePlaceNum, int orderNum) {
		Map<String, Integer> map=new HashMap<String, Integer>();
		map.put("coursePlaceNum", coursePlaceNum);
		map.put("orderNum", orderNum);
		mapper.updateCoursePlace(map);
	}

}
