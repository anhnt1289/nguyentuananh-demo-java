package com.base.common.constant;

public enum FolderTypeEnum {
    IMAGES("images"),
    THUMBNAILS ("thumbnails"),
    VIDEO ("videos"),
    STICKER ("stickers"),
    GIFTS ("gifts"),
    OTHER_FILE("files"),
    NEWS_BANNER("news_banner"),
    AVATAR_STICKER_CATEGORY("stickers/category_avatar"),
    ANIMATION_GIFTS("gifts/animation_gift"),
    ANIMATION_GIFTS_SUB  ("gifts/animation_gift/animation"),
    ID_CARDS ("id_cards"),
    CSV("csv"),
    ZIP("zip");
    String typeName;
    FolderTypeEnum(String name){
        this.typeName = name;
    }

    public String getName() {
        return this.typeName;
    }
}
