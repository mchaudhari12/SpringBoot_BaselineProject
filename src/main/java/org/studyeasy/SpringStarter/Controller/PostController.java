package org.studyeasy.SpringStarter.Controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.studyeasy.SpringStarter.Model.Account;
import org.studyeasy.SpringStarter.Model.Post;
import org.studyeasy.SpringStarter.Service.accountService;
import org.studyeasy.SpringStarter.Service.postService;

@Controller
public class PostController {

    @Autowired
    private postService postservice;

    @Autowired
    private accountService accountservice;

    @GetMapping("/post/{id}")
    public String post(@PathVariable Long id, Model model, Principal principal) {

        Optional<Post> optionalPost = postservice.getById(id);
        String authUser = "email";
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            model.addAttribute("post", post);

            if (principal != null) {
                authUser = principal.getName();
            }
            if (authUser.equals(post.getAccount().getEmail())) {
                model.addAttribute("isOwner", true);
            } else {
                model.addAttribute("isOwner", false);
            }

            return "post-view/post";
        } else {
            return "Page Not Found";
        }
    }

    @GetMapping("/posts/add")
    @PreAuthorize("isAuthenticated()")
    public String addPost(Model model, Principal principal) {
        String authUser = "email";
        if (principal != null) {
            authUser = principal.getName();
        }
        Optional<Account> account = accountservice.findOneByEmail(authUser);

        if (account.isPresent()) {
            Post post = new Post();
            post.setAccount(account.get());
            model.addAttribute("post", post);
            return "post-view/post_add";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/posts/add")
    @PreAuthorize("isAuthenticated()")
    public String addPostHandle(@ModelAttribute Post post, Principal principal) {
        String authUser = "email";
        if (principal != null) {
            authUser = principal.getName();
        }
        if (post.getAccount().getEmail().compareToIgnoreCase(authUser) < 0) {
            return "redirect:/?error";
        }
        postservice.save(post);
        return "redirect:/post/" + post.getId();
    }

    @GetMapping("/post/{id}/edit")
    @PreAuthorize("isAuthenticated()")
    public String getPostforEdit(@PathVariable Long id, Model model) {
        Optional<Post> optionalPost = postservice.getById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            model.addAttribute("post", post);
            return "post-view/post_edit";
        } else {
            return "404";
        }
    }

    @PostMapping("/post/{id}/edit")
    @PreAuthorize("isAuthenticated()")
    public String updatePost(@PathVariable Long id,@ModelAttribute Post post){

        Optional<Post> optinalPost = postservice.getById(id);
        if(optinalPost.isPresent()){
            Post existPost = optinalPost.get();
            existPost.setName(post.getName());
            existPost.setBody(post.getBody());
            postservice.save(existPost);
        }
        return "redirect:/post/" + post.getId();
    }

    @GetMapping("/post/{id}/delete")
    @PreAuthorize("isAuthenticated()")
    public String deletePost(@PathVariable Long id){

        Optional<Post> optionalPost = postservice.getById(id);
        if(optionalPost.isPresent()){
            Post post = optionalPost.get();
            postservice.delete(post);
            return "redirect:/";
        }else{
        return "redirect:/ ? error";
        }
    }
}
