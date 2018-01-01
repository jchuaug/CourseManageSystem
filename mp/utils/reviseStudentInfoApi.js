import cache from './localCache';
import utils from './utils';


function aboutMe(cb) {
    //todo you know what to do
    cb(cache.get('me'));
}

function unbind(cb) {
    utils.requestWithId({
        url: ``,
        success: cb
    })

}

export default {aboutMe, unbind};