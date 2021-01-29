package com.bitcamp.korea_tour.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bitcamp.korea_tour.model.CourseDto;
import com.bitcamp.korea_tour.model.CourseLikeDto;
import com.bitcamp.korea_tour.model.CourseMarkDto;
import com.bitcamp.korea_tour.model.JoinCourseDetailDto;
import com.bitcamp.korea_tour.model.UserDto;
import com.bitcamp.korea_tour.model.service.course.CourseLikeService;
import com.bitcamp.korea_tour.model.service.course.CourseMarkService;
import com.bitcamp.korea_tour.model.service.course.JoinCourseDetailService;
import com.bitcamp.korea_tour.model.service.login.setting.SessionNames;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CourseDetailController implements SessionNames {
	private final JoinCourseDetailService joincourseDetailService;
	private final CourseLikeService cls;
	private final CourseMarkService cms;


	@GetMapping(value="/courses/{courseNum}")
	public JsonDataList getCourseDetail(
			@PathVariable(value="courseNum") int courseNum,
			HttpServletRequest request
			) {

		HttpSession session=request.getSession();
		UserDto user=(UserDto) session.getAttribute(USER);
		int loginNum;
		String markNumStr;
		String likeNumStr;
		int markNum;
		int likeNum;
		
		if(user==null) {
			loginNum=0;
		}else {
			loginNum=user.getUserNum();
		}
		markNumStr=joincourseDetailService.getCourseMark(courseNum, loginNum);
		likeNumStr=joincourseDetailService.getCourseLike(courseNum, loginNum);
		
		
		CourseDto courseDto=joincourseDetailService.getCourseData(courseNum);
		List<JoinCourseDetailDto> coursePlaceList=joincourseDetailService.getCourseDetail(courseNum);
		int markTotalCount=joincourseDetailService.getCourseMarkTotal(courseNum);
		int likeTotalCount=joincourseDetailService.getCourseLikeTotal(courseNum);
		
		if(markNumStr==null) markNum=0;
		else markNum=Integer.parseInt(markNumStr);
		if(likeNumStr==null) likeNum=0;
		else likeNum=Integer.parseInt(likeNumStr);
		
		MarkLikeData markLikeData=new MarkLikeData(markTotalCount, likeTotalCount, markNum,likeNum);

		return new JsonDataList(courseDto, coursePlaceList, markLikeData);
	}
	//디테일페이지에서 코스 좋아요하기
	@PostMapping(value = "/courselikes")
	public void insertCourseLike(@RequestBody CourseLikeDto dto) {
		cls.insertCourseLike(dto);
	}
	
	
	//디테일페이지에서 좋아요 취소
	@DeleteMapping(value = "/courselikes/{likeNum}/{courseNum}")
	public void deleteLike(
			@PathVariable(value = "likeNum") int likeNum,
			@PathVariable(value = "courseNum") int courseNum
			) {
		cls.deleteCourseLike(likeNum, courseNum);
	}

	
	//디테일페이지에서 즐겨찾기하기
	@PostMapping(value = "/coursemarks")
	public void insertCourseMark(@RequestBody CourseMarkDto dto) {
		cms.insertCourseMark(dto);
	}
	
	
	//디테일페이지에서 즐겨찾기 취소 ->해당 코스의 즐겨찾기 개수 +1
	@DeleteMapping(value = "/coursemarks/{courseMarkNum}")
	public void deleteMark(@PathVariable int courseMarkNum) {
		cms.deleteCourseMark(courseMarkNum);
	}
	
	@Data
	@AllArgsConstructor
	static class JsonDataList {
		private CourseDto courseDto; //dto
		private List<JoinCourseDetailDto> coursePlaceList;  //list
		private MarkLikeData markLikeData;
	}

	@Data
	@AllArgsConstructor
	static class MarkLikeData {
		private int markTotalCount; //즐겨찾기 개수
		private int likeTotalCount; //좋아요 개수
		private int markNum; //로그인한 사용자의 해당코스 즐겨찾기Num
		private int likeNum; //로그인한 사용자의 해당코스 좋아요Num
	}


}
