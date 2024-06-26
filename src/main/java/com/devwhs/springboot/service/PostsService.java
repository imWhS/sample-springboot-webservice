package com.devwhs.springboot.service;

import com.devwhs.springboot.domain.posts.Posts;
import com.devwhs.springboot.domain.posts.PostsRepository;
import com.devwhs.springboot.web.dto.PostResponseDto;
import com.devwhs.springboot.web.dto.PostsListResponseDto;
import com.devwhs.springboot.web.dto.PostsSaveRequestDto;
import com.devwhs.springboot.web.dto.PostsUpdateRequestDto;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시물을 찾지 못했습니다. id = " + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시물을 찾지 못했습니다. id = " + id));
        return new PostResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시물을 찾지 못했습니다. id = " + id));
        postsRepository.delete(posts);
    }

}
