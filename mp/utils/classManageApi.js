import utils from './utils';
import cache from './localCache';


function getInfo(courseID, cb) {
    utils.requestWithId({
        url: `/course/${courseID}/seminar/current`,
        success: function (res) {
            const seminar = res.data;
            cache.set('currentSeminar',seminar);
            cb(seminar);
        }
    })
}

export default {
    getInfo
}
