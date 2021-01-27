import React, { Component } from 'react';
import "./ReservationList.css";


class ReservationList extends Component {
    render() {
        return (
            <div>
                <table className="reservationTable">
                    <caption><h1>예약확인</h1></caption>
                    <thead>
                        <th id="writeDay">예약 날짜</th>
                        <th id="homestayName">장소</th>
                        <th id="checkInDay">체크인</th>
                        <th id="checkOutDay">체크아웃</th>
                        <th id="person">인원</th>
                        <th id="approve">승인 현황</th>
                    </thead>
                </table>
            </div>
        );
    }
}

export default ReservationList;