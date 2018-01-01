import constants from './constants';
import cache from './localCache';

function bindUser(args, cb) {
    wx.login({
        success: function (res) {
            console.log(res);
            if (res.code) {
                wx.request({
                    url: constants.domain + '/wechat/bind',
                    method: 'post',
                    dataType: 'json',
                    data: {...args, 'code': res.code},
                    success(e) {
                        cache.set('user', e.data);  // it's a hack to keep track of current user
                        if (e.statusCode != '404') {
                            cache.set('jwt',e.data.jwt);
                            cb(true);
                        } else {
                            cb(false);
                        }
                    },
                    complete(e) {
                        cb(false);
                    }
                });
            } else {
                console.log('获取用户登录态失败！' + res.errMsg)
            }
        }
    });


}

export default {bindUser};