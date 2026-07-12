package com.healthinnova.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.healthinnova.portal.entity.UserFavorite;
import java.util.List;

public interface UserFavoriteService extends IService<UserFavorite> {
    void addFavorite(Long userId, String type, Long targetId, String targetTitle);
    void removeFavorite(Long userId, String type, Long targetId);
    List<UserFavorite> getUserFavorites(Long userId, String type);
    boolean isFavorited(Long userId, String type, Long targetId);
}
