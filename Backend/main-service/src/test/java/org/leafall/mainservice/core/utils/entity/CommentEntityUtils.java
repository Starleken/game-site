package org.leafall.mainservice.core.utils.entity;

import org.leafall.mainservice.entity.CommentEntity;
import org.leafall.mainservice.entity.PostEntity;

import static org.leafall.mainservice.core.utils.FakerUtils.*;

public abstract class CommentEntityUtils {

    public static CommentEntity generate(PostEntity post) {
        var comment = new CommentEntity();
        comment.setPost(post);
        comment.setContent(faker.lorem().paragraph());

        return comment;
    }
}
