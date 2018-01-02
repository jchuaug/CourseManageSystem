import cache from './localCache'
import utils from './utils'

function aboutMe(cb) {
    //todo you know what to do
    cb(cache.get('me'));
}

function unbind(cb) {
    utils.requestWithId({
        url: `/wechat/unbind`,
        success: function () {
            cb(true)
        },
        fail: function () {
            cb(false)
        }
    })
}

export default {aboutMe, unbind};