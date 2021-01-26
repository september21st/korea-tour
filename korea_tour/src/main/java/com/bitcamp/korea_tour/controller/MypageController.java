package com.bitcamp.korea_tour.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import com.bitcamp.korea_tour.model.JoinCourseDto;
import com.bitcamp.korea_tour.model.JoinCourseMarkDto;
import com.bitcamp.korea_tour.model.TourAnswerDto;
import com.bitcamp.korea_tour.model.UserDto;
import com.bitcamp.korea_tour.model.service.PlaceMarkService;
import com.bitcamp.korea_tour.model.service.TourAnswerService;
import com.bitcamp.korea_tour.model.service.course.CourseMarkService;
import com.bitcamp.korea_tour.model.service.course.CourseService;
import com.bitcamp.korea_tour.model.service.course.JoinCourseMyService;
import com.bitcamp.korea_tour.model.service.login.setting.SessionNames;
import com.bitcamp.korea_tour.model.service.paging.PagingService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MypageController implements SessionNames {

	private final CourseMarkService cms;
	private final PlaceMarkService pms;
	private final CourseService cs;
	private final JoinCourseMyService jcms;
	private final TourAnswerService tas;

	//페이징	
	private final PagingService pagingService;

	int totalCount=0;
	int start=0;
	int perPage=0;

	//mypage 첫 페이지
	@GetMapping("/mypage")
	public String getMypage(HttpServletRequest request) {

		HttpSession session = request.getSession();
		UserDto user = (UserDto)session.getAttribute(USER);
		int loginNum = user.getUserNum();
		//총 즐겨찾기 개수찾기
		int totalcoursemark = cms.getMyCourseMarkCount(loginNum);
		int totalplacemark = pms.getAllMyPlaceMarkCount(loginNum);
		int totalMark = totalcoursemark+totalplacemark;

		//나의 코스 개수 찾기
		int totalmycourse = cs.getMyCourseCount(loginNum);

		//나의 댓글 개수 찾기
		int myanswer = tas.getTotalCountCourseAnswer(loginNum);
		int myreanswer = tas.getTotalCountCourseReAnswer(loginNum);
		int totalmyanswer = myanswer+myreanswer;
		//보내기
		Map<String, Integer> mypagecount = new HashMap<String, Integer>();
		mypagecount.put("totalMark", totalMark);
		mypagecount.put("totalmycourse", totalmycourse);
		mypagecount.put("totalmyanswer", totalmyanswer);
		//System.out.println(mypagecount);

		Gson gson = new Gson();
		JsonObject json = gson.toJsonTree(mypagecount).getAsJsonObject();

		//System.out.println(json);
		return json.toString();
	}

	//mypage 내가 즐겨찾기한 코스 모아보기
	@GetMapping("/mypage/coursemarks/{currentPage}")
	public JsonData<List<JoinCourseMarkDto>> getMyCourseMarks(@PathVariable(value = "currentPage") int currentPage, HttpServletRequest request) {
		//세션가져오기
		HttpSession session = request.getSession();
		UserDto user = (UserDto)session.getAttribute(USER);
		int loginNum = user.getUserNum();

		totalCount=jcms.getMarkTotalCount(loginNum);
		start=pagingService.getPagingData(totalCount, currentPage).get("start");
		perPage=pagingService.getPagingData(totalCount, currentPage).get("perPage");
		List<JoinCourseMarkDto> list = new ArrayList<JoinCourseMarkDto>();
		list = jcms.getMyMarkCourse(loginNum, start, perPage);
		//System.out.println(list);
		System.out.println("즐겨찾기 코스모아보기 토탈개수: "+totalCount);
		return new JsonData<List<JoinCourseMarkDto>>(list);
	}

	//나의 코스(내가만든코스)모아보기
	@GetMapping("/mypage/courses/{currentPage}")
	public JsonData<List<JoinCourseDto>> getMyCourse(@PathVariable(value = "currentPage") int currentPage, HttpServletRequest request) {
		//세션가져오기
		HttpSession session = request.getSession();
		UserDto user = (UserDto)session.getAttribute(USER);
		int loginNum = user.getUserNum();

		totalCount=jcms.getMyTotalCount(loginNum);
		start=pagingService.getPagingData(totalCount, currentPage).get("start");
		perPage=pagingService.getPagingData(totalCount, currentPage).get("perPage");
		List<JoinCourseDto> list = jcms.getMyCourseList(loginNum, start, perPage);
		System.out.println("내가만든 코스모아보기 토탈개수: "+totalCount);
		return new JsonData<List<JoinCourseDto>>(list);
	}


	//내가 단 코스 댓글
	@GetMapping("/mypage/courseanswer/{currentPage}")
	public JsonData<List<TourAnswerDto>> getCourseAnswer(@PathVariable(value="currentPage") int currentPage, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserDto user = (UserDto)session.getAttribute(USER);
		int loginNum = user.getUserNum();

		totalCount=tas.getTotalCountCourseAnswer(loginNum);
		start=pagingService.getPagingData(totalCount, currentPage).get("start");
		perPage=pagingService.getPagingData(totalCount, currentPage).get("perPage");
		List<TourAnswerDto> courseanswer= new ArrayList<TourAnswerDto>();
		courseanswer = tas.getUserAnswerCourse(loginNum, start, perPage); 
		return new JsonData<List<TourAnswerDto>>(courseanswer);

	}
	//내가 단 코스 답글
	@GetMapping("/mypage/coursereanswer/{currentPage}")
	public JsonData<List<TourAnswerDto>> getCourseReAnswer(@PathVariable(value="currentPage") int currentPage, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserDto user = (UserDto)session.getAttribute(USER);
		int loginNum = user.getUserNum();

		totalCount=tas.getTotalCountCourseReAnswer(loginNum);
		start=pagingService.getPagingData(totalCount, currentPage).get("start");
		perPage=pagingService.getPagingData(totalCount, currentPage).get("perPage");
		List<TourAnswerDto> coursereanswer= new ArrayList<TourAnswerDto>();
		coursereanswer = tas.getUserReAnswerCourse(loginNum, start, perPage); 
		return new JsonData<List<TourAnswerDto>>(coursereanswer);

	}
	//내가 단 관광지 댓글
	@GetMapping("/mypage/placeanswer/{currentPage}")
	public JsonData<List<TourAnswerDto>> getPlaceAnswer(@PathVariable(value="currentPage") int currentPage, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserDto user = (UserDto)session.getAttribute(USER);
		int loginNum = user.getUserNum();

		totalCount=tas.getTotalCountPlaceAnswer(loginNum);
		start=pagingService.getPagingData(totalCount, currentPage).get("start");
		perPage=pagingService.getPagingData(totalCount, currentPage).get("perPage");
		List<TourAnswerDto> placeanswer = new ArrayList<TourAnswerDto>();
		placeanswer = tas.getUserAnswerPlace(loginNum, start, perPage); 
		return new JsonData<List<TourAnswerDto>>(placeanswer);

	}
	//내가 단 관광지 답글
	@GetMapping("/mypage/placereanswer/{currentPage}")
	public JsonData<List<TourAnswerDto>> getPlaceReAnswer(@PathVariable(value="currentPage") int currentPage, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserDto user = (UserDto)session.getAttribute(USER);
		int loginNum = user.getUserNum();

		totalCount=tas.getTotalCountPlaceReAnswer(loginNum);
		start=pagingService.getPagingData(totalCount, currentPage).get("start");
		perPage=pagingService.getPagingData(totalCount, currentPage).get("perPage");
		List<TourAnswerDto> placereanswer = new ArrayList<TourAnswerDto>();
		placereanswer = tas.getUserReAnswerPlace(loginNum, start, perPage); 
		return new JsonData<List<TourAnswerDto>>(placereanswer);

	}	 


	// 댓글,답글 삭제
	@DeleteMapping(value = "/myanswer/{tourAnswerNum}")
	public void deleteAnswer(@PathVariable int tourAnswerNum) {
		tas.deleteCourseAnswerByUser(tourAnswerNum);
		tas.deletePlaceAnswerByUser(tourAnswerNum);
	}

	
	
	///////////////////////////////////////////////////////////////

	@Data
	@AllArgsConstructor
	static class JsonData<T> {
		private T list;
	}


	@Data
	@AllArgsConstructor
	static class CountDataDto{
		private int totalMark;
		private int totalmycourse;
	}


	@Data
	@AllArgsConstructor
	static class MyPageDto{
		private String num;
	}
}
