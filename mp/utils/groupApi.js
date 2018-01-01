import cache from './localCache';
import utils from './utils';

function getGroupInfo(cb) {
    // GET /seminar/{seminarId}/group?include={studentId}
    // GET /group/{groupId}?embedTopics=true

    // first get group id
    utils.requestWithId({
        url: `/seminar/${cache.get('currentSeminarID')}/group/my`,
        success: function (res) {
            console.log('group', res);
            const groupID = res.data.id;
            cache.set('groupID', groupID);

            cache.set('group', res.data);
            cb(res.data);

            // then get group info
            // utils.requestWithId({
            //     url: `/group/${groupID}`,
            //     success: function (res) {
            //         cache.set('group', res.data);
            //         cb(res.data);
            //     }
            // });
        }
    });
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
    const members = group.members;
    const myID = cache.get('userID');
    const newMembers = members.filter(group => group.id != myID);

    const reqObj = {};
    reqObj.leader = {'id': myID};
    reqObj.members = newMembers;

    utils.requestWithId({
        url: `/group/${cache.get('groupID')}`,
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
    const members = group.members;

    const newMembers = Object.assign([], members);
    newMembers.push(group.leader);

    const reqObj = {};
    reqObj.members = newMembers;
    utils.requestWithId({
        url: `/group/${cache.get('groupID')}`,
        data: reqObj,
        method: 'put',
        success: function (res) {
            getGroupInfo(cb);
        }
    })
}

export default {getGroupInfo, amILeader, becomeLeader, quitLeader}