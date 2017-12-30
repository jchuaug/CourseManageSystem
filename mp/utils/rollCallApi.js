function getSeminarInfoById(id, cb) {
    // todo replace fake data
    // todo get page state: if called in roll
    cb({});
}

function callInRoll(args, cb) {
    // todo replace fake data

    // todo PUT /class/{classId}/attendance/{studentId}
    cb({
        state: true
    });   
}

export default {getSeminarInfoById, callInRoll}