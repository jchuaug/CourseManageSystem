import utils from './utils';
import cache from './localCache';

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

};

const putLocation = function (data, callback) {
    utils.requestWithId({
        url: '/seminar/' + cache.get('currentSeminar').id + '/class/' + cache.get('currentClass').id + '/attendance/' + cache.get('userID'),
        method: 'put',
        data: data,
        success: callback
    })
};

const getCurrentSeminar = function (callback) {
    return cache.get('currentSeminar');
    //  let url = '/seminar/'+id
    //  myrequest(url,callback)
};

const getClassStatus = function (cb) {
    const url = `/seminar/${cache.get('currentSeminar').id}/class/${cache.get('currentClass').id}/location`;
    console.log(url);
    utils.requestWithId({
        url: `/seminar/${cache.get('currentSeminar').id}/class/${cache.get('currentClass').id}/location`,
        success(res) {
            let status = 'start';
            switch (res.data.status) {
                case 0:
                    status = 'calling';
                    break;
                case 1:
                    status = 'end';
                    break;
                default:
                    break;
            }
            cb(status);
        }
    })

};

export default {
    getClassByClassId,
    putCurClassCalling,
    getCurrentSeminar,
    putLocation,
    getClassStatus
}
