import utils from './utils';
import cache from './localCache'

function getPresentationGroups(cb) {
    //todo get seminar id and grouid from local cache
//     GET /seminar/{seminarId}/group?gradeable={true}
// 打分 PUT /group/{groupId}/grade/{studentId}

    utils.requestWithId({
        url: `/seminar/group/${cache.get('group').id}/others`,
        success: function (res) {
            cb(res.data);
        }
    });


}

function submitScore(groups, cb) {
    utils.requestWithId({
        url: `/group/${cache.get('group').id}/grade/presentation/${cache.get('userID')}`,
        data: {'groups': groups},
        method: 'put',
        success: function (res) {
            if (res.statusCode == '204') {
                cb(true);
            } else {
                cb(false);
            }
        }
    });
}

export default {getPresentationGroups, submitScore}