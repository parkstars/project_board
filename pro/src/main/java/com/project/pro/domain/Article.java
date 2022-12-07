package com.project.pro.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@Table(indexes= {@Index(columnList="title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @Column(nullable=false) private String title;
    @Setter @Column(nullable=false, length=10000) private String content;
    @Setter private String hashtag;

    //중요사항. 위 클래스에 ToString에 대한 annotation이 있기 때문에 순환참조가 될수 있다.
    //따라서 그 연결고리를 끈는 작업이 필요하다.
    //메모리 저하 및 순환참조를 제거.
    @ToString.Exclude
    //양뱡향 바인드. 서로 데이타 변경이 되는 사항으로 게시글 삭제하면 댓글도 삭제되는 경우를 말한다.
    //그러나 관리목적 등에 의해 댓글은 남겨두는 경우도 생김으로 실무에서는 사용하지 않는 경우가 많다.
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    //jpa auditing
    @CreatedDate  @Column(nullable=false) private LocalDateTime createdAt;
    @CreatedBy     @Column(nullable=false, length=100) private String createdBy;
    @LastModifiedDate  @Column(nullable=false) private LocalDateTime modifiedAt;
    @LastModifiedBy  @Column(nullable=false, length=100) private String modifiedBy;
    protected Article(){}

    private Article(String title, String content, String hashtag){
        this.title=title;
        this.content=content;
        this.hashtag=hashtag;
    }
    public static Article of(String title, String content, String hashtag){
        return new Article(title, content, hashtag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id != null && id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
