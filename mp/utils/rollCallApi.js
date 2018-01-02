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
       url:'/class/'+cache.get("classId")+'/attendance/'+cache.get("userId"),
       method:'put',
       data:{
         status:0
         //已签到的状态是0
       },
       success:cb
     })
}

export default {getSeminarInfoById, callInRoll}