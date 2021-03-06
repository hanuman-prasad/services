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
public class Vote extends VersionedEntity {

    @NonNull
    @EqualsAndHashCode.Include
    private String id;
    private String postId;
    private VoteType voteTypeId;
    private LocalDateTime creationDate;

    @NonNull
    private UserWebsite userWebsite;

    public String getId() {
        return id;
    }

    public String getPostId() {
        return postId;
    }

    public VoteType getVoteTypeId() {
        return voteTypeId;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public UserWebsite getUserWebsite() {
        return userWebsite;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("postId", postId)
                .add("voteTypeId", voteTypeId)
                .add("creationDate", creationDate)
                .add("userWebsite", userWebsite)
                .toString();
    }
}
