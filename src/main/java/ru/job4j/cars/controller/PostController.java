package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.dto.PhotoDto;
import ru.job4j.cars.model.*;
import ru.job4j.cars.service.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {
    private final BrandService brandService;
    private final BodyService bodyService;
    private final PostService postService;
    private final EngineService engineService;
    private final OwnerService ownerService;
    private final SimplePhotoService simplePhotoService;
    private final CarService carService;
    private final PriceHistoryService priceHistoryService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "posts/list";
    }

    @GetMapping("/withPhotos")
    public String getWithPhotos(Model model) {
        model.addAttribute("posts", postService.findAllWithPhotos());
        return "posts/list";
    }

    @GetMapping("/forTheLastDay")
    public String getOnlyForLastDay(Model model) {
        model.addAttribute("posts", postService.findAllForTheLastDay());
        return "posts/list";
    }

    @GetMapping("/create")
    public String getCreatePage(Model model) {
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("bodies", bodyService.findAll());
        return "posts/create";
    }

    @PostMapping("/create")
    public String createPost(@ModelAttribute Post post, @ModelAttribute Car car, @ModelAttribute Engine engine,
                             @ModelAttribute Owner owner, @SessionAttribute User user,
                             @RequestParam MultipartFile[] files) throws IOException {
        post.setCreated(LocalDateTime.now());
        post.setUser(user);
        engineService.save(engine);
        owner.setUser(user);
        ownerService.save(owner);
        List<Photo> photos = new ArrayList<>();
        if (!files[0].isEmpty()) {
            for (MultipartFile file : files) {
                String path = file.getOriginalFilename();
                byte[] content = file.getBytes();
                photos.add(simplePhotoService.save(new PhotoDto(path, content)));
            }
        }
        car.setEngine(engine);
        car.setOwner(owner);
        car.setPhotos(photos);
        carService.save(car);
        post.setCar(car);
        postService.save(post);
        return "redirect:/posts";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        Optional<Post> optionalPost = postService.findById(id);
        if (optionalPost.isEmpty()) {
            model.addAttribute("message", "Объявление с указанныи id не найдено");
            return "errors/404";
        }
        Car car = carService.getCarWithPhoto(optionalPost.get().getCar().getId());
        if (car.getPhotos().size() == 0) {
            model.addAttribute("messagePhoto", "У этой машины нет фотографий");
        }
        model.addAttribute("car", car);
        model.addAttribute("post", optionalPost.get());
        return "posts/one";
    }

    @PostMapping("/complete")
    public String complete(@ModelAttribute Post post, Model model) {
        var isUpdated = postService.setStatus(post);
        if (!isUpdated) {
            model.addAttribute("message", "Объявление с указанным идентификатором не найдено!");
            return "errors/404";
        }
        return "redirect:/posts";
    }
}
