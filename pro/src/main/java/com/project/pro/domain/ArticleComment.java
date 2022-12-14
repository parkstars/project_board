package com.project.pro.domain;

import java.time.LocalDateTime;

public class ArticleComment {
    private Long id;
    private Article article; //아티클 id
    private String content;
    private String hashtag;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;
}
