package edu.elearning.se;

import com.google.common.base.MoreObjects;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;

@Builder
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PostLink extends VersionedEntity {

    private LocalDateTime creationDate;

    @NonNull
    @EqualsAndHashCode.Include
    private String id;
    private LinkType linkType;
    private String postId;
    private String relatedPostId;
    @NonNull
    private UserWebsite userWebsite;

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public String getId() {
        return id;
    }

    public LinkType getLinkType() {
        return linkType;
    }

    public String getPostId() {
        return postId;
    }

    public String getRelatedPostId() {
        return relatedPostId;
    }

    public UserWebsite getUserWebsite() {
        return userWebsite;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("creationDate", creationDate)
                .add("id", id)
                .add("linkType", linkType)
                .add("postId", postId)
                .add("relatedPostId", relatedPostId)
                .add("userWebsite", userWebsite)
                .toString();
    }
}
