package loc.react_ci_cd.model;

import java.util.List;

public record UserWithPosts(ExternalUser user, List<ExternalPost> posts) {
}
