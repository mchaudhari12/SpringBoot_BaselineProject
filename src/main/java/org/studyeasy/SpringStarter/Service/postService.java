package org.studyeasy.SpringStarter.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.studyeasy.SpringStarter.Model.Post;
import org.studyeasy.SpringStarter.Repository.postRepository;

@Service
public class postService {

    @Autowired
    private postRepository postrepository;

    public Optional<Post> getById(Long id){
        return postrepository.findById(id);
    }

    public List<Post> findAll(){
        return postrepository.findAll();
    }


    public Page<Post> findAll(int offset, int pageSize, String field){
        return postrepository.findAll(PageRequest.of(offset,pageSize).withSort(Direction.ASC,field));
    }


    public void delete(Post post){
        postrepository.delete(post);
    }

    public Post save(Post post){
        if(post.getId() == null){
            post.setCreatedAt(LocalDateTime.now());
        }
        post.setUpdatedAt(LocalDateTime.now());
        return postrepository.save(post);
    }
    
}
