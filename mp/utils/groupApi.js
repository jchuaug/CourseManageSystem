import cache from './localCache';
import utils from './utils';

function getGroupInfo(cb) {
    cb(cache.get('group'));
}

function amILeader() {
    const id = cache.get('userID');
    const group = cache.get('group');
    if (group.leader) {
        return group.leader.id == id;
    }
    return false;
}

function becomeLeader(cb) {
    const group = cache.get('group');
    const myID = cache.get('userID');

    const reqObj = {};
    reqObj.id = myID;
    console.log(reqObj);

    utils.requestWithId({
        url: `/group/${cache.get('groupID')}/assign`,
        data: reqObj,
        method: 'put',
        success: function (res) {
            getGroupInfo(cb);
        }
    });
    cb(true);
}


function quitLeader(cb) {
    const group = cache.get('group');


    const reqObj = {};
    reqObj.id = cache.get('userID');
    utils.requestWithId({
        url: `/group/${cache.get('groupID')}/resign`,
        data: reqObj,
        method: 'put',
        success: function (res) {
            getGroupInfo(cb);
        }
    })
}

export default {getGroupInfo, amILeader, becomeLeader, quitLeader}