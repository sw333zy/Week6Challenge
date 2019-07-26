package com.example.demo;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@Controller
public class HomeController {
    //AUTOWIRED WILL CREATE A NEW INSTANCE OF OUR OR OBJECT SO WE DONT HAVE TO TYPE OUT
    //OBJECT = NEW OBJECT
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    CloudinaryConfig cloudc;

    //START POSITION
    @RequestMapping("/")
    //CARS LIST MODEL CLASS CALLED MODEL
    public String listCars(Model model) {
        //TO THAT MODEL ADD THE ATTRIBUTE WITH THE VARIABLE NAME CATEGORIES
        //WHICH IS CATERGORY REPOSITORY.FINDALL()
        model.addAttribute("categories", categoryRepository.findAll());
        //ADD THE SAME FOR CARS
        model.addAttribute("cars", carRepository.findAll());
        //RETURN THE TEMPLATE CALLED LIST
        return "list";
    }

    //GET MAPPING WHICH RETRIEVES DATA FROM THE ADD ROUTE
    //WHEN VISITED IT WILL MAKE A NEW CLASS INSTANCE OF CAR WHERE THE USER CAN INPUT
    //VALUES SPECIFIED IN THE FORM
    @GetMapping("/add")
    public String carForm(Model model) {
        model.addAttribute("car", new Car());
        //ALSO CREATES VARIABLE CALLED CATEGORIES WHICH FINDS ALL THE CAR CATEGORIES FOR TO ASSOCIATE WITH OUR CAR
        model.addAttribute("categories", categoryRepository.findAll());
        return "carform";
    }

    //FOR ADDING PICUTURE DONT WORRY ABOUT IT
    @PostMapping("/add")
    public String processMessage(@ModelAttribute Car car,
                                 @RequestParam("file") MultipartFile file){
        if (file.isEmpty()) {
            carRepository.save(car);
            return "redirect:/";
        }
        try{
            Map uploadResult = cloudc.upload(file.getBytes(),
                    ObjectUtils.asMap("resourcetype", "auto"));
            car.setCarpic(uploadResult.get("url").toString());
            carRepository.save(car);
        }catch (IOException e){
            e.printStackTrace();
            return "redirect:/add";
        }
        return  "redirect:/";
    }

    //POST MAPPING WILL SEND DATA TO SERVER
    @PostMapping("/process")
    //IF THE CLASS OF CAR OBJECT OF CAR IS VALID ACCORDING TO ITS CONSTRICTIONS
    //BINDINGRESULT IS THE CLASS AND RESULT OBJECT FOR THIS
    public String processForm(@Valid Car car, BindingResult result,
                              Model model) {
        //IF THE HASERRORS FUCTION WHEN APPLIED TO RESULT HAS ERRORS
        //IT RETURNS THE FORM WITH THE ERROR MESSAGES
        if (result.hasErrors()) {
            //ONCE AGAIN THIS SHOWS OUR CATEGORIES FOR THE FORM
            model.addAttribute("categories", categoryRepository.findAll());
            return "carform";
        }
        //IF THERE ARE NO PROBLEMS SAVE THE CAR AND REDIRECT TO OUR UPDATED
        //DEFAULT PAGE WHICH IS LIST
        carRepository.save(car);
        return "redirect:/";
    }

    @GetMapping("/addcategory")
    public String categoryForm(Model model) {
        //TO THE MODEL ADD ATTRIBUTE CATEGORY VARIABLE WHICH IS OUR CATEGORY CLASS
        model.addAttribute("category", new Category());
        //CATEGORIES VARIABLE COLLECTS FROM FINDALL
        model.addAttribute("categories", categoryRepository.findAll());

        return "categoryform";
    }

    @PostMapping("/processcategory")
    public String processCategory(@Valid Category category, BindingResult result,
                                  Model model) {
        if (result.hasErrors()) {
            return "categoryform";
        }
        categoryRepository.save(category);
        return "redirect:/add";

    }

    //FOR DETAIL ROUTE
    @RequestMapping("/detail/{id}")
    //THE PATH VAIRABLE ID IS A LONG ID, MODEL THIS
    public String showCourse(@PathVariable("id") long id, Model model){
        //ON THE MODEL MAKE A CAR AND GET IT FROM THE CAR REPOSITORY BY ITS ID
        model.addAttribute("car", carRepository.findById(id).get());
        return "show";
    }

    //SAME
    @RequestMapping("/update/{id}")
    public String updateCourse(@PathVariable("id") long id, Model model){
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("car", carRepository.findById(id).get());
        return "carform";
    }

    @RequestMapping("/delete/{id}")
    public String deleteCourse(@PathVariable("id") long id){
        //IN THE CAR REPOSITORY DELETE BY THE SELECTED ID
        carRepository.deleteById(id);
        return "redirect:/";
    }
}