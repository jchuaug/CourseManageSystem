import cache from './localCache';
import utils from './utils';

const rollStatusInCalling = {
    "numPresent": 4,
    "present": [
        {
            "id": 2357,
            "name": "张三"
        },
        {
            "id": 8232,
            "name": "李四"
        }
    ]
};

const rollStatusAfterCalled = {
    "numPresent": 4,
    "present": [
        {
            "id": 2357,
            "name": "张三"
        },
        {
            "id": 8232,
            "name": "李四"
        }
    ],
    "late": [
        {
            "id": 3412,
            "name": "王五"
        },
        {
            "id": 5234,
            "name": "王七九"
        }
    ],
    "absent": [
        {
            "id": 34,
            "name": "张六"
        }
    ]
};

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
};

//简单地封装了一下wx.request
const myrequest = function (requrl, callback) {
    wx.request({
        url: requrl,
        success: function (res) {
            callback(res.data)
        },
        fail: function () {
            wx.showToast({
                title: '获取后台数据失败',
            })
        }
    })
};

const getRollStatusInCalling = function (id, callback) {
    callback(rollStatusInCalling)
    // let url = '/class/'+id+'/attendance?showPresent=true'
    // myrequest(url,callback)
};

const getRollStatusAfterCalled = function (id, callback) {
    callback(rollStatusAfterCalled)
    // let url = '/class/'+id+'/attendance?showPresent=true&showLate=true&showAbsent=true'
    // myrequest(url,callback)
};

const getClassDetail = function () {
    return cache.get('currentClass');
};

function getCallingStatus(cb) {
    Promise.all([getInfo('attendance'), getInfo('attendance/present')]).then(res => {
        const result = res[0];
        result.present = res[1];
        cb(result);
    });
}

function getInfo(url) {
    const classID = cache.get('currentClass').id;
    const seminarID = cache.get('currentSeminar').id;

    return new Promise(resolve => {
        utils.requestWithId({
            url: `/seminar/${seminarID}/class/${classID}/${url}`,
            success: function (res) {
                resolve(res.data);
            }
        });
    })
}


export default {
    getClassDetail,
    getCallingStatus
}