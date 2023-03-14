package com.boot.repository;

import com.boot.entity.BBSComment;
import com.boot.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BBSCommentRepository extends JpaRepository<BBSComment, String>, JpaSpecificationExecutor<BBSComment> {
}
