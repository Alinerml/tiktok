package com.tiktok.controller;

import com.tiktok.bean.User;
import com.tiktok.bean.Video;
import com.tiktok.bean.dto.UserIdAndTokenDto;
import com.tiktok.bean.dto.UserInfoDto;
import com.tiktok.bean.vo.UserLoginVo;
import com.tiktok.common.api.vo.Result;
import com.tiktok.service.IUserInfoService;
import com.tiktok.service.IUserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;

 /**
 * @Description: user
 * @Author: jeecg-boot
 * @Date:   2023-08-03
 * @Version: V1.0
 */
@Api(tags="user")
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
	@Autowired
	private IUserService userService;

	 @Autowired
	 private IUserInfoService userInfoService;

	 @ApiOperation(value="用户信息", notes="用户信息")
	 @GetMapping()
	 public Result<User> queryById(@RequestBody UserIdAndTokenDto userIdAndTokenDto) {
		 User user = userService.queryById(userIdAndTokenDto);
		 if(user==null) {
			 return Result.error("未找到对应数据");

		 }
		 return Result.OK(user);
	 }

	 /**
	  * @description:  添加
	  */
	 @ApiOperation(value="测试-用户user添加", notes="测试-用户添加")
//	@RequiresPermissions("goods:goods_goods:add")
	 @PostMapping(value = "/add")
	 public Result<String> add(@RequestBody User user) {
		 userService.save(user);
		 return Result.OK("添加成功！");
	 }

	 /**
	  * @description:  用户注册
	  */
	 @ApiOperation(value="用户注册", notes="用户注册")
//	@RequiresPermissions("goods:goods_goods:add")
	 @PostMapping(value = "/register")
	 public Result register(@RequestBody UserInfoDto userInfoDto) {
		 UserLoginVo userLoginVo = userInfoService.register(userInfoDto);

		 return Result.OK("注册成功！",userLoginVo);
	 }

	 /**
	  * @description:  用户登录
	  */
	 @ApiOperation(value="用户注册", notes="用户注册")
//	@RequiresPermissions("goods:goods_goods:add")
	 @PostMapping(value = "/login")
	 public Result login(@RequestBody UserInfoDto userInfoDto) {
		 UserLoginVo userLoginVo = userInfoService.login(userInfoDto);

		 return Result.OK("登录成功！",userLoginVo);
	 }


	 /**
	  * @description:  修改
	  */
	 @ApiOperation(value="测试-用户user修改", notes="测试-用户修改")
//	@RequiresPermissions("goods:goods_goods:add")
	 @PostMapping(value = "/edit")
	 public Result<String> edit(@RequestBody User user) {
		 userService.updateById(user);
		 return Result.OK("修改成功！");
	 }
}
