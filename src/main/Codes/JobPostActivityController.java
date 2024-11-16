package com.example.portal.controller;

import com.example.portal.services.CarService;
import com.example.portal.services.UsersService;
import com.example.portal.entity.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class JobPostActivityController {

    private final UsersService usersService;
    private final CarService carService;

    @Autowired
    public JobPostActivityController(UsersService usersService, CarService carService) {
        this.usersService = usersService;
        this.carService = carService;
    }

    @GetMapping("/dashboard/")
    public String searchJob(Model model) {
        Object currentUserProfile = usersService.getCurrentUserProfile();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            model.addAttribute("userName", currentUserName);
        }

        List<Car> cars = carService.getAllCars();
        model.addAttribute("cars", cars);
        model.addAttribute("user", currentUserProfile);
        return "dashboard";
    }

    @GetMapping("/dashboard/addCar")
    public String showAddCarPage(Model model) {
        model.addAttribute("car", new Car());
        return "addCar";
    }

    @PostMapping("/dashboard/add-car")
    public String addCar(@RequestParam("make") String make,
                         @RequestParam("model") String model,
                         @RequestParam("year") int year,
                         @RequestParam("location") String location,
                         @RequestParam("price") double price,
                         @RequestParam("image") MultipartFile image,
                         Model modelAttribute) {
        Car car = new Car();
        car.setMake(make);
        car.setModel(model);
        car.setYear(year);
        car.setLocation(location);
        car.setPrice(price);

        if (!image.isEmpty()) {
            String imageUrl = carService.saveCarImage(image);
            car.setImageUrl(imageUrl);
        }

        carService.saveCar(car);
        return "redirect:/dashboard/";
    }
}
