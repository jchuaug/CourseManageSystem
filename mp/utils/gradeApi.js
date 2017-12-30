function getPresentationGroups(cb) {
    //todo get seminar id and grouid from local cache
//     GET /seminar/{seminarId}/group?gradeable={true}
// 打分 PUT /group/{groupId}/grade/{studentId}
    cb([{id: 0, name: 'A1', score: 0},
        {id: 1, name: 'A2', score: 0},
        {id: 2, name: 'A3', score: 0},
        {id: 3, name: 'A4', score: 0},
        {id: 4, name: 'A5', score: 0}]);

}

function submitScore(groups, cb) {
    //todo
    cb(true);
}

export default {getPresentationGroups, submitScore}