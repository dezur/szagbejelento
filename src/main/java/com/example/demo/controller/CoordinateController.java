package com.example.demo.controller;

import com.example.demo.dto.CoordinateDto;
import com.example.demo.model.Coordinate;
import com.example.demo.service.CoordinateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/coordinates")
public class CoordinateController {
    @Autowired
    CoordinateService coordinateService;

    @GetMapping("")
    public List<Coordinate> list() {
        return coordinateService.listAllCoordinates();
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<Coordinate>> getCoordinatesByDate(@PathVariable("date") Date date) {
        return new ResponseEntity<>(coordinateService.filterByDate(date), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Coordinate> get(@PathVariable Integer id) {
        try {
            Coordinate coordinate = coordinateService.getCoordinate(id);
            return new ResponseEntity<Coordinate>(coordinate, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Coordinate>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/")
    public void add(@RequestBody CoordinateDto coordinate) {
        coordinateService.saveCoordinate(coordinate);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {

        coordinateService.deleteCoordinate(id);
    }
}