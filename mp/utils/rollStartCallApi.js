import utils from './utils';
import cache from './localCache';

const classdata = {
    "id": 23,
    "name": "周三1-2节",
    "numStudent": 120,
    "time": [
        {
            "week": 1,
            "day": 1,
            "lessons": [
                1,
                2
            ],
            "site": "海韵201"
        },
        {
            "week": 0,
            "day": 3,
            "lessons": [
                3,
                4
            ],
            "site": "公寓405"
        }
    ],
    "calling": 22,
    "roster": "/roster/周三12班.xlsx",
    "proportions": {
        "3": 20,
        "4": 60,
        "5": 20,
        "report": 50,
        "presentation": 50
    }
}

const seminardata1 = {
    "id": 32,
    "name": "概要设计",
    "description": "模型层与数据库设计",
    "groupingMethod": "fixed",
    "startTime": "2017-10-10",
    "endTime": "2017-10-24"
}

const seminardata2 = {
    "id": 12,
    "name": "原型设计",
    "description": "模型层与数据库设计",
    "groupingMethod": "random",
    "startTime": "2017-9-10",
    "endTime": "2017-9-24"
};

const getClassByClassId = function (id, callback) {
    // let url = '/class/'+id
    // myrequest(url,callback)

    utils.requestWithId({
        url: `/class/${id}`,
        success: function (res) {
            const currentClass = res.data;
            cache.set('currentClass', currentClass);
            callback(currentClass);
        }
    });

};

const putCurClassCalling = function (args, callback) {
    callback();
    const requestBody = {};
    requestBody.calling = args.calling;

    utils.requestWithId({
        url: `/class/${args.classID}`,
        method: 'put',
        data: requestBody,
        success: function (res) {
            if (res.status == '204') {
                callback(true);
            }
        }
    });
    // wx.request({
    //   url: '/class/'+id,
    //   data:reqbody,
    //   success:(res)=>{
    //     callback(res)
    //   }
    // })
};

const getCurrentSeminar = function (callback) {
    return cache.get('currentSeminar');
    //  let url = '/seminar/'+id
    //  myrequest(url,callback)
};

const putLocation = function(data,callback){
  utils.requestWithId({
    // url: '/seminar/'+cache.get('currentSeminar').id+'/class/'+cache.get('currentClass').id+'/attendance/'+cache.get('userID'),
    url: '/seminar/' + cache.get('currentSeminar').id + '/class/' + cache.get('currentClass').id + '/attendance/'+20170315,
    method:'put',
    data:data,
    success:callback
  })
}

export default {
    getClassByClassId,
    putCurClassCalling,
    getCurrentSeminar,
    putLocation
}
