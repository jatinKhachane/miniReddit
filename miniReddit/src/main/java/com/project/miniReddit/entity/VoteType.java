package com.project.miniReddit.entity;

public enum VoteType {
    UPVOTE(1), DOWNVOTE(-1);
    VoteType(int direction) {}
}
