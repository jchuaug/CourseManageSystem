//app.js
import constants from "./utils/constants";
import cache from "./utils/localCache";

App({
    onLaunch: function () {
        // 登录，根据后台返回的数据决定是不是需要绑定
        wx.login({
            success: function (res) {
                if (res.code) {
                    cache.set('code', res.code);
                    wx.request({
                        url: constants.domain + '/wechat/login',
                        method: 'post',
                        dataType: 'json',
                        data: {"code": res.code},
                        success(e) {
                            console.log(e);
                            cache.set('user', e.data);  // it's a hack to keep track of current user
                            cache.set('jwt', e.data.jwt);

                            let targetUrl = "";
                            if (e.data.type === 'student') {
                                targetUrl = "/pages/student/studentMain/studentMain";
                            } else {
                                targetUrl = "/pages/teacher/classManage/classManage"
                            }

                            wx.redirectTo({
                                url: targetUrl
                            });
                        },
                        complete(e) {
                            if (e.statusCode == '404') {
                                wx.redirectTo({
                                    url: '/pages/bind/bind'
                                });
                            }
                        }
                    });
                } else {
                    console.log('获取用户登录态失败！' + res.errMsg)
                }
            }
        });
        //
        // // 获取用户信息
        // wx.getSetting({
        //     success: res => {
        //         if (res.authSetting['scope.userInfo']) {
        //             // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
        //             wx.getUserInfo({
        //                 success: res => {
        //                     // 可以将 res 发送给后台解码出 unionId
        //                     this.globalData.userInfo = res.userInfo
        //
        //                     // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
        //                     // 所以此处加入 callback 以防止这种情况
        //                     if (this.userInfoReadyCallback) {
        //                         this.userInfoReadyCallback(res)
        //                     }
        //                 }
        //             })
        //         }
        //     }
        // })
    },
    globalData: {
        userInfo: null
    }
});