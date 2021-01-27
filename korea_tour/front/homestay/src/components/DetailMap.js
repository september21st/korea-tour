import React, { useEffect } from 'react';
import './HouseDetailCss/DetailMap.css';

const { kakao } = window;

const DetailMap = () => {
  useEffect(() => {
    const container = document.getElementById('myMap');
    const options = {
      center: new kakao.maps.LatLng(33.450701, 126.570667),
      level: 4,
    };
    const map = new kakao.maps.Map(container, options);

    // 주소-좌표 변환 객체를 생성합니다
    const geocoder = new kakao.maps.services.Geocoder();

    // 주소로 좌표를 검색합니다
    geocoder.addressSearch('개봉로15길', addSearchCB);

    function addSearchCB(result, status) {
      // 정상적으로 검색이 완료됐으면
      if (status === kakao.maps.services.Status.OK) {
        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

        // 결과값으로 받은 위치를 마커로 표시합니다

        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
        map.setCenter(coords);
      }
      const circle = new kakao.maps.Circle({
        center: coords, // 원의 중심좌표 입니다
        radius: 150, // 미터 단위의 원의 반지름입니다
        strokeWeight: 5, // 선의 두께입니다
        strokeColor: 'ddd', // 선의 색깔입니다
        strokeOpacity: 1, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
        strokeStyle: 'solid', // 선의 스타일 입니다
        fillColor: '#3e3e3e', // 채우기 색깔입니다
        fillOpacity: 0.7, // 채우기 불투명도 입니다
      });
      circle.setMap(map);
    }
  }, []);

  return (
    <>
    <div id="myMap"></div>
    <div id="second"></div>
    </>
  );
};

export default DetailMap;