package com.example.demo.repository;

import com.example.demo.model.Coordinate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface CoordinateRepository extends JpaRepository<Coordinate, Integer>{


    List<Coordinate> findCoordinatesByCreationDate(Date date);
}