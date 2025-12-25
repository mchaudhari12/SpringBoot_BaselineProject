package org.studyeasy.SpringStarter.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.studyeasy.SpringStarter.Model.Post;
import org.studyeasy.SpringStarter.Service.postService;

@Controller
public class HomeController {

    @Autowired
    private postService postService;

    @GetMapping("/")
    public String home(Model model){
        List<Post> posts = postService.getAll();
        model.addAttribute("posts", posts);
        return "home";
    }
  
    @GetMapping("/editor")
    public String editor(Model model){
        return "editor";
    }
    
}
