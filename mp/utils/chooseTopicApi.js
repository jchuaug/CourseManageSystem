import utils from './utils';
import cache from './localCache';

function getTopics(cb) {
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
            //update seminar info

            utils.requestWithId({
                url: `/seminar/${cache.get('currentSeminarID')}/group/my`,
                success: function (res) {
                    cache.set('group', res.data);
                    cb(true);
                }
            });

        },
        fail: function (res) {
            console.error(res);
            cb(false);
        }
    });
}

export default {getTopics, chooseTopic};
// async function () {
//     var x = await add
//     var x = await add
//     var x = await add
//     var x = await add
//     var x = await add
// }