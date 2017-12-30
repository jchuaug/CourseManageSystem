import constants from './constants';
import cache from './localCache';

function bindUser(args, cb) {
    wx.login({
        success: function (res) {
            console.log(res);
            if (res.code) {
                wx.request({
                    url: constants.domain + '/auth/weChat',
                    method: 'post',
                    dataType: 'json',
                    data: {"code": res.code, "type": 1},
                    complete(e) {
                        if (e.statusCode == '204') {
                            // bind success
                            console.log(e);
                            cache.set('userID', e.header.id);  // it's a hack to keep track of current user
                            cb(true);
                        } else {
                            cb(false);
                        }
                    }
                });
            } else {
                console.log('获取用户登录态失败！' + res.errMsg)
            }
        }
    });


}

export default {bindUser};