package com.avito;

import com.DAO.services.CarService;
import com.cars_annot.Car;
import com.cars_annot.CarForm;
import com.cars_annot.Holder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Controller
@RequestMapping("/create")
public class CreateController {

    final Logger logger = Logger.getLogger(CreateController.class.getName());
    @Autowired
    CarService carService;

    @GetMapping
    protected String goToCreate() {
        return "createCar";
    }

    @PostMapping
    protected String saveCar(@SessionAttribute("user") Holder holder, @ModelAttribute("car") CarForm carForm) {
        Car car = carForm.getCar();
        car.setHolder(holder);
        carService.save(car);
        return "createCar";
    }
}
