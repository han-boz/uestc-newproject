package com.healthinnova.portal.controller.user;

import com.healthinnova.portal.common.Result;
import com.healthinnova.portal.entity.User;
import com.healthinnova.portal.entity.UserFavorite;
import com.healthinnova.portal.security.SecurityUtils;
import com.healthinnova.portal.service.UserFavoriteService;
import com.healthinnova.portal.service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/favorite")
public class FavoriteController {

    private final UserFavoriteService favoriteService;
    private final UserService userService;

    public FavoriteController(UserFavoriteService favoriteService, UserService userService) {
        this.favoriteService = favoriteService;
        this.userService = userService;
    }

    /** 获取当前用户的收藏列表 */
    @GetMapping("/list")
    public Result<Map<String, List<UserFavorite>>> list(@RequestParam(required = false) String type) {
        String username = SecurityUtils.getCurrentUsername();
        User user = userService.getByUsername(username);
        if (user == null) return Result.fail(401, "未登录");

        List<UserFavorite> list = favoriteService.getUserFavorites(user.getId(), type);
        // 按 type 分组返回
        Map<String, List<UserFavorite>> grouped = list.stream()
                .collect(Collectors.groupingBy(UserFavorite::getType));
        return Result.ok(grouped);
    }

    /** 添加收藏 */
    @PostMapping("/add")
    public Result<Void> add(@RequestBody Map<String, Object> body) {
        String username = SecurityUtils.getCurrentUsername();
        User user = userService.getByUsername(username);
        if (user == null) return Result.fail(401, "未登录");

        String type = (String) body.get("type");
        Long targetId = Long.valueOf(body.get("targetId").toString());
        String targetTitle = (String) body.getOrDefault("targetTitle", "");
        favoriteService.addFavorite(user.getId(), type, targetId, targetTitle);
        return Result.ok(null);
    }

    /** 取消收藏 */
    @DeleteMapping("/remove")
    public Result<Void> remove(@RequestParam String type, @RequestParam Long targetId) {
        String username = SecurityUtils.getCurrentUsername();
        User user = userService.getByUsername(username);
        if (user == null) return Result.fail(401, "未登录");

        favoriteService.removeFavorite(user.getId(), type, targetId);
        return Result.ok(null);
    }

    /** 检查是否已收藏 */
    @GetMapping("/check")
    public Result<Map<String, Boolean>> check(@RequestParam String type, @RequestParam Long targetId) {
        String username = SecurityUtils.getCurrentUsername();
        User user = userService.getByUsername(username);
        if (user == null) return Result.ok(Map.of("favorited", false));

        boolean fav = favoriteService.isFavorited(user.getId(), type, targetId);
        return Result.ok(Map.of("favorited", fav));
    }
}
