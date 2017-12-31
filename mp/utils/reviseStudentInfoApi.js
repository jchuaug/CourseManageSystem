import cache from './localCache';
function aboutMe(cb) {
    //todo you know what to do
    cb(cache.get('me'));
}

export default {aboutMe};