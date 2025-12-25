package org.studyeasy.SpringStarter.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.studyeasy.SpringStarter.Model.Post;

@Repository
public interface postRepository extends JpaRepository<Post,Long> {
    
}
