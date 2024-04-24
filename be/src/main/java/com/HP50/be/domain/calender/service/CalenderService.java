package com.HP50.be.domain.calender.service;

import com.HP50.be.domain.calender.dto.CalenderRequestDto;
import com.HP50.be.domain.calender.dto.CreateCalenderRequestDto;

public interface CalenderService {
    public boolean createCalender(int memberId, CreateCalenderRequestDto requestDto);
    public boolean updateCalender(int memberId, CalenderRequestDto requestDto);
}
