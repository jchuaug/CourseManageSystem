import utils from './utils';
import cache from './localCache';

function getTopics(cb) {
//     获得所有话题 GET /seminar/{seminarId}/topic
// 请求数据：无
// 响应数据：包含所有话题的信息的JSON
// [
//     {
//         "id": 257,
//         "name": "领域模型与模块",
//         "description": "Domain model与模块划分",
//         "groupLimit": 5,
//         "groupLeft": 2
//     }
// ]

    utils.requestWithId({
        url: `/seminar/${cache.get('currentSeminarID')}/topic`,
        success: function (res) {
            cb(res.data);
        }
    });
}

function chooseTopic(id, cb) {
    const groupID = cache.get('groupID');
    const requestBody = {id};

    utils.requestWithId({
        url: `/group/${groupID}/topic`,
        data: requestBody,
        method: 'post',
        success: function (res) {
            console.log(res);
            cb(true);
        },
        fail: function (res) {
            console.error(res);
            cb(false);
        }
    });
}

export default {getTopics,chooseTopic};
// async function () {
//     var x = await add
//     var x = await add
//     var x = await add
//     var x = await add
//     var x = await add
// }