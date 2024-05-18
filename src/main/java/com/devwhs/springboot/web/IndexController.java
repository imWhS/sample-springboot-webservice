package com.devwhs.springboot.web;

import com.devwhs.springboot.config.auth.LoginMember;
import com.devwhs.springboot.config.auth.dto.SessionMember;
import com.devwhs.springboot.service.PostsService;
import com.devwhs.springboot.web.dto.PostResponseDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginMember SessionMember member) {
        model.addAttribute("posts", postsService.findAllDesc());

        if (member != null) model.addAttribute("userName", member.getName());

        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable("id") Long id, Model model) {
        PostResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }

}
