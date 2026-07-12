package com.healthinnova.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthinnova.portal.common.enums.GlobalErrorCodeConstants;
import com.healthinnova.portal.common.exception.ServiceException;
import com.healthinnova.portal.entity.UserFavorite;
import com.healthinnova.portal.mapper.UserFavoriteMapper;
import com.healthinnova.portal.service.UserFavoriteService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserFavoriteServiceImpl extends ServiceImpl<UserFavoriteMapper, UserFavorite> implements UserFavoriteService {

    @Override
    public void addFavorite(Long userId, String type, Long targetId, String targetTitle) {
        // 检查是否已收藏
        LambdaQueryWrapper<UserFavorite> check = new LambdaQueryWrapper<>();
        check.eq(UserFavorite::getUserId, userId)
             .eq(UserFavorite::getType, type)
             .eq(UserFavorite::getTargetId, targetId);
        if (count(check) > 0) {
            throw new ServiceException(GlobalErrorCodeConstants.BAD_REQUEST.getCode(), "已经收藏过了");
        }
        UserFavorite fav = new UserFavorite();
        fav.setUserId(userId);
        fav.setType(type);
        fav.setTargetId(targetId);
        fav.setTargetTitle(targetTitle);
        save(fav);
    }

    @Override
    public void removeFavorite(Long userId, String type, Long targetId) {
        LambdaQueryWrapper<UserFavorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserFavorite::getUserId, userId)
               .eq(UserFavorite::getType, type)
               .eq(UserFavorite::getTargetId, targetId);
        remove(wrapper);
    }

    @Override
    public List<UserFavorite> getUserFavorites(Long userId, String type) {
        LambdaQueryWrapper<UserFavorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserFavorite::getUserId, userId)
               .eq(type != null, UserFavorite::getType, type)
               .orderByDesc(UserFavorite::getCreateTime);
        return list(wrapper);
    }

    @Override
    public boolean isFavorited(Long userId, String type, Long targetId) {
        LambdaQueryWrapper<UserFavorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserFavorite::getUserId, userId)
               .eq(UserFavorite::getType, type)
               .eq(UserFavorite::getTargetId, targetId);
        return count(wrapper) > 0;
    }
}
