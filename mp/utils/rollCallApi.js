import cache from "./localCache";
import utils from './utils';

function getSeminarInfoById(id, cb) {
    // todo replace fake data
    // todo get page state: if called in roll
    cb({});
}

function callInRoll(cb) {
    // todo replace fake data

    // todo PUT /class/{classId}/attendance/{studentId}

    console.log(cache.get("classId"));
    utils.requestWithId({
        url: `/seminar/${cache.get('currentSeminarId')}/class/${cache.get('classId')}/attendance/${cache.get('userID')}`,
        method: 'put',
        data: {
            seminarId: cache.get("currentSeminarId"),
            status: 0
            //已签到的状态是0
        },
        success: cb
    })
    console.log(cache.get("currentSeminarId"));
}

export default {getSeminarInfoById, callInRoll}