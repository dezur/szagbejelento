package com.example.demo.dto.converter;

import com.example.demo.dto.CoordinateDto;
import com.example.demo.model.Coordinate;
import org.springframework.stereotype.Component;

@Component
public class CoordinateConverter {
    public Coordinate convertFromDto(CoordinateDto coordinateDto){
        return new Coordinate(coordinateDto.getLatitude(), coordinateDto.getLongitude());
    }
}
