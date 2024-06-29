package com.leafall.accountsservice.core.utils.entity;

import com.leafall.accountsservice.entity.CommentEntity;
import com.leafall.accountsservice.entity.PostEntity;

import java.util.Date;

import static com.leafall.accountsservice.core.utils.FakerUtils.*;

public abstract class CommentEntityUtils {

    public static CommentEntity generate(PostEntity post) {
        var comment = new CommentEntity();
        comment.setPost(post);
        comment.setContent(faker.lorem().paragraph());

        return comment;
    }
}
