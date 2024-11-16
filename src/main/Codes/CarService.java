package com.example.portal.services;

import com.example.portal.entity.Car;
import com.example.portal.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final String uploadDir = "src/main/resources/static/images/";

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    public String saveCarImage(MultipartFile image) {
        if (image.isEmpty()) {
            return null;
        }

        String uniqueFileName = UUID.randomUUID().toString() + "-" + image.getOriginalFilename();
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try {
            Path path = Path.of(uploadDir, uniqueFileName);
            Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            return uniqueFileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Car> getCarsByOwner(String email) {
        return carRepository.findByOwner_Email(email);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }
}
