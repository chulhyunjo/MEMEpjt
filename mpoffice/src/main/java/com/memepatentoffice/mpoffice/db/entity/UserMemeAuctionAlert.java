package com.memepatentoffice.mpoffice.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class UserMemeAuctionAlert {
    @EmbeddedId
    private UserMemeAuctionAlertId id;

    @MapsId("memeSeq")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "meme_seq", nullable = false)
    private Meme memeSeq;

    @MapsId("userSeq")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_seq", nullable = false)
    private User userSeq;

}